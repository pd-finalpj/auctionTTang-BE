package com.encore.auction.controller.bookmark.responses;

import java.time.LocalDateTime;

import com.encore.auction.model.auction.item.ItemCategory;
import com.encore.auction.utils.validator.LocalDateTimeValidator;

import lombok.Getter;

@Getter
public final class BookmarkDetailsResponse {

	private final Long auctionItemId;

	private final String auctionAddressCode;

	private final String auctionItemName;

	private final String auctionLocation;

	private final String auctionLotNumber;

	private final String addressDetail;

	private final Long appraisedValue;

	private final String auctionStartDate;

	private final String auctionEndDate;

	private final ItemCategory itemCategory;

	private final Double areaSize;

	private final Integer auctionFailedCount;

	private final Integer hit;

	public BookmarkDetailsResponse(Long auctionItemId, String auctionAddressCode, String auctionItemName,
		String auctionLocation, String auctionLotNumber, String addressDetail, Long appraisedValue,
		LocalDateTime auctionStartDate, LocalDateTime auctionEndDate, ItemCategory itemCategory, Double areaSize,
		Integer auctionFailedCount, Integer hit) {
		this.auctionItemId = auctionItemId;
		this.auctionAddressCode = auctionAddressCode;
		this.auctionItemName = auctionItemName;
		this.auctionLocation = auctionLocation;
		this.auctionLotNumber = auctionLotNumber;
		this.addressDetail = addressDetail;
		this.appraisedValue = appraisedValue;
		this.auctionStartDate = LocalDateTimeValidator.of().convertLocalDateTimeToString(auctionStartDate);
		this.auctionEndDate = LocalDateTimeValidator.of().convertLocalDateTimeToString(auctionEndDate);
		this.itemCategory = itemCategory;
		this.areaSize = areaSize;
		this.auctionFailedCount = auctionFailedCount;
		this.hit = hit;
	}
}
