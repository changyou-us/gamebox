/*
 * Copyright 2012-2013 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV
 */
package com.gamebox.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;


/**
 * Utils - Web
 * 
 * @author Alex Ding
 * @version 1.0
 */
public final class WebUtils {

    /**
     * 不可实例化
     */
    private WebUtils() {

    }

    public final static String COOKIE_PREFIX = "gamebox_";

    public final static String COOKIE_AUTH = "auth";
    
    public final static String COOKIE_DOMAIN = ".gamebox.com";
    
    public final static String COOKIE_PATH = "/";

    /**
     * COOKIE中登陆用户的用户名的key值
     */
    public final static String COOKIE_USER = "loginuser";

    /**
     * COOKIE中登陆用户的key值
     */
    public static final String USER = "user";
    
    public static final String ALLIANCE_USER = "user";

    public static final String COOKIE_UID = "uid";

    public static final String SESSOIN_ID = "sessionid";

    public final static String CHARSET = "UTF-8";

    /**
     * 解密auth的字符串
     */
    public final static String ENCODE_PASSWORD = "ENCODE";

    /**
     * 解密auth的字符串
     */
    public final static String DECODE_PASSWORD = "DECODE";

    /**
     * 连接超时时间
     */
    public static final Integer CONNECT_TIMEOUT = 10000;

    /**
     * 读写超时时间
     */
    public static final Integer READ_TIMEOUT = 10000;

