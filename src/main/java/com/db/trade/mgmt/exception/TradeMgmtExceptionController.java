package com.db.trade.mgmt.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class TradeMgmtExceptionController extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = LowerVersionTradeRequestException.class)
	public ResponseEntity<Object> handleLowerVersionTradeException(LowerVersionTradeRequestException ex , WebRequest req) {
		ErrorDetails error = new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(value = MaturityDatePastException.class)
	public ResponseEntity<Object> handleMaturityDateException(MaturityDatePastException ex , WebRequest req) {
		ErrorDetails error = new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
