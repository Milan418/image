package com.milan.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CheckCodeService {
	
	/**
	 * 使用Jcaptcha开源组件实现验证码生成
	 * @param request
	 * @param response
	 */
	void createCodeByJcaptcha(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 使用Kaptcha开源组件实现验证码生成
	 * @param request
	 * @param response
	 */
	void createCodeByKaptcha(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Graphics生成验证码
	 * @param request
	 * @param response
	 */
	void createCodeByGraphics(HttpServletRequest request, HttpServletResponse response);

}
