package com.milan.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.coobird.thumbnailator.Thumbnails;
/**
 * 图片缩放显示控制器
 * @author Milan.Ma
 * @version 1.0
 */
@Controller
@RequestMapping("/scale")
public class ImageScaleController {
	
	public static final int WIDTH = 100;//缩放图片的宽度
	public static final int HEIGHT = 100;//缩放图片的长度

	@RequestMapping(value="/page.do",method=RequestMethod.GET)
	public String page() throws Exception{
		return "image_scale";
	}
	@RequestMapping(value="/upload.do",method=RequestMethod.POST)
	public ModelAndView uploadFile(@RequestParam("image") CommonsMultipartFile file,HttpServletRequest request) throws Exception{
		String path = "/images";//相对路径
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = file.getOriginalFilename();
		try {
			//图片上传
			file.transferTo(new File(realPath+"/"+fileName));
			
			FileInputStream in = new FileInputStream(realPath+"/"+fileName);
			//图片缩放
			Thumbnails.of(in).size(WIDTH, HEIGHT).toFile(realPath+"/thum_"+fileName);
			//关闭流
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView();
		view.addObject("imageUrl", path+"/"+fileName);
		view.addObject("thumImageUrl", path+"/thum_"+fileName);
		view.setViewName("image_scale_success");
		return view;
	}
}
