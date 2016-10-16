package com.milan.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.milan.service.WaterMarkService;

/**
 * 图片加水印显示控制器
 * @author Milan.Ma
 * @version 1.0
 */
@Controller
@RequestMapping("/watermark")
public class WaterMarkController {
	
	@Autowired
	private WaterMarkService watermarkService;
	
	@RequestMapping(value="/page.do",method=RequestMethod.GET)
	public String page() throws Exception{
		return "watermark";
	}
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
	public ModelAndView uploadFile(@RequestParam("image") CommonsMultipartFile file,HttpServletRequest request) throws Exception{
		String path = "/images";//相对路径
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = file.getOriginalFilename();
		try {
			//图片上传
			file.transferTo(new File(realPath+"/"+fileName));
			
			//图片加水印
			watermarkService.watermarkMore(new File(realPath+"/"+fileName),"watermark_"+fileName, realPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView();
		view.addObject("imageUrl", path+"/"+fileName);
		view.addObject("watermarkImageUrl", path+"/watermark_"+fileName);
		view.setViewName("watermark_success");
		return view;
	}
}
