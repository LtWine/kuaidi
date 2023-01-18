package com.liumj.kuaidi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.liumj.kuaidi.dto.req.SearchByNumReq;
import com.liumj.kuaidi.dto.resp.QueryResultResp;
import com.liumj.kuaidi.dto.third.KuaidiNiaoInfo;
import com.liumj.kuaidi.enums.KuaiNiaoStatus;
import com.liumj.kuaidi.enums.Status;
import com.liumj.kuaidi.service.SearchService;
import com.liumj.kuaidi.service.kuainiao.KuaiNiaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import static com.liumj.kuaidi.enums.Status.checkMatchStatus;

/**
 * @author ltwine
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
	private final KuaiNiaoService kuaiNiaoService;

	@Override
	public List<QueryResultResp> search(List<SearchByNumReq.SearchDetail> trackNums) {
		List<KuaidiNiaoInfo> search = kuaiNiaoService.search(trackNums);
		return search.stream().map(dto -> {

			QueryResultResp queryResultResp = new QueryResultResp();
			queryResultResp.setTrackNum(dto.getLogisticCode());
			Integer stateEx = dto.getStateEx();
			Status status = checkMatchStatus(stateEx);
			if (status == null) {
				String name = KuaiNiaoStatus.checkMatchStatus(stateEx);
				queryResultResp.setStatusName(name);
			}
			else {
				queryResultResp.setStatusName(status.getStatusName());
			}
			queryResultResp.setStorehouse(dto.getLocation());
			return queryResultResp;
		}).collect(Collectors.toList());
	}
}
