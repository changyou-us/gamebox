/*
 * Copyright 2012-2013 gamebox. All rights reserved.
 * Support: http://www.gamebox.com
 * Team: WG DEV
 */
package com.gamebox;

/**
 * 公共参数
 * 
 * @author Alex Ding
 * @version 1.0
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** gamebox.xml文件路径 */
	public static final String GAMEBOX_XML_PATH = "/gamebox.xml";

	/** gamebox.properties文件路径 */
	public static final String GAMEBOX_PROPERTIES_PATH = "/gamebox.properties";
	
	/** gamebox.json文件路径 */
	public static final String GAMEBOX_JSON_PATH = "/gamebox.json";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}