package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 商品查询Controller
 * @author jessyon
 *
 */
@Controller
public class ItemController {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Value("${SEARCH_INDEX_SYNC_URL}")
	private String SEARCH_INDEX_SYNC_URL;
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("item/{itemId}")
	@ResponseBody
	private TbItem getItemById(@PathVariable Long itemId){
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
	
	@RequestMapping(value="item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item,String desc,String itemParams){
		return itemService.createItem(item, desc,itemParams);
	}

	@RequestMapping(value="item/update",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult update(TbItem item,String desc,String itemParams){
		TaotaoResult result = itemService.updateItem(item, desc,itemParams);
		
		//修改成功，修改索引库中的索引值

		
		HttpClientUtil.doGet(SEARCH_BASE_URL+SEARCH_INDEX_SYNC_URL+item.getId());
		System.out.println(SEARCH_BASE_URL+SEARCH_INDEX_SYNC_URL+item.getId());
		return result.ok();
	}
	
	@RequestMapping("/page/item/{itemId}")
	public String showItemParam(@PathVariable Long itemId,Model model){
		String myhtml = itemService.getItemParamHtml(itemId);
		model.addAttribute("myhtml",myhtml);
		return "itemparam";
	}
	
}
