package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {

	TbItem getItemById(Long itemId);
	TbItemDesc getItemDescById(Long itemId);
	
}
