package com.liumj.kuaidi.dto.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author ltwine
 */
@Data
public class QueryResultResp {

	@ExcelProperty("快递号")
	private String trackNum;
	@ExcelProperty("状态")
	private String statusName;
	@ExcelProperty("仓库")
	private String storehouse;
}
