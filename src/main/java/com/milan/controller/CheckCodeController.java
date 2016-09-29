package com.milan.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Producer;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 验证码生成控制器
 * 
 * @author Milan.Ma
 * @since 2016-09-26
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/checkcode")
public class CheckCodeController {
	
	@Autowired
	private ImageCaptchaService  captchaService;
	
	@Autowired
	private Producer captchaProducer;

	@RequestMapping(value = "/page.do", method = RequestMethod.GET)
	public String page() {
		return "checkcode";
	}

	@RequestMapping(value = "/create.do")
	public void createCheckCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		createCodeByKaptcha(request, response);
	}
	
	/**
	 * 使用Kaptcha开源组件实现验证码生成
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void createCodeByKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setDateHeader("Expires", 0);     
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");    
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");    
        response.setHeader("Pragma", "no-cache");    
        response.setContentType("image/jpeg");   
        
        //生成验证码
        String captcha = captchaProducer.createText();
        //放到session中
        request.getSession().setAttribute("captcha", captcha);
        //输出
        BufferedImage bi = captchaProducer.createImage(captcha);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
			out.flush();
		} finally {
			out.close();
		}
	}
	
	/**
	 * 使用Jcaptcha开源组件实现验证码生成
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void createCodeByJcaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//生成图片存放到session中
		BufferedImage bi = captchaService.getImageChallengeForID(request.getSession().getId(), request.getLocale());
		// 图片输出
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		ImageIO.write(bi, "JPG", jpegOutputStream);
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		out.write(jpegOutputStream.toByteArray());
		out.flush();
		out.close();
	}

	/**
	 * Graphics生成验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void createCodeByGraphics(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 定义BufferedImage
		BufferedImage bi = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);

		//绘图类
		Graphics graphics = bi.getGraphics();
		//画一个矩形框
		Color color = new Color(200, 150, 255);//矩形框颜色
		graphics.setColor(color);
		graphics.fillRect(0, 0, 68, 22);

		// 生成图片内容
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		Random random = new Random();
		int len = chars.length;
		int index = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			index = random.nextInt(len);
			// 生成颜色
			graphics.setColor(new Color(random.nextInt(100), random.nextInt(80), random.nextInt(255)));
			graphics.drawString(chars[index] + "", (i * 15) + 3, 18);//填充文字
			sb.append(chars[index]);
		}

		// 放到session中,用于验证
		request.getSession().setAttribute("checkcode", sb.toString());

		// 图片输出
		ImageIO.write(bi, "JPG", response.getOutputStream());
	}
	
	

}
