package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

/**
 * 内容管理Service
 * 
 * @author jessyon
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentList(Long parentId) {

		// 按条件查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();

		// 查询条件为parentId
		criteria.andParentIdEqualTo(parentId);

		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

		// 转换成EasyUITreeNode列表
		List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
		
		for (TbContentCategory tbContentCategory : list) {
			
			EasyUITreeNode treeNode= new EasyUITreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			//如果没有子节点为open,如果有子节点为closed
			treeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(treeNode);
			
		}

		return resultList;
	}

}