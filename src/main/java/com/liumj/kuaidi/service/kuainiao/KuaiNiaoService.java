package com.liumj.kuaidi.service.kuainiao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.liumj.kuaidi.dto.req.SearchByNumReq;
import com.liumj.kuaidi.dto.third.KuaidiNiaoInfo;
import com.liumj.kuaidi.enums.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import static com.liumj.kuaidi.enums.KuaiNiaoStatus.STATUS_1000;
import static com.liumj.kuaidi.enums.KuaiNiaoStatus.STATUS_404;
import static com.liumj.kuaidi.enums.KuaiNiaoStatus.STATUS_407;
import static com.liumj.kuaidi.enums.KuaiNiaoStatus.STATUS_413;

/**
 * @author ltwine
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KuaiNiaoService {

	public static final String URL = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
	public static final String USERID = "1787686";
	private static final String API_KEY = "e063c8ee-171c-440b-aa97-4cf041dd8cc1";
	private final Executor executor;


	public List<KuaidiNiaoInfo> search(List<SearchByNumReq.SearchDetail> trackNums) {
		List<KuaidiNiaoInfo> result = new ArrayList<>();
		List<CompletableFuture<KuaidiNiaoInfo>> futures = new ArrayList<>();
		for (SearchByNumReq.SearchDetail searchDetail : trackNums) {
			CompletableFuture<KuaidiNiaoInfo> future = CompletableFuture.supplyAsync(() -> queryByDetail(searchDetail), executor);
			futures.add(future);
		}
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		for (CompletableFuture<KuaidiNiaoInfo> future : futures) {
			try {
				result.add(future.get());
			}
			catch (Exception e) {
				log.error("异步处理异常", e);
			}
		}
		return result;
	}

	private KuaidiNiaoInfo queryByDetail(SearchByNumReq.SearchDetail searchDetail) {
		Map<String, Object> params = new LinkedHashMap<>();
		String code = Company.getCode(searchDetail.getCompany());
		if (StringUtils.isBlank(code)) {
			log.error("快递公司不存在 {}", JSON.toJSONString(searchDetail));
			return null;
		}

		String customerName = searchDetail.getCustomerName();
		if (StringUtils.isNotBlank(customerName)) {
			params.put("CustomerName", StringUtils.substring(customerName, -4));
		}

		params.put("ShipperCode", code);
		params.put("LogisticCode", searchDetail.getTrackNum());
		Map<String, Object> paramMap = new LinkedHashMap<>();
		String data = JSON.toJSONString(params);
		paramMap.put("RequestData", data);
		paramMap.put("EBusinessID", USERID);
		paramMap.put("RequestType", 8001);
		paramMap.put("DataSign", getSign(data));
		paramMap.put("DataType", 2);
		String resp = HttpRequest.post(URL)
				.header(Header.USER_AGENT, Header.USER_AGENT.getValue())
				.contentType("application/x-www-form-urlencoded;charset=utf-8")
				.form(paramMap)//表单内容
				.timeout(2000)//超时，毫秒
				.execute().body();
		KuaidiNiaoInfo kuaidiNiaoInfo = JSON.parseObject(resp, KuaidiNiaoInfo.class);
		checkExistBackStatus(kuaidiNiaoInfo);
		return kuaidiNiaoInfo;
	}


	private void checkExistBackStatus(KuaidiNiaoInfo kuaidiNiaoInfo) {
		if (kuaidiNiaoInfo.getState() != 4) {
			for (KuaidiNiaoInfo.Trace trace : kuaidiNiaoInfo.getTraces()) {
				if (Arrays.asList(STATUS_404.getCode(), STATUS_407.getCode(), STATUS_413.getCode()).contains(trace.getAction())) {
					kuaidiNiaoInfo.setStateEx(STATUS_1000.getCode());
					break;
				}
			}
		}
	}

	public static String getSign(String data) {
		data += API_KEY;
		Digester md5 = new Digester(DigestAlgorithm.MD5);
		String digest = md5.digestHex(data);
		String encode = Base64.encode(digest);
		return URLEncodeUtil.encode(encode);
	}

	public static void main(String[] args) {
		//		List<KuaidiNiaoInfo> result = search(Arrays.asList("YT1683175267694","YT1683179123631","YT1683196369422","JT2917392869902","JT2917383157447"));
		//		for (KuaidiNiaoInfo kuaidiNiaoInfo : result) {
		//			System.out.println(kuaidiNiaoInfo);
		//		}

		//		String sign = getSign("{'OrderCode':'','ShipperCode':'SF','LogisticCode':'118954907573'}56da2cf8-c8a2-44b2-b6fa-476cd7d1ba17");
		//		System.out.println(sign);
	}

}
