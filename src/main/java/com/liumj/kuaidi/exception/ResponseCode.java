package com.liumj.kuaidi.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
	/**
	 * 系统级
	 */
	SUCCESS(0, "成功"),
	INIT_ERROR(1, "初始化失败"),
	FAIL(100, "服务器忙，请稍后再试"),
	VALID_EXCEPTION(101, "参数校验异常"),
	DUPLICATE_REQUEST(102, "重复的请求"),
	DATA_ERROR(103, "数据异常"),
	CONNECT_ERROR(105, "服务器繁忙"),
	REQUEST_THIRD_ERROR(106, "请求第三方接口异常"),
	;
	/**
	 * 业务级
	 */
	private final Integer code;
	private final String message;

}
