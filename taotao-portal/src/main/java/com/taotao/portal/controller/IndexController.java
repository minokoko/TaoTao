package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 访问首页controller
 * @author jessyon
 *
 */
@Controller
public class IndexController {

	@RequestMapping("/index")
	public String showIndex(){
		return "index";
	}
}
