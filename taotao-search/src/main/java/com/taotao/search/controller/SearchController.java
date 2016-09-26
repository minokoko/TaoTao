package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * 发布搜索服务
 * @author jessyon
 *
 */
@Controller
public  class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/q")
	@ResponseBody
	public TaotaoResult search(@RequestParam (defaultValue="")   String keyword,
							   @RequestParam (defaultValue="1")  Integer page,
							   @RequestParam (defaultValue="30") Integer rows){
		
		try {
			//转换字符集
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
			
			SearchResult searchResult = searchService.search(keyword, page, rows);
			return TaotaoResult.ok(searchResult);
		} catch (SolrServerException e) {
		
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return null;	
		
	}
	
}
