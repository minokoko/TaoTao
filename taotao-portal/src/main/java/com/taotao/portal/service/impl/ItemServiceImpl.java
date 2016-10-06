package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_ITEM_BASE_URL}")
	private String REST_ITEM_BASE_URL;
	
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;

	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL;
	
	/**
	 * 根据商品Id查询商品基本信息
	 */
	@Override
	public TbItem getItemById(Long itemId) {
		
		//根据商品id查询商品基本信息
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_BASE_URL+itemId);
		
		//将json数据类型转换成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, PortalItem.class);
		
		//取商品对象
		TbItem item =(TbItem) taotaoResult.getData();
		
		return item;
	}
	
	
	/**
	 * 根据商品ID调用taotao-rest服务获得商品描述
	 */
	@Override
	public String getItemDescById(Long itemId) {
		
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_DESC_URL+itemId);
		
		//将json数据转换成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
		
		//取商品描述
		TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
		
		String desc = itemDesc.getItemDesc();
		
		return desc;
	}
	
	
	/**
	 * 根据商品id查询商品参数
	 */
	@Override
	public String getItemParamById(Long itemId) {
		
		//调用服务，得到的是json数据
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_PARAM_URL+itemId);
		
		//将json数据转换成java对象
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
		
		//取规格参数
		TbItemParamItem itemParam  =(TbItemParamItem) taotaoResult.getData();
		
		String paramData = itemParam.getParamData();
		
		//把规格参数的json数据转换成java对象
		List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
		// 遍历list，生成html
		StringBuffer sb = new StringBuffer();

		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : mapList) {
			sb.append("		<tr>\n");
			sb.append("			<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
			sb.append("		</tr>\n");
			// 取规格项
			List<Map> mapList2 = (List<Map>) map.get("params");
			for (Map map2 : mapList2) {
				sb.append("		<tr>\n");
				sb.append("			<td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
				sb.append("			<td>" + map2.get("v") + "</td>\n");
				sb.append("		</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");

		return sb.toString();
	}

}
