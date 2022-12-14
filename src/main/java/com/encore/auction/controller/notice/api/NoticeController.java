package com.encore.auction.controller.notice.api;

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

import com.encore.auction.controller.notice.requests.NoticeRegisterRequest;
import com.encore.auction.controller.notice.requests.NoticeUpdateRequest;
import com.encore.auction.controller.notice.responses.NoticeDeleteResponse;
import com.encore.auction.controller.notice.responses.NoticeDetailsResponse;
import com.encore.auction.controller.notice.responses.NoticeIdResponse;
import com.encore.auction.service.notice.NoticeService;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

	private final NoticeService noticeService;

	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@PostMapping
	public ResponseEntity<NoticeIdResponse> registerNotice(@RequestHeader("Token") String token,
		@Valid @RequestBody NoticeRegisterRequest noticeRegisterRequest) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(noticeService.registerNotice(token, noticeRegisterRequest));
	}

	@GetMapping("/{notice-id}")
	public ResponseEntity<NoticeDetailsResponse> retrieveNotice(@PathVariable("notice-id") Long noticeId) {
		return ResponseEntity.ok().body(noticeService.retrieveNotice(noticeId));
	}

	@PutMapping("/{notice-id}")
	public ResponseEntity<NoticeIdResponse> updateNotice(@PathVariable("notice-id") Long noticeId,
		@RequestHeader("Token") String token,
		@Valid @RequestBody NoticeUpdateRequest noticeUpdateRequest) {
		return ResponseEntity.ok().body(noticeService.updateNotice(noticeId, token, noticeUpdateRequest));
	}

	@DeleteMapping("/{notice-id}")
	public ResponseEntity<NoticeDeleteResponse> deleteNotice(@PathVariable("notice-id") Long noticeId,
		@RequestHeader("Token") String token) {
		return ResponseEntity.ok().body(noticeService.deleteNotice(noticeId, token));
	}
}
