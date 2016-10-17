package com.milan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.milan.service.CheckCodeService;

/**
 * 验证码生成控制器
 * @author Milan.Ma
 * @since 2016-09-26
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/checkcode")
public class CheckCodeController {
	
	@Autowired
	private CheckCodeService checkCodeService;
	
	@RequestMapping(value = "/page.do", method = RequestMethod.GET)
	public String page() {
		return "checkcode";
	}

	@RequestMapping(value = "/create.do")
	public void createCheckCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		checkCodeService.createCodeByKaptcha(request, response);
	}
}
