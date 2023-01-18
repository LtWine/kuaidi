package com.liumj.kuaidi.dto.third;

import java.util.List;

import lombok.Data;

/**
 * @author ltwine
 */
@Data
public class KuaidiNiaoInfo {
	private Integer EBusinessID;
	private String ShipperCode;
	private String LogisticCode;
	private Integer State;
	private Integer StateEx;
	private String Location;
	private List<Trace> Traces;

	@Data
	public static class Trace {
		private Integer Action;
		private String AcceptStation;
		private String AcceptTime;
		private String Location;
	}
}
