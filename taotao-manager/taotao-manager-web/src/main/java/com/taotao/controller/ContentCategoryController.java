package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类管理Controller 
 * @author jessyon
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0") Long parentId){
		return contentCategoryService.getContentList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createNode(Long parentId,String name){
		return contentCategoryService.insertCategory(parentId, name);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public void updateNodeName(Long id,String name){
		contentCategoryService.updateCategoryName(id, name);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public void delete(Long parentId,Long id){
		contentCategoryService.deleteById(parentId, id);
	}
	
}
