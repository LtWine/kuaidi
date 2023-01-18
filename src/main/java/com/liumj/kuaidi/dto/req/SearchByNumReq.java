package com.liumj.kuaidi.dto.req;

import java.util.List;

import lombok.Data;

/**
 * @author ltwine
 */
@Data
public class SearchByNumReq {
	private List<SearchDetail> trackInfos;

	@Data
	public static class SearchDetail{
		private String company;
		private String trackNum;
		private String customerName;
	}
}
