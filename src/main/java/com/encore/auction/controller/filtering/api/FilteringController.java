package com.encore.auction.controller.filtering.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encore.auction.controller.filtering.requests.FilteringAuctionItemByManagerIdRequest;
import com.encore.auction.controller.filtering.requests.FilteringAuctionItemRequest;
import com.encore.auction.controller.filtering.responses.FilteringItemsListResponse;
import com.encore.auction.service.filtering.FilteringService;
import com.encore.auction.utils.security.Permission;

@RestController
@RequestMapping("/api/auction-list")
public class FilteringController {

	private final FilteringService filteringService;

	public FilteringController(FilteringService filteringService) {
		this.filteringService = filteringService;
	}

	@GetMapping
	public ResponseEntity<FilteringItemsListResponse> getFilteredAuctionItemList(
		@ModelAttribute FilteringAuctionItemRequest filteringAuctionItemRequest) {
		return ResponseEntity.ok()
			.body(filteringService.getFilteredAuctionItemList(filteringAuctionItemRequest));
	}

	@Permission
	@GetMapping("/by-manager")
	public ResponseEntity<FilteringItemsListResponse> findAuctionItemListByManagerId(
		@RequestHeader("Token") String token,
		@ModelAttribute FilteringAuctionItemByManagerIdRequest filteringAuctionItemByManagerIdRequest) {
		return ResponseEntity.ok()
			.body(filteringService.findAuctionListByManagerId(filteringAuctionItemByManagerIdRequest, token));
	}
}
