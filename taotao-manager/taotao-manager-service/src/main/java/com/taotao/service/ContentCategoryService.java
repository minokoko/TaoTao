package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	public List<EasyUITreeNode> getContentList(Long parentId);
	public TaotaoResult insertCategory(Long parentId,String name);
	public void updateCategoryName(Long id,String name);
	public void deleteById(Long parentId,Long id);
	
}
