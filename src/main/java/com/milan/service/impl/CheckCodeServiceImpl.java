package com.milan.service.impl;

import java.awt.Color;
import java.awt.Font;
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
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Producer;
import com.milan.service.CheckCodeService;
import com.octo.captcha.service.image.ImageCaptchaService;

@Service("checkCodeService")
public class CheckCodeServiceImpl implements CheckCodeService{
	
	@Autowired
	private ImageCaptchaService  captchaService;
	
	@Autowired
	private Producer captchaProducer;

	public void createCodeByJcaptcha(HttpServletRequest request,
			HttpServletResponse response) {
		// 生成图片存放到session中
		BufferedImage bi = captchaService.getImageChallengeForID(request
				.getSession().getId(), request.getLocale());
		// 图片输出
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		ServletOutputStream out = null;
		try {
			ImageIO.write(bi, "JPG", jpegOutputStream);
			response.setContentType("image/jpeg");
			out = response.getOutputStream();
			out.write(jpegOutputStream.toByteArray());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void createCodeByKaptcha(HttpServletRequest request,
			HttpServletResponse response) {
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
        ServletOutputStream out = null;
        try {
        	out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch(Exception e){
			
		}finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void createCodeByGraphics(HttpServletRequest request,
			HttpServletResponse response) {
		response.setDateHeader("Expires", 0);     
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");    
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");    
        response.setHeader("Pragma", "no-cache");    
        response.setContentType("image/jpeg");   
        
		// 定义BufferedImage
		BufferedImage bi = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);

		// 绘图类
		Graphics graphics = bi.getGraphics();
		// 画一个矩形框
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 90, 38);

		// 生成图片内容
		Random random = new Random();
		char[] chars = "ABCDEFGHILMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		int len = chars.length;
		int index = 0;
		StringBuffer captcha = new StringBuffer();
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		for (int i = 0; i < 4; i++) {
			index = random.nextInt(len);
			// 生成颜色
			graphics.setColor(new Color(random.nextInt(255),
					random.nextInt(255), random.nextInt(255)));
			graphics.setFont(font);
			graphics.drawString(chars[index] + "", (i * 20) + 3, 20+random.nextInt(10));// 填充文字
			captcha.append(chars[index]);
		}

		// 放到session中,用于验证
		request.getSession().setAttribute("checkcode", captcha.toString());

		// 图片输出
		try {
			ImageIO.write(bi, "JPG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
