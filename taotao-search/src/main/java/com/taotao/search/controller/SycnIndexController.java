package com.taotao.search.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.service.SycnIndexService;

@Controller
public class SycnIndexController {

	@Autowired
	private SycnIndexService sycnIndexService;
	
	//缓存同步，将redis中的数据删除
		@RequestMapping("/sync/index/{id}")
		@ResponseBody
		public TaotaoResult sysncContent(@PathVariable Long id){
			try {
				TaotaoResult reuslt = sycnIndexService.syncIndex(id);
				System.out.println("同步索引成功");
				return reuslt;
			} catch (Exception e) {
				e.printStackTrace();
				return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
			}
		}
	
}
