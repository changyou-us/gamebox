package com.gamebox.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * 验证参数的公共方法类
 * 
 * @author zhangnan_wg
 * @since 2014-07-11
 */
public final class ValidateUtils {

    /**
     * 生成随机数
     */
    private static Random random = new Random();

    /**
     * 随机数的取值
     */
    private static final String randChars = "0123456789abcdefghigklmnopqrstuvtxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";

    /**
     * 算出一定长度的salt 可以使数字和字母
     * 
     * @param length
     * @param isOnlyNum
     * @return
     */
    public static String getRandStr(int length, boolean isOnlyNum) {

        int size = isOnlyNum ? 10 : 62;
        StringBuffer hash = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            hash.append(randChars.charAt(random.nextInt(size)));
        }
        return hash.toString();
    }

    /**
     * 随机获取字符串
     * 
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {

        if (length <= 0) {
            return "";
        }
        char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i',
                'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
        }
        return stringBuffer.toString();
    }

    /**
     * 验证是否为邮箱
     * 
     * @param email
     *            验证字符串
     * @return 是否为邮箱字符
     */
    public static boolean validateEmail(String email) {

//        Pattern p = Pattern.compile("^((([a-z]|\\d|[_-])+(\\.([a-z]|\\d|[_-])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e])|(\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d)|(([a-z]|\\d)([a-z]|\\d|-|\\.|_|~)*([a-z]|\\d)))\\.)+(([a-z])|(([a-z])([a-z]|\\d|-|\\.|_|~)*([a-z])))$/i");
//        Pattern p = Pattern.compile("^(([-._]\\w+)*@\\w+([-._]\\w+)*\\.\\w+([-._]\\w+)*){0,1}$");
        Pattern p = Pattern.compile("^[\\w-._]+@[\\w-._]+(\\.[\\w-._]+)+$");
        Matcher m = p.matcher(email);
        return m.find();
    }

    public static boolean validateName(String name) {

        // Pattern p = Pattern.compile("[0-9a-zA-Z\\s.,#()-_]*");
        Pattern p = Pattern.compile("[0-9a-zA-Z\u4e00-\u9fa5]*");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean validateCNName(String name) {

        Pattern p = Pattern.compile("[0-9a-zA-Z\u4E00-\u9FA5]+");
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 验证密码是否符合标准 gamebox的密码是由数字和字母组成，不区分大小写
     * 
     * @param pwd
     * @return
     */
    public static boolean validatePassword(String pwd) {

        // String regex = "([A-Za-z]+[0-9])";
        String regex = "[0-9a-zA-Z]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    public static boolean validateWebsite(String name) {

        Pattern p = Pattern.compile("([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
        Matcher m = p.matcher(name);
        return m.find();
    }

    /**
     * 将string设置小写
     * 
     * @param strings
     */
    public static void strings2Low(String... strings) {

        for (String s : strings) {
            if (!StringUtils.isEmpty(s)) {
                s = s.toLowerCase();
            }

        }
    }

    public static boolean isPositiveInt(String arg) {

        if (arg == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(arg).matches();
    }
    
    public static void main(String[] args)
    {
//        String[] strs = { "adsf@er","adsf@er.com", "ad!sf@er.com", "ad$sf@er.com", "ad@sf@er.com", "ad_sf@er.com",
//                "_ad-sf@er.com", "adsf@er-cin.com", "adsf@er-cin.com","zhangnan_wg@cyou-inc.com","adsf@er_cin.com", "ad.sf@er_cin.com", "adsf@er_cin.com.com" };
//        // System.out.println(validateName(str));
//        for (String str : strs) {
//            System.out.println("===============");
//            System.out.println(str);
//            System.out.println(validateEmail(str));
//        }
        String email="zhncu111g@163.com";
        System.out.println(Md5Token.getInstance().getShortToken(email));
        System.out.println(WebUtils.authCode(email,null,null,0));
    }

}