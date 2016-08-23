package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {

	public TaotaoResult getItemParamByCid(Long cid);
	public TaotaoResult insertItemParam(Long cid,String paramData);
	
}
