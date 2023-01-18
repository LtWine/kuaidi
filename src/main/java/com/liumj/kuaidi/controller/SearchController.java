package com.liumj.kuaidi.controller;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.liumj.kuaidi.dto.base.ResponseDto;
import com.liumj.kuaidi.dto.req.SearchByNumReq;
import com.liumj.kuaidi.dto.resp.QueryResultResp;
import com.liumj.kuaidi.exception.BusinessException;
import com.liumj.kuaidi.exception.ResponseCode;
import com.liumj.kuaidi.service.ExcelService;
import com.liumj.kuaidi.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ltwine
 */
@Api(tags = "search", produces = "search")
@RestController
@RequiredArgsConstructor
public class SearchController {
	private final SearchService searchService;
	private final ExcelService excelService;

	@PostMapping(value = "/searchByNum")
	@ApiOperation("searchByNum")
	public ResponseDto<List<QueryResultResp>> searchByNum(@RequestBody SearchByNumReq searchByNumReq) {
		return ResponseDto.success(searchService.search(searchByNumReq.getTrackInfos()));
	}

	@PostMapping(value = "/searchByExcel")
	@ApiOperation(value = "searchByExcel")
	public void searchByExcel(@RequestPart("file") MultipartFile file, HttpServletResponse response) throws Exception {
		if (file.isEmpty()) {
			throw new BusinessException(ResponseCode.FAIL, "请上传excel文件");
		}
		InputStream inputStream = file.getInputStream();
		List<SearchByNumReq.SearchDetail> trackNums = excelService.readTrackNumFromExcel(inputStream);
		SearchByNumReq searchByNumReq = new SearchByNumReq();
		searchByNumReq.setTrackInfos(trackNums);
		ResponseDto<List<QueryResultResp>> listResponseDto = searchByNum(searchByNumReq);
		List<QueryResultResp> data = listResponseDto.getData();

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
		String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
		EasyExcel.write(response.getOutputStream(), QueryResultResp.class).sheet("1").doWrite(data);
	}
}
