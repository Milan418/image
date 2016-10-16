package com.milan.service;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

public interface WaterMarkService {
	public static final String MARK_TEXT = "测试";//水印内容
	public static final String FONT_NAME = "微软雅黑";//字体
	public static final Color FONT_COLOR = Color.RED;//字体颜色
	public static final int FONT_STYLE = Font.BOLD;
	public static final int FONT_SIZE = 80;
	public static final int X = 10;//水印位置横坐标
	public static final int Y = 10;//水印位置纵坐标
	public static final float ALPHA = 0.3f;//水印的透明度
	
	/**
	 * 实现图片水印
	 * @param image 原始图片
	 * @param fileName 加水印后图片名称
	 * @param targetFile 图片存放位置
	 * @return 水印图片位置
	 */
	public String wartermark(File image,String fileName,String targetFile);
	
	/**
	 * 实现图片多水印
	 * @param image
	 * @param fileName
	 * @param targetFile
	 * @return
	 */
	public String watermarkMore(File image,String fileName,String targetFile);

}
