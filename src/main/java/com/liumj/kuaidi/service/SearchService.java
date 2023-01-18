package com.liumj.kuaidi.service;

import java.util.List;

import com.liumj.kuaidi.dto.req.SearchByNumReq;
import com.liumj.kuaidi.dto.resp.QueryResultResp;

/**
 * @author ltwine
 */
public interface SearchService {
	List<QueryResultResp> search(List<SearchByNumReq.SearchDetail> trackNums);
}
