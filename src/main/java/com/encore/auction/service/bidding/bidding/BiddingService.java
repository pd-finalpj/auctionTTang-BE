package com.encore.auction.service.bidding.bidding;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encore.auction.controller.bidding.bidding.requests.BiddingRegisterRequest;
import com.encore.auction.controller.bidding.bidding.requests.BiddingUpdateRequest;
import com.encore.auction.controller.bidding.bidding.responses.BiddingDeleteResponse;
import com.encore.auction.controller.bidding.bidding.responses.BiddingDetailsResponse;
import com.encore.auction.controller.bidding.bidding.responses.BiddingIdResponse;
import com.encore.auction.controller.bidding.bidding.responses.BiddingRetrieveResponse;
import com.encore.auction.exception.NonExistResourceException;
import com.encore.auction.exception.WrongRequestException;
import com.encore.auction.model.auction.item.AuctionItem;
import com.encore.auction.model.bidding.aftbidding.BiddingResult;
import com.encore.auction.model.bidding.bidding.Bidding;
import com.encore.auction.model.biddingdetails.BiddingDetails;
import com.encore.auction.model.user.User;
import com.encore.auction.repository.auction.AuctionItemRepository;
import com.encore.auction.repository.bidding.bidding.BiddingRepository;
import com.encore.auction.repository.bidding.bidding.BiddingRetrieveRepository;
import com.encore.auction.repository.mongodb.AuctionFailedLogMongoDBRepository;
import com.encore.auction.repository.mongodb.BiddingDetailsMongoDBRepository;
import com.encore.auction.repository.user.UserRepository;
import com.encore.auction.utils.mapper.AuctionMapper;
import com.encore.auction.utils.mapper.BiddingMapper;
import com.encore.auction.utils.token.JwtProvider;
import com.encore.auction.utils.validator.LocalDateTimeValidator;

@Service
public class BiddingService {

	private final UserRepository userRepository;
	private final BiddingRepository biddingRepository;
	private final AuctionItemRepository auctionItemRepository;
	private final BiddingRetrieveRepository biddingRetrieveRepository;
	private final JwtProvider jwtProvider;
	private final BiddingDetailsMongoDBRepository biddingDetailsMongoDBRepository;

	public BiddingService(UserRepository userRepository, BiddingRepository biddingRepository,
		AuctionItemRepository auctionItemRepository, BiddingRetrieveRepository biddingRetrieveRepository,
		JwtProvider jwtProvider, BiddingDetailsMongoDBRepository biddingDetailsMongoDBRepository,
		AuctionFailedLogMongoDBRepository auctionFailedLogMongoDBRepository) {
		this.userRepository = userRepository;
		this.biddingRepository = biddingRepository;
		this.auctionItemRepository = auctionItemRepository;
		this.biddingRetrieveRepository = biddingRetrieveRepository;
		this.jwtProvider = jwtProvider;
		this.biddingDetailsMongoDBRepository = biddingDetailsMongoDBRepository;
	}

	@Transactional
	public BiddingIdResponse registerBidding(BiddingRegisterRequest biddingRegisterRequest, String token) {
		String userId = jwtProvider.checkTokenIsUserAndGetUserID(token);

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new NonExistResourceException("User does not exist"));

		AuctionItem auctionItem = auctionItemRepository.findById(biddingRegisterRequest.getAuctionItemId())
			.orElseThrow(() -> new NonExistResourceException("Auction item does not exist"));

		if (biddingRepository.findByUserIdAndAuctionItemId(user.getId(), auctionItem.getId()).isPresent())
			throw new WrongRequestException("User Already Bidding Auction try Update Bidding");

		LocalDateTime requestTime = LocalDateTime.now();

		checkBiddingDateIsBetweenAuctionStartDateAndEndDate(auctionItem, requestTime);

		Bidding bidding = BiddingMapper.of()
			.registerRequestToEntity(biddingRegisterRequest, user, auctionItem, requestTime);

		Bidding savedBidding = biddingRepository.save(bidding);

		saveLogDataToMongoDB(user, auctionItem, savedBidding);

		return BiddingMapper.of().entityToBiddingIdResponse(savedBidding);
	}

	public BiddingRetrieveResponse retrieveBidding(Long biddingId) {
		Bidding bidding = checkBiddingExistAndGetBidding(biddingId);

		return biddingRetrieveRepository.retrieveBiddingByBiddingId(bidding.getId());
	}

	@Transactional
	public BiddingDetailsResponse updateBidding(Long biddingId, BiddingUpdateRequest biddingUpdateRequest,
		String token) {
		String userId = jwtProvider.checkTokenIsUserAndGetUserID(token);

		Bidding bidding = checkBiddingExistAndGetBidding(biddingId);

		if (checkUserNotMatch(userId, bidding))
			throw new WrongRequestException("User does not match");

		checkAuctionIsAlreadyDone(bidding);

		bidding.updateBidding(biddingUpdateRequest);

		return BiddingMapper.of().entityToBiddingDetailsResponse(bidding);
	}

	@Transactional
	public BiddingDeleteResponse deleteBidding(Long biddingId, String token) {
		String userId = jwtProvider.checkTokenIsUserAndGetUserID(token);

		Bidding bidding = checkBiddingExistAndGetBidding(biddingId);

		if (checkUserNotMatch(userId, bidding))
			throw new WrongRequestException("User does not match");

		checkAuctionIsAlreadyDone(bidding);

		bidding.deleteBidding();

		return BiddingMapper.of().entityToBiddingDeleteResponse(bidding);
	}

	private void checkAuctionIsAlreadyDone(Bidding bidding) {
		if (isAuctionAlreadyDone(bidding))
			throw new WrongRequestException("Auction is already done");
	}

	private void checkBiddingDateIsBetweenAuctionStartDateAndEndDate(AuctionItem auctionItem,
		LocalDateTime requestTime) {
		if (LocalDateTimeValidator.of().isBiddingTimeBeforeAuctionStartDate(auctionItem, requestTime))
			throw new WrongRequestException("Can't Bidding, because auction start not yet");

		if (LocalDateTimeValidator.of().isBiddingTimeAfterAuctionEndDate(auctionItem, requestTime))
			throw new WrongRequestException("Can't Bidding, because auction already end");
	}

	private Bidding checkBiddingExistAndGetBidding(Long biddingId) {
		return biddingRepository.findById(biddingId)
			.orElseThrow(() -> new NonExistResourceException("Bidding does not exist"));
	}

	private boolean checkUserNotMatch(String userId, Bidding bidding) {
		return !bidding.getUser().getId().equals(userId);
	}

	private boolean isAuctionAlreadyDone(Bidding bidding) {
		return bidding.getAuctionItem().getAuctionEndDate().isBefore(LocalDateTime.now());
	}

	private void saveLogDataToMongoDB(User user, AuctionItem auctionItem, Bidding savedBidding) {
		BiddingDetails biddingDetails = new BiddingDetails(savedBidding.getId(), user.getId(), user.getBirth(),
			savedBidding.getBiddingDate(),
			savedBidding.getAmount(), null, BiddingResult.UNDEFINED,
			AuctionMapper.of().entityToAuctionDetailsResponse(auctionItem));

		biddingDetailsMongoDBRepository.save(biddingDetails);
	}
}
