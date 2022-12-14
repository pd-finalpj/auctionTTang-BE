package com.encore.auction.controller.filtering.responses;

import java.time.LocalDateTime;

import com.encore.auction.model.auction.item.ItemCategory;
import com.encore.auction.model.auction.item.ItemSoldState;

import lombok.Getter;

@Getter
public class FilteringItemsResponse {

	private final Long auctionItemId;

	private final String managerId;
	
	private final String managerName;

	private final String addressCode;

	private final String auctionItemCaseNumber;

	private final String auctionItemName;

	private final String stateName;

	private final String cityName;

	private final String location;

	private final String lotNumber;

	private final String addressDetail;

	private final Long appraisedValue;

	private final LocalDateTime auctionStartDate;

	private final LocalDateTime auctionEndDate;

	private final ItemCategory itemCategory;

	private final Double areaSize;

	private final Integer auctionFailedCount;

	private final ItemSoldState itemSoldState;

	public FilteringItemsResponse(Long auctionItemId, String managerId, String managerName, String addressCode,
		String auctionItemCaseNumber, String auctionItemName, String stateName, String cityName, String location,
		String lotNumber, String addressDetail, Long appraisedValue, LocalDateTime auctionStartDate,
		LocalDateTime auctionEndDate, ItemCategory itemCategory, Double areaSize, Integer auctionFailedCount,
		ItemSoldState itemSoldState) {
		this.auctionItemId = auctionItemId;
		this.managerId = managerId;
		this.managerName = managerName;
		this.addressCode = addressCode;
		this.auctionItemCaseNumber = auctionItemCaseNumber;
		this.auctionItemName = auctionItemName;
		this.stateName = stateName;
		this.cityName = cityName;
		this.location = location;
		this.lotNumber = lotNumber;
		this.addressDetail = addressDetail;
		this.appraisedValue = appraisedValue;
		this.auctionStartDate = auctionStartDate;
		this.auctionEndDate = auctionEndDate;
		this.itemCategory = itemCategory;
		this.areaSize = areaSize;
		this.auctionFailedCount = auctionFailedCount;
		this.itemSoldState = itemSoldState;
	}
}
