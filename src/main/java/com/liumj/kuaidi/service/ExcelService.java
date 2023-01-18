package com.liumj.kuaidi.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.liumj.kuaidi.dto.req.SearchByNumReq;

import org.springframework.stereotype.Service;

/**
 * @author ltwine
 */
@Service
public class ExcelService {

	public List<SearchByNumReq.SearchDetail> readTrackNumFromExcel(InputStream inputStream) {
		ExcelReaderBuilder read = EasyExcel.read(inputStream);
		ExcelReaderSheetBuilder sheet = read.sheet(0);
		List<Object> objects = sheet.doReadSync();
		Set<SearchByNumReq.SearchDetail> resultSet = new LinkedHashSet<>();
		for (Object object : objects) {
			LinkedHashMap line = (LinkedHashMap) object;
			Object company = line.get(0);
			Object trackNum = line.get(1);
			Object customerNum = line.get(2);
			SearchByNumReq.SearchDetail searchDetail = new SearchByNumReq.SearchDetail();
			searchDetail.setTrackNum(String.valueOf(trackNum));
			searchDetail.setCompany(String.valueOf(company));
			if (customerNum != null) {
				searchDetail.setCustomerName(String.valueOf(customerNum));
			}
			resultSet.add(searchDetail);
		}
		return new ArrayList<>(resultSet);
	}

	//	public static void main(String[] args) {
	//		BufferedInputStream inputStream = FileUtil.getInputStream("/Users/liumeijian/Soft/gqproject/kuaidi/kuaidi1.xlsx");
	//		List<String> strings = readTrackNumFromExcel(inputStream);
	//		System.out.println();
	//	}
}
