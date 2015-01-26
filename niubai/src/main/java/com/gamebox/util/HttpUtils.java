package com.gamebox.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 从gamebox_partner copy过来的代码，处理http信息类
 * 
 * @author zhangnan_wg
 * 
 */
public class HttpUtils {

    private final static String STATUS_UNKNOWN = "unknown";

    private final static String LOCALHOST = "127.0.0.1";

    private final static String UTF8 = "UTF-8";

    private HttpUtils() {

    }

    /**
     * 获取IP
     * 
     * @param request
     *            这个不用说了撒
     * @return 访问者IP
     */
    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || STATUS_UNKNOWN.equalsIgnoreCase(ip))
            ip = request.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || STATUS_UNKNOWN.equalsIgnoreCase(ip))
            ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || STATUS_UNKNOWN.equalsIgnoreCase(ip))
            ip = request.getRemoteAddr();
        String realIp = "";
        if (ip != null && ip.indexOf(LOCALHOST) >= 0)
            realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !"".equals(realIp)) {
            return realIp.split(",")[0];
        }
        else {
            return ip.split(",")[0];
        }
    }

    /**
     * 获取服务器域名,比如www.gamebox.com
     * 
     * @param request
     * @return 服务器域名 http://hotsname:port/
     */
    public static String getHttpHost(HttpServletRequest request) {

        String port = "";
        if (request.getServerPort() != 80) {
            port = ":" + request.getServerPort();
        }
        StringBuffer sb = new StringBuffer(6);
        sb.append(request.getScheme()).append("://").append(request.getServerName()).append(port)
                .append(request.getContextPath()).append("/");
        return sb.toString();
    }

    /**
     * 获取get请求完整url
     * 
     * @param request
     * @return 完整请求url
     */
    public static String getFullRequestUrl(HttpServletRequest request) {

        return request.getRequestURL() + "?" + request.getQueryString();
    }

    /**
     * 向渠道方以post形式发送字符串
     * 
     * @param url
     *            发送url
     * @param str
     *            要发送的字符串
     * @return 渠道返回值
     */
    public static String postString(String url, String str) {

        String res = "";
        try {
            URL u = new URL(url);
            URLConnection uc = u.openConnection();
            uc.setDoOutput(true);
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter pw = new PrintWriter(uc.getOutputStream());
            pw.println(str);
            pw.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            res = in.readLine();
            in.close();
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 接收第三方以post形式发送过来的字符串参数，重新封装成paramName=paramValue&……的形式
     * 
     * @param request
     *            HttpServletRequest
     * @param str
     *            自定义字符串开头
     * @return 渠道返回值
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("rawtypes")
    public static String getPostString(String str, HttpServletRequest request) throws UnsupportedEncodingException {

        Enumeration en = request.getParameterNames();
        StringBuffer buffer = new StringBuffer();
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            String paramValue = request.getParameter(paramName);
            buffer.append(str).append("&").append(paramName).append("=").append(URLEncoder.encode(paramValue, UTF8));
        }
        return buffer.toString();
    }
}
