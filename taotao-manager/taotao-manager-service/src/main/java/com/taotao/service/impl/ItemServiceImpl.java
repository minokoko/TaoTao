package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;
/**
 * 根据Item的ID查询Item
 * @author jessyon
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Override
	public TbItem getItemById(Long itemId) {
		//条件查询
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		
		List<TbItem> list = itemMapper.selectByExample(example);
		
		TbItem tbItem=null;
		if(list!=null && list.size() > 0){
			 tbItem = list.get(0);
		}
		return tbItem;
	}

}
