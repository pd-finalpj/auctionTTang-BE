package com.encore.auction.controller.auction.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encore.auction.controller.auction.requests.AuctionCreateRequest;
import com.encore.auction.controller.auction.requests.AuctionUpdateRequest;
import com.encore.auction.controller.auction.responses.AuctionDeleteResponse;
import com.encore.auction.controller.auction.responses.AuctionDetailsResponse;
import com.encore.auction.controller.auction.responses.AuctionIdResponse;
import com.encore.auction.controller.auction.responses.AuctionRetrieveResponse;
import com.encore.auction.service.auction.AuctionService;

@RestController
@RequestMapping("/auction")
public class AuctionController {

	private final AuctionService auctionService;

	public AuctionController(AuctionService auctionService) {
		this.auctionService = auctionService;
	}

	@PostMapping("/create")
	public ResponseEntity<AuctionIdResponse> createAuctionItem(@RequestHeader("Token") String token,
		@Valid @RequestBody AuctionCreateRequest auctionCreateRequest) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(auctionService.createAuctionItem(auctionCreateRequest, token));
	}

	@GetMapping("/{auction-item-id}")
	public ResponseEntity<AuctionRetrieveResponse> retrieveAuctionItem(
		@PathVariable("auction-item-id") Long auctionItemId) {
		return ResponseEntity.ok().body(auctionService.retrieveAuctionItem(auctionItemId));
	}

	@PutMapping("/{auction-item-id}")
	public ResponseEntity<AuctionDetailsResponse> updateAuctionItem(
		@PathVariable("auction-item-id") Long auctionItemId,
		@Valid @RequestBody AuctionUpdateRequest auctionUpdateRequest, @RequestHeader("Token") String token) {
		return ResponseEntity.ok().body(auctionService.updateAuctionItem(auctionItemId, auctionUpdateRequest, token));
	}

	@DeleteMapping("/{auction-item-id}")
	public ResponseEntity<AuctionDeleteResponse> deleteAuctionItem(@PathVariable("auction-item-id") Long auctionItemId,
		@RequestHeader("Token") String token) {
		return ResponseEntity.ok().body(auctionService.deleteAuctionItem(auctionItemId, token));
	}
}
