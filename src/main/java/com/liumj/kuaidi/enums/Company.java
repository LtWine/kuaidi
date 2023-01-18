package com.liumj.kuaidi.enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.liumj.kuaidi.dto.third.CompanyInfo;
import com.liumj.kuaidi.dto.third.KuaidiNiaoInfo;
import com.liumj.kuaidi.service.kuainiao.KuaiNiaoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.liumj.kuaidi.service.kuainiao.KuaiNiaoService.URL;
import static com.liumj.kuaidi.service.kuainiao.KuaiNiaoService.USERID;

/**
 * @author ltwine
 */
@Getter
@RequiredArgsConstructor
public enum Company {
	/**
	 *
	 */
	YTO("圆通速递", "YTO", Arrays.asList(".*圆通.*")),
	ZTO("中通快递", "ZTO", Arrays.asList(".*中通.*")),
	JD("京东快递", "JD", Arrays.asList(".*京东.*")),
	JTSD("极兔速递", "JTSD", Arrays.asList(".*极兔.*")),
	SF("顺丰速运", "SF", Arrays.asList(".*顺丰.*")),
	YD("韵达速递", "YD", Arrays.asList(".*韵达.*")),
	FWX("丰网速运", "FWX", Arrays.asList(".*丰网.*")),
	HTKY("百世快递", "HTKY", Arrays.asList(".*百世.*")),
	STO("申通快递", "STO", Arrays.asList(".*申通.*")),
	YZPY("邮政快递包裹", "YZPY", Arrays.asList(".*邮政快递包裹.*")),
	EMS("EMS", "EMS", Arrays.asList(".*EMS.*")),
	ZJS("宅急送", "ZJS", Arrays.asList(".*宅急送.*")),
	HHTT("天天", "HHTT", Arrays.asList(".*天天.*")),
	;

	private final String name;
	private final String code;
	private final List<String> regs;

	public static String getCode(String company) {
		Company[] values = values();
		for (Company value : values) {
			Optional<String> first = value.getRegs().stream()
					.filter(reg -> ReUtil.isMatch(reg, company))
					.findFirst();
			if (first.isPresent()) {
				return value.getCode();
			}
		}
		return "";
	}

}
