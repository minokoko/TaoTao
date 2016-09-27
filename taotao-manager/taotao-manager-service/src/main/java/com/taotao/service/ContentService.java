package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	public EasyUIDataGridResult getContentListById(int page,int rows,Long categoryId);
	public TaotaoResult insertContent(TbContent content);
	
}
