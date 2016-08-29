package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * 商品分类列表查询
 * 
 * @author jessyon
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public ItemCatResult getItemCatList() {

		// 调用递归方法查询商品分类列表
		List catList = getItemCatList((long) 0);
		ItemCatResult result = new ItemCatResult();
		result.setData(catList);
		return result;
	}

	private List getItemCatList(Long parentId) {
		// 根据parentId查询列表
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList<>();
		int index= 0;
		for (TbItemCat tbItemCat : list) {
			
			if(index >13) break;
			
			if (tbItemCat.getIsParent()) {
				// 如果不是叶子节点
				CatNode node = new CatNode();
				node.setUrl("/products/" + tbItemCat.getId() + ".html");
				if (tbItemCat.getParentId() == 0) {
					// 第一层的根节点
					node.setName("<a href='/producets/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
					//第一级节点个数不能超过14个
					index++;
				} else {
					// 非根节点
					node.setName(tbItemCat.getName());
				}
				// 调用自己，返回的结果为list
				node.setItems(getItemCatList(tbItemCat.getId()));
				// 把node添加到列表
				resultList.add(node);
			} else {
				// 如果是叶子节点
				String itemLeaf = "/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
				resultList.add(itemLeaf);
			}

		}

		return resultList;

	}

}