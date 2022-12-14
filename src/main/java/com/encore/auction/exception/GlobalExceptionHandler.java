package com.encore.auction.exception;

import org.hibernate.LazyInitializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.encore.auction.exception.response.ExceptionResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NonExistResourceException.class)
	protected ResponseEntity<ExceptionResponse> handleNoSuchElementFoundException(NonExistResourceException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Non Exist Resource Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}

	@ExceptionHandler(WrongRequestException.class)
	protected ResponseEntity<ExceptionResponse> wrongRequestException(WrongRequestException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Wrong Request Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(WrongTimeException.class)
	protected ResponseEntity<ExceptionResponse> wrongTimeException(WrongTimeException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Wrong Time Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ExceptionResponse> validException(MethodArgumentNotValidException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Validation Exception - Bad Request")
			.message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(MalformedJwtException.class)
	protected ResponseEntity<ExceptionResponse> malformedJwtException(MalformedJwtException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Malformed Jwt Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ExceptionResponse> illegalArgumentException(IllegalArgumentException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Illegal Argument Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(SignatureException.class)
	protected ResponseEntity<ExceptionResponse> signatureException(SignatureException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("JWT Signature Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	protected ResponseEntity<ExceptionResponse> expiredJwtException(ExpiredJwtException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("JWT Expired Jwt Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler(SchedulerThreadException.class)
	protected ResponseEntity<ExceptionResponse> schedulerThreadException(SchedulerThreadException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Scheduler Thread Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	@ExceptionHandler(LazyInitializationException.class)
	protected ResponseEntity<ExceptionResponse> lazyInitializationException(LazyInitializationException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Lazy Initialization Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ExceptionResponse> runtimeException(RuntimeException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Runtime Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	protected ResponseEntity<ExceptionResponse> missingRequestHeaderException(MissingRequestHeaderException e) {
		final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
			.code("Missing Request Header Exception")
			.message(e.getMessage())
			.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}
}
