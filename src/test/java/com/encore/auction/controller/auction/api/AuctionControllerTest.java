// package com.encore.auction.controller.auction.api;
//
// import static org.mockito.Mockito.*;
// import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
// import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
// import static org.springframework.restdocs.payload.PayloadDocumentation.*;
// import static org.springframework.restdocs.request.RequestDocumentation.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.restdocs.payload.JsonFieldType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
//
// import com.encore.auction.controller.auction.requests.AuctionCreateRequest;
// import com.encore.auction.controller.auction.requests.AuctionUpdateRequest;
// import com.encore.auction.controller.auction.responses.AuctionDeleteResponse;
// import com.encore.auction.controller.auction.responses.AuctionDetailsResponse;
// import com.encore.auction.controller.auction.responses.AuctionIdResponse;
// import com.encore.auction.controller.auction.responses.AuctionRetrieveResponse;
// import com.encore.auction.controller.comment.responses.CommentDetailsResponse;
// import com.encore.auction.model.auction.item.ItemCategory;
// import com.encore.auction.service.auction.AuctionService;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @WebMvcTest(controllers = AuctionController.class)
// @AutoConfigureRestDocs
// class AuctionControllerTest {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@MockBean
// 	private AuctionService auctionService;
//
// 	@Autowired
// 	private ObjectMapper objectMapper;
//
// 	private final Long auctionItemId = 13L;
//
// 	private final String addressCode = "11310";
//
// 	private final String managerId = "tester2";
//
// 	private final String auctionItemName = "??????????????????";
//
// 	private final String location = "????????? ?????????";
//
// 	private final String lotNumber = "22";
//
// 	private final String addressDetail = "?????????";
//
// 	private final Long appraisedValue = 190000000L;
//
// 	private final LocalDateTime auctionStartDate = LocalDateTime.of(2019, 12, 25, 00, 00, 00, 0000);
//
// 	private final LocalDateTime auctionEndDate = LocalDateTime.of(2022, 12, 25, 00, 00, 00, 0000);
//
// 	private final ItemCategory itemCategory = ItemCategory.APARTMENT;
//
// 	private final Double areaSize = 123.4;
//
// 	private final Integer auctionFailedCount = 12;
//
// 	private final Integer hit = 34;
//
// 	@Test
// 	@DisplayName("Create Auction Item Controller Test - Success")
// 	void createAuctionSuccess() throws Exception {
// 		// given
// 		AuctionCreateRequest auctionCreateRequest = new AuctionCreateRequest(managerId, addressCode, auctionItemName,
// 			location, lotNumber, addressDetail, appraisedValue, auctionStartDate, auctionEndDate, itemCategory,
// 			areaSize);
//
// 		AuctionIdResponse auctionIdResponse = new AuctionIdResponse(auctionItemId);
//
// 		when(auctionService.createAuctionItem(any(AuctionCreateRequest.class))).thenReturn(auctionIdResponse);
// 		// when
// 		ResultActions resultActions = mockMvc.perform(
// 			post("/auction/create")
// 				.content(objectMapper.writeValueAsString(auctionCreateRequest))
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.accept(MediaType.APPLICATION_JSON));
// 		//then
// 		resultActions.andExpect(status().isCreated())
// 			.andDo(print())
// 			.andDo(document("auction-create",
// 				requestFields(
// 					fieldWithPath("managerId").type(JsonFieldType.STRING).description("?????? ????????? ????????? ?????????"),
// 					fieldWithPath("addressCode").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
// 					fieldWithPath("auctionItemName").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("location").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("lotNumber").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("addressDetail").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
// 					fieldWithPath("appraisedValue").type(JsonFieldType.NUMBER).description("?????????"),
// 					fieldWithPath("auctionStartDate").type(JsonFieldType.STRING).description("?????? ?????????"),
// 					fieldWithPath("auctionEndDate").type(JsonFieldType.STRING).description("?????? ?????????"),
// 					fieldWithPath("itemCategory").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("areaSize").type(JsonFieldType.NUMBER).description("??????")
// 				),
// 				responseFields(
// 					fieldWithPath("auctionItemId").type(JsonFieldType.NUMBER).description("?????? ??????")
// 				)
// 			));
// 	}
//
// 	@Test
// 	@DisplayName("Retrieve Auction Item Controller Test - Success")
// 	void retrieveAuctionSuccess() throws Exception {
// 		// given
// 		List<CommentDetailsResponse> commentDetailsResponseList = new ArrayList<>();
// 		commentDetailsResponseList.add(new CommentDetailsResponse("tester1", auctionItemId, "?????? ?????????"));
//
// 		AuctionRetrieveResponse auctionRetrieveResponse = new AuctionRetrieveResponse(auctionItemId, addressCode,
// 			managerId,
// 			auctionItemName,
// 			location, lotNumber, addressDetail, appraisedValue, auctionStartDate, auctionEndDate, itemCategory,
// 			areaSize, auctionFailedCount, hit, commentDetailsResponseList);
//
// 		when(auctionService.retrieveAuctionItem(anyLong())).thenReturn(auctionRetrieveResponse);
// 		// when
// 		ResultActions resultActions = mockMvc.perform(
// 			get("/auction/{auction-item-id}", auctionItemId)
// 				.accept(MediaType.APPLICATION_JSON));
// 		//then
// 		resultActions.andExpect(status().isOk())
// 			.andDo(print())
// 			.andDo(document("auction-retrieve",
// 				pathParameters(parameterWithName("auction-item-id").description("?????? ?????? Auction Item Id")),
// 				responseFields(
// 					fieldWithPath("auctionItemId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????? ??????"),
// 					fieldWithPath("managerId").type(JsonFieldType.STRING).description("?????? ????????? ????????? ?????????"),
// 					fieldWithPath("addressCode").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
// 					fieldWithPath("auctionItemName").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("location").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("lotNumber").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("addressDetail").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
// 					fieldWithPath("appraisedValue").type(JsonFieldType.NUMBER).description("?????????"),
// 					fieldWithPath("auctionStartDate").type(JsonFieldType.STRING).description("?????? ?????????"),
// 					fieldWithPath("auctionEndDate").type(JsonFieldType.STRING).description("?????? ?????????"),
// 					fieldWithPath("itemCategory").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("areaSize").type(JsonFieldType.NUMBER).description("??????"),
// 					fieldWithPath("auctionFailedCount").type(JsonFieldType.NUMBER).description("?????? ??????"),
// 					fieldWithPath("hit").type(JsonFieldType.NUMBER).description("?????????"),
// 					fieldWithPath("commentDetailsResponseList").type(JsonFieldType.ARRAY).description("?????????"),
// 					fieldWithPath("commentDetailsResponseList[].userId").type(JsonFieldType.STRING)
// 						.description("????????? ?????? ?????????"),
// 					fieldWithPath("commentDetailsResponseList[].auctionItemId").type(JsonFieldType.NUMBER)
// 						.description("????????? ??????????????? auction item ?????????"),
// 					fieldWithPath("commentDetailsResponseList[].content").type(JsonFieldType.STRING)
// 						.description("?????? ??????")
// 				)
// 			));
// 	}
//
// 	@Test
// 	@DisplayName("Update Auction Item Controller Test - Success")
// 	void updateAuctionSuccess() throws Exception {
// 		//given
// 		AuctionUpdateRequest auctionUpdateRequest = new AuctionUpdateRequest(managerId, addressCode,
// 			auctionItemName,
// 			location, lotNumber, addressDetail, appraisedValue, auctionStartDate, auctionEndDate, itemCategory,
// 			areaSize);
//
// 		AuctionDetailsResponse auctionDetailsResponse = new AuctionDetailsResponse(auctionItemId, addressCode,
// 			managerId,
// 			auctionItemName,
// 			location, lotNumber, addressDetail, appraisedValue, auctionStartDate, auctionEndDate, itemCategory,
// 			areaSize, auctionFailedCount, hit);
//
// 		when(auctionService.updateAuctionItem(anyLong(), any(AuctionUpdateRequest.class))).thenReturn(
// 			auctionDetailsResponse);
// 		//when
// 		ResultActions resultActions = mockMvc.perform(
// 			put("/auction/{auction-item-id}", auctionItemId)
// 				.content(objectMapper.writeValueAsString(auctionUpdateRequest))
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.accept(MediaType.APPLICATION_JSON));
// 		//then
// 		resultActions.andExpect(status().isOk())
// 			.andDo(print())
// 			.andDo(document("auction-update",
// 				pathParameters(parameterWithName("auction-item-id").description("????????? Auction Item Id")),
// 				requestFields(
// 					fieldWithPath("managerId").type(JsonFieldType.STRING).description("?????? ????????? ????????? ?????????"),
// 					fieldWithPath("addressCode").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
// 					fieldWithPath("auctionItemName").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("location").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("lotNumber").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("addressDetail").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
// 					fieldWithPath("appraisedValue").type(JsonFieldType.NUMBER).description("?????????"),
// 					fieldWithPath("auctionStartDate").type(JsonFieldType.STRING).description("?????? ?????????"),
// 					fieldWithPath("auctionEndDate").type(JsonFieldType.STRING).description("?????? ?????????"),
// 					fieldWithPath("itemCategory").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("areaSize").type(JsonFieldType.NUMBER).description("??????")
// 				),
// 				responseFields(
// 					fieldWithPath("auctionItemId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????? ??????"),
// 					fieldWithPath("managerId").type(JsonFieldType.STRING).description("?????? ????????? ????????? ?????????"),
// 					fieldWithPath("addressCode").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
// 					fieldWithPath("auctionItemName").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("location").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("lotNumber").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("addressDetail").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
// 					fieldWithPath("appraisedValue").type(JsonFieldType.NUMBER).description("?????????"),
// 					fieldWithPath("auctionStartDate").type(JsonFieldType.STRING).description("?????? ?????????"),
// 					fieldWithPath("auctionEndDate").type(JsonFieldType.STRING).description("?????? ?????????"),
// 					fieldWithPath("itemCategory").type(JsonFieldType.STRING).description("?????? ??????"),
// 					fieldWithPath("areaSize").type(JsonFieldType.NUMBER).description("??????"),
// 					fieldWithPath("auctionFailedCount").type(JsonFieldType.NUMBER).description("?????? ??????"),
// 					fieldWithPath("hit").type(JsonFieldType.NUMBER).description("?????????")
// 				)
// 			));
// 	}
//
// 	@Test
// 	@DisplayName("delete Auction Item Controller Test - Success")
// 	void deleteAuctionSuccess() throws Exception {
// 		//given
// 		AuctionDeleteResponse auctionDeleteResponse = new AuctionDeleteResponse(auctionItemId, true);
//
// 		when(auctionService.deleteAuctionItem(anyLong(), anyString())).thenReturn(auctionDeleteResponse);
// 		//when
// 		ResultActions resultActions = mockMvc.perform(
// 			delete("/auction/{auction-item-id}", auctionItemId)
// 				.header("managerId", managerId)
// 				.accept(MediaType.APPLICATION_JSON));
// 		//then
// 		resultActions.andExpect(status().isOk())
// 			.andDo(print())
// 			.andDo(document("auction-delete",
// 				pathParameters(parameterWithName("auction-item-id").description("????????? Auction Item Id")),
// 				responseFields(
// 					fieldWithPath("auctionItemId").type(JsonFieldType.NUMBER).description("????????? ??????"),
// 					fieldWithPath("state").type(JsonFieldType.BOOLEAN).description("????????? ??????")
// 				)
// 			));
// 	}
//
// }
