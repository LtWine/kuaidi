package com.liumj.kuaidi.exception;


/**
 */
public class BusinessException extends RuntimeException {

	private final int code;

	public BusinessException(String message, int code) {
		super(message);
		this.code = code;
	}

	public BusinessException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.code = responseCode.getCode();
	}

	public BusinessException(ResponseCode responseCode, String message) {
		super(message);
		this.code = responseCode.getCode();
	}

	public int getCode() {
		return code;
	}
}
