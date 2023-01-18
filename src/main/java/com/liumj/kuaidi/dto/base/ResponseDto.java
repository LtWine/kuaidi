package com.liumj.kuaidi.dto.base;


import com.liumj.kuaidi.exception.ResponseCode;
import lombok.Data;

@Data
public class ResponseDto<T> {
	private String msg;
	private int code;
	private T data;
	private static final ResponseDto<String> SUCEESSS = new ResponseDto<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), "");

	public ResponseDto(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}


	public static ResponseDto<String> success() {
		return SUCEESSS;
	}

	public static <T> ResponseDto<T> success(T data) {
		return new ResponseDto<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
	}

	public static <T> ResponseDto<T> fail(ResponseCode responseCode, String message, T data) {
		return new ResponseDto<>(responseCode.getCode(), message, data);
	}

	public static ResponseDto<String> fail(ResponseCode responseCode, String message) {
		return new ResponseDto<>(responseCode.getCode(), message, "");
	}

	public static ResponseDto<String> fail(ResponseCode responseCode) {
		return new ResponseDto<>(responseCode.getCode(), responseCode.getMessage(), "");
	}
}
