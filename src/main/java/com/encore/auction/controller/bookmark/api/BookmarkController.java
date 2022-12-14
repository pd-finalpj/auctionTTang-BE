package com.encore.auction.controller.bookmark.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encore.auction.controller.bookmark.requests.BookmarkRegisterRequest;
import com.encore.auction.controller.bookmark.responses.BookmarkCountResponse;
import com.encore.auction.controller.bookmark.responses.BookmarkDeleteResponse;
import com.encore.auction.controller.bookmark.responses.BookmarkRegisterResponse;
import com.encore.auction.service.bookmark.BookmarkService;
import com.encore.auction.utils.security.Permission;

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

	private final BookmarkService bookmarkService;

	public BookmarkController(BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}

	@Permission
	@PostMapping
	public ResponseEntity<BookmarkRegisterResponse> registerBookmark(
		@RequestBody BookmarkRegisterRequest bookmarkRegisterRequest, @RequestHeader("Token") String token) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(bookmarkService.registerBookmark(bookmarkRegisterRequest, token));
	}

	@Permission
	@DeleteMapping("/{auction-id}")
	public ResponseEntity<BookmarkDeleteResponse> deleteBookmark(
		@PathVariable("auction-id") Long auctionId,
		@RequestHeader("Token") String token) {
		return ResponseEntity.ok().body(bookmarkService.deleteBookmark(auctionId, token));
	}

	@Permission
	@GetMapping
	public ResponseEntity<BookmarkCountResponse> countBookmark(@RequestHeader("Token") String token) {
		return ResponseEntity.ok().body(bookmarkService.countBookmark(token));
	}
}
