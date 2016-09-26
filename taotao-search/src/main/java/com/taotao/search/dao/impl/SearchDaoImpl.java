package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;

/*
 * solr查询dao
 */
@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;

	@Override
	public SearchResult search(SolrQuery query) throws SolrServerException {
		
			//执行查询
			QueryResponse response = solrServer.query(query);
			
			//取查询结果
			SolrDocumentList results = response.getResults();
			
			//创建列表
			List<SearchItem> itemList = new ArrayList<SearchItem>();
			
			for (SolrDocument result : results) {
				//创建一个SearchItem对象
				SearchItem item = new SearchItem();
				
				item.setCategory_name(result.get("item_category_name").toString());
				item.setId(result.get("id").toString());
				item.setImage(result.get("item_image").toString());
				item.setPrice(Long.parseLong(result.get("item_price").toString()));
				item.setSell_point(result.get("item_sell_point").toString());
				
				
				//取高亮显示
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				
				List<String> list = highlighting.get(result.get("id")).get("item_title");
				
				String itemTitle ="";
				
				if(list!=null && list.size()>0){
					//取高亮后的结果
					itemTitle = list.get(0);
				}else{
					itemTitle =result.get("item_title").toString();
				}
				
				item.setTitle(itemTitle);
				
				//添加到列表
				itemList.add(item);
			}
			//查询结果
			SearchResult result = new SearchResult();
			
			//商品列表
			result.setItemList(itemList);
			
			//查询结果总记录条数
			result.setRecordCount(results.getNumFound());
			
			return result;
		
	}

}
