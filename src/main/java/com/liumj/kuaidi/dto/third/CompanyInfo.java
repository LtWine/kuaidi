package com.liumj.kuaidi.dto.third;

import java.util.List;

import lombok.Data;

/**
 * @author ltwine
 */
@Data
public class CompanyInfo {
	private Integer EBusinessID;
	private Boolean Success;
	private String LogisticCode;
	private List<Shipper> Shippers;

	@Data
	public static class Shipper {
		private String ShipperName;
		private String ShipperCode;
	}
}