    /**
     * 添加cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie名称
     * @param value
     *            cookie值
     * @param maxAge
     *            有效期(单位: 秒)
     * @param path
     *            路径
     * @param domain
     *            域
     * @param secure
     *            是否启用加密
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            Integer maxAge, String path, String domain, Boolean secure) {

        Assert.notNull(request);
        Assert.notNull(response);
        Assert.hasText(name);
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge);
            }
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            if (secure != null) {
                cookie.setSecure(secure);
            }
            response.addCookie(cookie);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie名称
     * @param value
     *            cookie值
     * @param maxAge
     *            有效期(单位: 秒)
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
            Integer maxAge) {

        addCookie(request, response, name, value, maxAge, COOKIE_PATH, COOKIE_DOMAIN, null);
    }

    /**
     * 添加cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie名称
     * @param value
     *            cookie值
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {

        addCookie(request, response, name, value, null, COOKIE_PATH, COOKIE_DOMAIN, null);
    }

    /**
     * 获取cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param name
     *            cookie名称
     * @return 若不存在则返回null
     */
    public static String getCookie(HttpServletRequest request, String name) {

        Assert.notNull(request);
        Assert.hasText(name);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 移除cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie名称
     * @param path
     *            路径
     * @param domain
     *            域
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path,
            String domain) {

        Assert.notNull(request);
        Assert.notNull(response);
        Assert.hasText(name);
        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除cookie
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            cookie名称
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {

        removeCookie(request, response, name, COOKIE_PATH, COOKIE_DOMAIN);
    }

    /**
     * 获取参数
     * 
     * @param queryString
     *            查询字符串,格式为url中请求的参数,如name=jason&age=23
     * @param encoding
     *            编码格式
     * @param name
     *            参数名称
     * @return 参数
     */
    public static String getParameter(String queryString, String encoding, String name) {

        String[] parameterValues = getParameterMap(queryString, encoding).get(name);
        return parameterValues != null && parameterValues.length > 0 ? parameterValues[0] : null;
    }

    /**
     * 获取参数
     * 
     * @param queryString
     *            查询字符串
     * @param encoding
     *            编码格式
     * @param name
     *            参数名称
     * @return 参数
     */
    public static String[] getParameterValues(String queryString, String encoding, String name) {

        return getParameterMap(queryString, encoding).get(name);
    }

    /**
     * 获取参数
     * 
     * @param queryString
     *            查询字符串
     * @param encoding
     *            编码格式
     * @return 参数
     */
    public static Map<String, String[]> getParameterMap(String queryString, String encoding) {

        Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        Charset charset = Charset.forName(encoding);
        if (StringUtils.isNotEmpty(queryString)) {
            byte[] bytes = queryString.getBytes(charset);
            if (bytes != null && bytes.length > 0) {
                int ix = 0;
                int ox = 0;
                String key = null;
                String value = null;
                while (ix < bytes.length) {
                    byte c = bytes[ix++];
                    switch ((char) c) {
                    case '&':
                        value = new String(bytes, 0, ox, charset);
                        if (key != null) {
                            putMapEntry(parameterMap, key, value);
                            key = null;
                        }
                        ox = 0;
                        break;
                    case '=':
                        if (key == null) {
                            key = new String(bytes, 0, ox, charset);
                            ox = 0;
                        }
                        else {
                            bytes[ox++] = c;
                        }
                        break;
                    case '+':
                        bytes[ox++] = (byte) ' ';
                        break;
                    case '%':
                        bytes[ox++] = (byte) ((convertHexDigit(bytes[ix++]) << 4) + convertHexDigit(bytes[ix++]));
                        break;
                    default:
                        bytes[ox++] = c;
                    }
                }
                if (key != null) {
                    value = new String(bytes, 0, ox, charset);
                    putMapEntry(parameterMap, key, value);
                }
            }
        }
        return parameterMap;
    }

    private static void putMapEntry(Map<String, String[]> map, String name, String value) {

        String[] newValues = null;
        String[] oldValues = map.get(name);
        if (oldValues == null) {
            newValues = new String[] { value };
        }
        else {
            newValues = new String[oldValues.length + 1];
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = value;
        }
        map.put(name, newValues);
    }

    private static byte convertHexDigit(byte b) {

        if ((b >= '0') && (b <= '9')) {
            return (byte) (b - '0');
        }
        if ((b >= 'a') && (b <= 'f')) {
            return (byte) (b - 'a' + 10);
        }
        if ((b >= 'A') && (b <= 'F')) {
            return (byte) (b - 'A' + 10);
        }
        throw new IllegalArgumentException();
    }

    /**
     * 发送POST请求
     * 
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, Map<String, String> parameters) {

        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            if (parameters != null && parameters.size() != 0) {
                // 编码请求参数
                if (parameters.size() == 1) {
                    for (String name : parameters.keySet()) {
                        sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                    }
                    params = sb.toString();
                }
                else {
                    for (String name : parameters.keySet()) {
                        sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
                                .append("&");
                    }
                    String temp_params = sb.toString();
                    params = temp_params.substring(0, temp_params.length() - 1);
                }
            }
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();

            // 设置超时时间
            httpConn.setConnectTimeout(CONNECT_TIMEOUT);
            httpConn.setReadTimeout(READ_TIMEOUT);

            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送GET请求
     * 
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) {

        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            String full_url = "";
            // 编码请求参数
            if (parameters != null && parameters.size() != 0) {
                if (parameters.size() == 1) {
                    for (String name : parameters.keySet()) {
                        sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                    }
                    params = sb.toString();
                }
                else {
                    for (String name : parameters.keySet()) {
                        sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
                                .append("&");
                    }
                    String temp_params = sb.toString();
                    params = temp_params.substring(0, temp_params.length() - 1);
                }
                full_url = url + "?" + params;
            }
            else {
                full_url = url;
            }

            System.out.println(full_url);
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(full_url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            /*
            for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }*/
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        catch (ConnectException e) {
            System.out.println("ConnectException");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 加密鉴权信息
     * 
     * @param string
     *            鉴权加密的字符串
     * @param operation
     *            操作：ENCODE-加密，DECODE-解密
     * @param key
     *            密匙
     * @param expiry
     *            时间
     * @return
     *            解密结果
     */
    public static String authCode(String string, String operation, String key, int expiry) {

        try {
            Md5Token md5 = Md5Token.getInstance();
            // 动态密匙长度，相同的明文会生成不同密文就是依靠动态密匙，密文变化 = 16 的 $ckey_length 次方
            int ckey_length = 4; 
            long millisecond = System.currentTimeMillis();
            //取系统时间
            int time = (int) (millisecond / 1000); 
            key = md5.getLongToken(key);
            // 密匙a参与加解密
            String keya = md5.getLongToken(key.substring(0, 16)); 
            // 密匙b做数据完整性验证
            String keyb = md5.getLongToken(key.substring(16, 32));  
            // 参与运算的密匙c
            String keyc = ckey_length != 0 ? (DECODE_PASSWORD.equals(operation) ? string.substring(0, ckey_length)
                    : md5.getLongToken(String.valueOf(millisecond)).substring(32 - ckey_length)) : "";
            String cryptkey = keya + md5.getLongToken(keya + keyc);
            int key_length = cryptkey.length();
            // 明文，前10位用来保存时间戳，解密时验证数据有效性，10到26位用来保存$keyb(密匙b)，解密时会通过这个密匙验证数据完整性
            // 如果是解码的话，会从第$ckey_length位开始，因为密文前$ckey_length位保存 动态密匙，以保证解密正确
            string = DECODE_PASSWORD.equals(operation) ? Base64Util.decode(string.substring(ckey_length), CHARSET)
                    : (expiry > 0 ? expiry + time : "0000000000") + md5.getLongToken(string + keyb).substring(0, 16)
                            + string;
            int string_length = string.length();
            int range = 256;
            int[] rndkey = new int[range];
            // 产生密匙簿
            for (int i = 0; i < range; i++) {
                rndkey[i] = cryptkey.charAt(i % key_length);
            }
            int tmp;
            int[] box = new int[range];
            for (int i = 0, j = 0; i < range; i++) {
                j = (j + box[i] + rndkey[i]) % range;
                tmp = box[i];
                box[i] = box[j];
                box[j] = tmp;
            }
            StringBuffer result = new StringBuffer(string_length);
            // 核心加解密部分
            for (int a = 0, i = 0, j = 0; i < string_length; i++) {
                a = (a + 1) % range;
                j = (j + box[a]) % range;
                tmp = box[a];
                box[a] = box[j];
                box[j] = tmp;
                result.append((char) ((int) string.charAt(i) ^ (box[(box[a] + box[j]) % range])));
            }
            if (DECODE_PASSWORD.equals(operation)) {
                int resulttime = intval(result.substring(0, 10));
                if ((resulttime == 0 || resulttime - time > 0)
                        && result.substring(10, 26).equals(
                                md5.getLongToken(result.substring(26) + keyb).substring(0, 16))) {
                    return result.substring(26);
                }
                else {
                    return "";
                }
            }
            else {
                return keyc + (Base64Util.encode(result.toString(), CHARSET)).replaceAll("=", "");
            }
        }
        catch (Exception e) {
            return "";
        }
    }

    /**
     * 
     * @param s
     * @return
     */
    public static int intval(String s) {

        return intval(s, 10);
    }

    /**
     * 
     * @param s
     * @param radix
     * @return
     */
    public static int intval(String s, int radix) {

        if (s == null || s.length() == 0) {
            return 0;
        }
        if (radix == 0) {
            radix = 10;
        }
        else if (radix < Character.MIN_RADIX) {
            return 0;
        }
        else if (radix > Character.MAX_RADIX) {
            return 0;
        }
        int result = 0;
        int i = 0, max = s.length();
        int limit;
        int multmin;
        int digit;
        boolean negative = false;
        if (s.charAt(0) == '-') {
            negative = true;
            limit = Integer.MIN_VALUE;
            i++;
        }
        else {
            limit = -Integer.MAX_VALUE;
        }
        if (i < max) {
            digit = Character.digit(s.charAt(i++), radix);
            if (digit < 0) {
                return 0;
            }
            else {
                result = -digit;
            }
        }
        multmin = limit / radix;
        while (i < max) {
            digit = Character.digit(s.charAt(i++), radix);
            if (digit < 0) {
                break;
            }
            if (result < multmin) {
                result = limit;
                break;
            }
            result *= radix;
            if (result < limit + digit) {
                result = limit;
                break;
            }
            result -= digit;
        }
        if (negative) {
            if (i > 1) {
                return result;
            }
            else {
                return 0;
            }
        }
        else {
            return -result;
        }
    }
    
}