package com.liumj.kuaidi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ltwine
 */
@Getter
@RequiredArgsConstructor
public enum KuaiNiaoStatus {
	/**
	 *
	 */
	STATUS_0(0,"暂无轨迹信息"),
	STATUS_1(1,"已揽收"),
	STATUS_10(10,"待揽件"),
	STATUS_201(201,"到达派件城市"),
	STATUS_202(202,"派件中"),
	STATUS_211(211,"已放入快递柜或驿站"),
	STATUS_301(301,"正常签收"),
	STATUS_302(302,"派件异常后最终签收"),
	STATUS_304(304,"代收签收"),
	STATUS_311(311,"快递柜或驿站签收"),
	STATUS_4(4, "在路上"),
	STATUS_401(401, "发货无信息"),
	STATUS_402(402, "超时未签收"),
	STATUS_403(403, "超时未更新"),
	STATUS_404(404, "拒收(退件)"),
	STATUS_405(405, "派件异常"),
	STATUS_406(406, "退货签收"),
	STATUS_407(407, "退货未签收"),
	STATUS_412(412, "快递柜或驿站超时未取"),
	STATUS_413(413, "单号已拦截"),


	STATUS_1000(1000, "退货中"),

	;
	private final Integer code;
	private final String name;

	public static String checkMatchStatus(Integer statsEx) {
		for (KuaiNiaoStatus value : values()) {
			if (value.getCode().equals(statsEx)) {
				return value.getName();
			}
		}
		return "";
	}
}
