package com.milan.service.impl;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.milan.service.WaterMarkService;

@Service("watermarkService")
public class WaterMarkServiceImpl implements WaterMarkService{

	public String wartermark(File imageFile, String fileName, String targetFile) {
		FileOutputStream out = null;
		try {
			//获取原图的高度，宽度
			Image image = ImageIO.read(imageFile);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			
			//1.创建图片缓存对象
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			//2.创建图片绘制对象
			Graphics2D graphics = bufferedImage.createGraphics();
			
			//3.将图片缓存到对应的BufferedImage对象中
			graphics.drawImage(image, 0, 0, width, height, null);
			
			//4.将水印绘制到图片
			graphics.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
			graphics.setColor(FONT_COLOR);
			
			//计算水印的位置
			int width2 = FONT_SIZE * getTextLength(MARK_TEXT);//水印的宽度
			int height2 = FONT_SIZE;//水印的高度
			int widthDiff = width -width2;
			int heigthDiff = height - height2;
			int x = X;
			int y = Y;
			//防止文字溢出
			if(x>widthDiff){
				x = widthDiff;
			}
			if(y>heigthDiff){
				y = heigthDiff;
			}
			//设置水印文字透明度
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			graphics.drawString(MARK_TEXT, x, y+FONT_SIZE);
			graphics.dispose();
			
			//输出图片到对应目录
			out = new FileOutputStream(targetFile+"/"+fileName);
			ImageIO.write(bufferedImage, "jpg", out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{//关闭资源
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return targetFile+"/"+fileName;
	}
	
	public String watermarkMore(File imageFile, String fileName, String targetFile) {
		FileOutputStream out = null;
		try {
			//获取原图的高度，宽度
			Image image = ImageIO.read(imageFile);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			
			//1.创建图片缓存对象
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			//2.创建图片绘制对象
			Graphics2D graphics = bufferedImage.createGraphics();
			
			//3.将图片缓存到对应的BufferedImage对象中
			graphics.drawImage(image, 0, 0, width, height, null);
			
			//4.将水印绘制到图片
			graphics.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
			graphics.setColor(FONT_COLOR);
			
			//计算水印的位置
			int width2 = FONT_SIZE * getTextLength(MARK_TEXT);//水印的宽度
			int height2 = FONT_SIZE;//水印的高度
			
			//设置水印文字透明度
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,ALPHA));
			//设置水印文字弧度(30度),旋转中心(图片中心)
			graphics.rotate(Math.toRadians(30), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
			//从上往下,从左往右地打水印
			//为了保证水印均匀直接看做将高度，长度扩展0.5
			int x = -width/2;
			int y = -height/2;
			while(x <= width*1.5){
				while(y < height*1.5){
					graphics.drawString(MARK_TEXT, x, y);
					y += height2+200;//水印间高度间隔300
				}
				y = -height/2;
				x += width2+200;//水印间宽度间隔300
			}
			
			graphics.dispose();
			
			//输出图片到对应目录
			out = new FileOutputStream(targetFile+"/"+fileName);
			ImageIO.write(bufferedImage, "jpg", out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{//关闭资源
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return targetFile+"/"+fileName;
	}
	
	/**
	 * 获取文本长度
	 * @param text
	 * @return
	 */
	private int getTextLength(String text){
		int length = text.length();
		for(int i=0; i<text.length(); i++){
			String s = String.valueOf(text.charAt(i));
			if(s.getBytes().length>1){//中文
				length++;
			}
		}
		length = length%2==0?length/2:length/2+1;
		return length;
	}

}
