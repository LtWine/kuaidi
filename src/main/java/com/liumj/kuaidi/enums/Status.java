package com.liumj.kuaidi.enums;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.liumj.kuaidi.enums.KuaiNiaoStatus.STATUS_1000;

/**
 * @author ltwine
 */
@Getter
@RequiredArgsConstructor
public enum Status {

	/**
	 *
	 */
	SIGN_FOR_CUSTOMER(1, "签收", Arrays.asList(301, 302, 304, 311)),
	ON_ROAD(2, "在路上", Arrays.asList(2, 201, 202, 211)),
	RETURNING(3, "退回中", Arrays.asList(STATUS_1000.getCode())),
	SIGN_FOR_SALEER(4, "卖家签收", Arrays.asList(406)),
	;
	private final Integer status;
	private final String statusName;
	private final List<Integer> statusExs;

	public static Status checkMatchStatus(Integer statsEx) {
		for (Status value : values()) {
			List<Integer> statusExs = value.getStatusExs();
			for (Integer status : statusExs) {
				if (status.equals(statsEx)) {
					return value;
				}
			}
		}
		return null;
	}

}
