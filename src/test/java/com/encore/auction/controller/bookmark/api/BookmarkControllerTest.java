// package com.encore.auction.controller.bookmark.api;
//
// import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
// import static org.mockito.Mockito.*;
// import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
// import static org.springframework.restdocs.payload.PayloadDocumentation.*;
// import static org.springframework.restdocs.request.RequestDocumentation.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
// import com.encore.auction.controller.bookmark.requests.BookmarkRegisterRequest;
// import com.encore.auction.controller.bookmark.responses.BookmarkDeleteResponse;
// import com.encore.auction.controller.bookmark.responses.BookmarkRegisterResponse;
// import com.encore.auction.service.bookmark.BookmarkService;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @WebMvcTest(controllers = BookmarkController.class)
// @AutoConfigureRestDocs
// class BookmarkControllerTest {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@MockBean
// 	private BookmarkService bookmarkService;
//
// 	@Autowired
// 	private ObjectMapper objectMapper;
//
// 	private final String userId = "tester1";
// 	private final Long auctionId = 3L;
//
// 	@Test
// 	@DisplayName("Register Bookmark Controller Test - Success")
// 	void registerBookmarkSuccess() throws Exception {
// 		//given
// 		BookmarkRegisterRequest bookmarkRegisterRequest = new BookmarkRegisterRequest(userId, auctionId);
// 		BookmarkRegisterResponse bookmarkRegisterResponse = new BookmarkRegisterResponse(userId, auctionId, false);
//
// 		when(bookmarkService.registerBookmark(any(BookmarkRegisterRequest.class))).thenReturn(bookmarkRegisterResponse);
// 		//when
// 		ResultActions resultActions = mockMvc.perform(
// 			post("/bookmark")
// 				.content(objectMapper.writeValueAsString(bookmarkRegisterRequest))
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.accept(MediaType.APPLICATION_JSON));
// 		//then
// 		resultActions.andExpect(status().isCreated())
// 			.andDo(
// 				document("bookmark-registration",
// 					requestFields(
// 						fieldWithPath("userId").type(JsonFieldType.STRING).description("bookmark??? ??????????????? ?????? user??? ?????????"),
// 						fieldWithPath("auctionId").type(JsonFieldType.NUMBER)
// 							.description("bookmark ????????? ?????? auction item??? ?????????")
// 					),
// 					responseFields(
// 						fieldWithPath("userId").type(JsonFieldType.STRING).description("????????? user??? ?????????"),
// 						fieldWithPath("auctionId").type(JsonFieldType.NUMBER)
// 							.description("????????? accommodation??? ?????????"),
// 						fieldWithPath("state").type(JsonFieldType.BOOLEAN)
// 							.description("????????? bookmark??? ?????? false : ??????, true : ??????")
// 					))).andDo(print());
// 	}
//
// 	@Test
// 	@DisplayName("Delete Bookmark Controller Test - Success")
// 	void deleteBookmarkSuccess() throws Exception {
// 		//given
// 		BookmarkDeleteResponse bookmarkDeleteResponse = new BookmarkDeleteResponse(userId, auctionId, true);
//
// 		when(bookmarkService.deleteBookmark(anyLong(), anyString())).thenReturn(bookmarkDeleteResponse);
// 		//when
// 		ResultActions resultActions = mockMvc.perform(
// 			delete("/bookmark/{auction-id}", auctionId)
// 				.header("userId", userId)
// 				.accept(MediaType.APPLICATION_JSON));
// 		//then
// 		resultActions.andExpect(status().isOk())
// 			.andDo(
// 				document("bookmark-delete",
// 					pathParameters(
// 						parameterWithName("auction-id").description("bookmark ????????? auction ?????????")
// 					),
// 					responseFields(
// 						fieldWithPath("userId").type(JsonFieldType.STRING).description("????????? user??? ?????????"),
// 						fieldWithPath("auctionId").type(JsonFieldType.NUMBER)
// 							.description("????????? accommodation??? ?????????"),
// 						fieldWithPath("state").type(JsonFieldType.BOOLEAN)
// 							.description("????????? bookmark??? ?????? false : ??????, true : ??????")
// 					)
// 				)).andDo(print());
// 	}
// }