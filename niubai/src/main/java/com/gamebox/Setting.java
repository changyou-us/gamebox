/*
 * Copyright 2012-2013 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV
 */
package com.gamebox;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 系统设置
 * 
 * @author Alex Ding
 * @version 1.0
 */
public class Setting implements Serializable {

    private static final long serialVersionUID = -1478999889661796840L;

    /**
     * 水印位置
     */
    public enum WatermarkPosition {

        /** 无 */
        no,

        /** 左上 */
        topLeft,

        /** 右上 */
        topRight,

        /** 居中 */
        center,

        /** 左下 */
        bottomLeft,

        /** 右下 */
        bottomRight
    }

    /**
     * 小数位精确方式
     */
    public enum RoundType {

        /** 四舍五入 */
        roundHalfUp,

        /** 向上取整 */
        roundUp,

        /** 向下取整 */
        roundDown
    }

    /**
     * 验证码类型
     */
    public enum CaptchaType {

        /** 会员登录 */
        memberLogin,

        /** 会员注册 */
        memberRegister,

        /** 后台登录 */
        adminLogin,

        /** 商品评论 */
        review,

        /** 商品咨询 */
        consultation,

        /** 找回密码 */
        findPassword,

        /** 重置密码 */
        resetPassword,

        /** 其它 */
        other
    }

    /**
     * 账号锁定类型
     */
    public enum AccountLockType {

        /** 会员 */
        member,

        /** 管理员 */
        admin
    }

    /** 缓存名称 */
    public static final String CACHE_NAME = "setting";

    /** 缓存Key */
    public static final Integer CACHE_KEY = 0;

    /** 分隔符 */
    private static final String SEPARATOR = ",";

    /** 网站名称 */
    private String siteName;

    /** 网站网址 */
    private String siteUrl;

    /** logo */
    private String logo;

    /** 热门搜索 */
    private String hotSearch;

    /** 是否网站开启 */
    private Boolean isSiteEnabled;

    /** 网站关闭消息 */
    private String siteCloseMessage;

    /** 水印透明度 */
    private Integer watermarkAlpha;

    /** 水印图片 */
    private String watermarkImage;

    /** 水印位置 */
    private WatermarkPosition watermarkPosition;

    /** 价格精确位数 */
    private Integer priceScale;

    /** 价格精确方式 */
    private RoundType priceRoundType;

    /** 是否开放注册 */
    private Boolean isRegisterEnabled;

    /** 是否允许E-mail重复注册 */
    private Boolean isDuplicateEmail;

    /** 禁用用户名 */
    private String disabledUsername;

    /** email最小长度 */
    private Integer emailMinLength;

    /** email最大长度 */
    private Integer emailMaxLength;
    
    /** 用户名最小长度 */
    private Integer usernameMinLength;

    /** 用户名最大长度 */
    private Integer usernameMaxLength;

    /** 密码最小长度 */
    private Integer passwordMinLength;

    /** 密码最大长度 */
    private Integer passwordMaxLength;

    /** 是否允许E-mail登录 */
    private Boolean isEmailLogin;

    /** 验证码类型 */
    private CaptchaType[] captchaTypes;

    /** 账号锁定类型 */
    private AccountLockType[] accountLockTypes;

    /** 连续登录失败最大次数 */
    private Integer accountLockCount;

    /** 自动解锁时间 */
    private Integer accountLockTime;

    /** 安全密匙有效时间 */
    private Integer safeKeyExpiryTime;

    /** 上传文件最大限制 */
    private Integer uploadMaxSize;

    /** 允许上传图片扩展名 */
    private String uploadImageExtension;

    /** 允许上传Flash扩展名 */
    private String uploadFlashExtension;

    /** 允许上传媒体扩展名 */
    private String uploadMediaExtension;

    /** 允许上传文件扩展名 */
    private String uploadFileExtension;

    /** 图片上传路径 */
    private String imageUploadPath;

    /** Flash上传路径 */
    private String flashUploadPath;

    /** 媒体上传路径 */
    private String mediaUploadPath;

    /** 文件上传路径 */
    private String fileUploadPath;

    /** 发件人邮箱 */
    private String smtpFromMail;

    /** SMTP服务器地址 */
    private String smtpHost;

    /** SMTP服务器端口 */
    private Integer smtpPort;

    /** SMTP用户名 */
    private String smtpUsername;

    /** SMTP密码 */
    private String smtpPassword;

    /** 货币符号 */
    private String currencySign;

    /** 货币单位 */
    private String currencyUnit;

    /** 是否开启开发模式 */
    private Boolean isDevelopmentEnabled;

    /** Cookie路径 */
    private String cookiePath;

    /** Cookie作用域 */
    private String cookieDomain;

    /**
     * cookie的生命周期
     */
    private Integer cookieAge;

    /**
     * 是否发送信息通知其他系统
     */
    private Boolean isNotify;

    /**
     * 连接超时时间
     */
    private Integer connectTimeout;

    /**
     * 读写超时时间
     */
    private Integer readTimeout;

    /**
     * 头像保存路径
     */
    private String avatarPath;

    /**
     * 默认的头像路径
     */
    private String defaultAvatar;

    /**
     * 上传文件的最大限制
     */
    private Integer avatarMaxSize;

    /**
     * @author zhaichuancheng_cp
     * 
     *         工单附件路径
     */
    private String ticketAttachmentPath;
    
    /**
     * @author wuji
     * 
     *         上传临时文件目录
     */
    private String tempFilePath;
    
    /**
     * partner的servlet通知路径
     */
    private String partnerServlet;
    
    /**
     * 获取网站名称
     * 
     * @return 网站名称
     */
    @NotEmpty
    @Length(max = 200)
    public String getSiteName() {

        return siteName;
    }

    /**
     * 设置网站名称
     * 
     * @param siteName
     *            网站名称
     */
    public void setSiteName(String siteName) {

        this.siteName = siteName;
    }

    /**
     * 获取网站网址
     * 
     * @return 网站网址
     */
    @NotEmpty
    @Length(max = 200)
    public String getSiteUrl() {

        return siteUrl;
    }

    /**
     * 设置网站网址
     * 
     * @param siteUrl
     *            网站网址
     */
    public void setSiteUrl(String siteUrl) {

        this.siteUrl = StringUtils.removeEnd(siteUrl, "/");
    }

    /**
     * 获取logo
     * 
     * @return logo
     */
    @NotEmpty
    @Length(max = 200)
    public String getLogo() {

        return logo;
    }

    /**
     * 设置logo
     * 
     * @param logo
     *            logo
     */
    public void setLogo(String logo) {

        this.logo = logo;
    }

    /**
     * 获取热门搜索
     * 
     * @return 热门搜索
     */
    @Length(max = 200)
    public String getHotSearch() {

        return hotSearch;
    }

    /**
     * 设置热门搜索
     * 
     * @param hotSearch
     *            热门搜索
     */
    public void setHotSearch(String hotSearch) {

        if (hotSearch != null) {
            hotSearch = hotSearch.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
        }
        this.hotSearch = hotSearch;
    }

    /**
     * 获取是否网站开启
     * 
     * @return 是否网站开启
     */
    @NotNull
    public Boolean getIsSiteEnabled() {

        return isSiteEnabled;
    }

    /**
     * 设置是否网站开启
     * 
     * @param isSiteEnabled
     *            是否网站开启
     */
    public void setIsSiteEnabled(Boolean isSiteEnabled) {

        this.isSiteEnabled = isSiteEnabled;
    }

    /**
     * 获取网站关闭消息
     * 
     * @return 网站关闭消息
     */
    @NotEmpty
    public String getSiteCloseMessage() {

        return siteCloseMessage;
    }

    /**
     * 设置网站关闭消息
     * 
     * @param siteCloseMessage
     *            网站关闭消息
     */
    public void setSiteCloseMessage(String siteCloseMessage) {

        this.siteCloseMessage = siteCloseMessage;
    }

    /**
     * 获取水印透明度
     * 
     * @return 水印透明度
     */
    @NotNull
    @Min(0)
    @Max(100)
    public Integer getWatermarkAlpha() {

        return watermarkAlpha;
    }

    /**
     * 设置水印透明度
     * 
     * @param watermarkAlpha
     *            水印透明度
     */
    public void setWatermarkAlpha(Integer watermarkAlpha) {

        this.watermarkAlpha = watermarkAlpha;
    }

    /**
     * 获取水印图片
     * 
     * @return 水印图片
     */
    public String getWatermarkImage() {

        return watermarkImage;
    }

    /**
     * 设置水印图片
     * 
     * @param watermarkImage
     *            水印图片
     */
    public void setWatermarkImage(String watermarkImage) {

        this.watermarkImage = watermarkImage;
    }

    /**
     * 获取水印位置
     * 
     * @return 水印位置
     */
    @NotNull
    public WatermarkPosition getWatermarkPosition() {

        return watermarkPosition;
    }

    /**
     * 设置水印位置
     * 
     * @param watermarkPosition
     *            水印位置
     */
    public void setWatermarkPosition(WatermarkPosition watermarkPosition) {

        this.watermarkPosition = watermarkPosition;
    }

    /**
     * 获取价格精确位数
     * 
     * @return 价格精确位数
     */
    @NotNull
    @Min(0)
    @Max(3)
    public Integer getPriceScale() {

        return priceScale;
    }

    /**
     * 设置价格精确位数
     * 
     * @param priceScale
     *            价格精确位数
     */
    public void setPriceScale(Integer priceScale) {

        this.priceScale = priceScale;
    }

    /**
     * 获取价格精确方式
     * 
     * @return 价格精确方式
     */
    @NotNull
    public RoundType getPriceRoundType() {

        return priceRoundType;
    }

    /**
     * 设置价格精确方式
     * 
     * @param priceRoundType
     *            价格精确方式
     */
    public void setPriceRoundType(RoundType priceRoundType) {

        this.priceRoundType = priceRoundType;
    }

    /**
     * 获取是否开放注册
     * 
     * @return 是否开放注册
     */
    @NotNull
    public Boolean getIsRegisterEnabled() {

        return isRegisterEnabled;
    }

    /**
     * 设置是否开放注册
     * 
     * @param isRegisterEnabled
     *            是否开放注册
     */
    public void setIsRegisterEnabled(Boolean isRegisterEnabled) {

        this.isRegisterEnabled = isRegisterEnabled;
    }

    /**
     * 获取是否允许E-mail重复注册
     * 
     * @return 是否允许E-mail重复注册
     */
    @NotNull
    public Boolean getIsDuplicateEmail() {

        return isDuplicateEmail;
    }

    /**
     * 设置是否允许E-mail重复注册
     * 
     * @param isDuplicateEmail
     *            是否允许E-mail重复注册
     */
    public void setIsDuplicateEmail(Boolean isDuplicateEmail) {

        this.isDuplicateEmail = isDuplicateEmail;
    }

    /**
     * 获取禁用用户名
     * 
     * @return 禁用用户名
     */
    @Length(max = 200)
    public String getDisabledUsername() {

        return disabledUsername;
    }

    /**
     * 设置禁用用户名
     * 
     * @param disabledUsername
     *            禁用用户名
     */
    public void setDisabledUsername(String disabledUsername) {

        if (disabledUsername != null) {
            disabledUsername = disabledUsername.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
        }
        this.disabledUsername = disabledUsername;
    }

    
    
    public Integer getEmailMinLength() {
    
        return emailMinLength;
    }

    
    public void setEmailMinLength(Integer emailMinLength) {
    
        this.emailMinLength = emailMinLength;
    }

    
    public Integer getEmailMaxLength() {
    
        return emailMaxLength;
    }

    
    public void setEmailMaxLength(Integer emailMaxLength) {
    
        this.emailMaxLength = emailMaxLength;
    }

    /**
     * 获取用户名最小长度
     * 
     * @return 用户名最小长度
     */
    @NotNull
    @Min(1)
    @Max(117)
    public Integer getUsernameMinLength() {

        return usernameMinLength;
    }

    /**
     * 设置用户名最小长度
     * 
     * @param usernameMinLength
     *            用户名最小长度
     */
    public void setUsernameMinLength(Integer usernameMinLength) {

        this.usernameMinLength = usernameMinLength;
    }

    /**
     * 获取用户名最大长度
     * 
     * @return 用户名最大长度
     */
    @NotNull
    @Min(1)
    @Max(117)
    public Integer getUsernameMaxLength() {

        return usernameMaxLength;
    }

    /**
     * 设置用户名最大长度
     * 
     * @param usernameMaxLength
     *            用户名最大长度
     */
    public void setUsernameMaxLength(Integer usernameMaxLength) {

        this.usernameMaxLength = usernameMaxLength;
    }

    /**
     * 获取密码最小长度
     * 
     * @return 密码最小长度
     */
    @NotNull
    @Min(1)
    @Max(117)
    public Integer getPasswordMinLength() {

        return passwordMinLength;
    }

    /**
     * 设置密码最小长度
     * 
     * @param passwordMinLength
     *            密码最小长度
     */
    public void setPasswordMinLength(Integer passwordMinLength) {

        this.passwordMinLength = passwordMinLength;
    }

    /**
     * 获取密码最大长度
     * 
     * @return 密码最大长度
     */
    @NotNull
    @Min(1)
    @Max(117)
    public Integer getPasswordMaxLength() {

        return passwordMaxLength;
    }

    /**
     * 设置密码最大长度
     * 
     * @param passwordMaxLength
     *            密码最大长度
     */
    public void setPasswordMaxLength(Integer passwordMaxLength) {

        this.passwordMaxLength = passwordMaxLength;
    }

    /**
     * 获取是否允许E-mail登录
     * 
     * @return 是否允许E-mail登录
     */
    @NotNull
    public Boolean getIsEmailLogin() {

        return isEmailLogin;
    }

    /**
     * 设置是否允许E-mail登录
     * 
     * @param isEmailLogin
     *            是否允许E-mail登录
     */
    public void setIsEmailLogin(Boolean isEmailLogin) {

        this.isEmailLogin = isEmailLogin;
    }

    /**
     * 获取验证码类型
     * 
     * @return 验证码类型
     */
    public CaptchaType[] getCaptchaTypes() {

        return captchaTypes;
    }

    /**
     * 设置验证码类型
     * 
     * @param captchaTypes
     *            验证码类型
     */
    public void setCaptchaTypes(CaptchaType[] captchaTypes) {

        this.captchaTypes = captchaTypes;
    }

    /**
     * 获取账号锁定类型
     * 
     * @return 账号锁定类型
     */
    public AccountLockType[] getAccountLockTypes() {

        return accountLockTypes;
    }

    /**
     * 设置账号锁定类型
     * 
     * @param accountLockTypes
     *            账号锁定类型
     */
    public void setAccountLockTypes(AccountLockType[] accountLockTypes) {

        this.accountLockTypes = accountLockTypes;
    }

    /**
     * 获取连续登录失败最大次数
     * 
     * @return 连续登录失败最大次数
     */
    @NotNull
    @Min(1)
    public Integer getAccountLockCount() {

        return accountLockCount;
    }

    /**
     * 设置连续登录失败最大次数
     * 
     * @param accountLockCount
     *            连续登录失败最大次数
     */
    public void setAccountLockCount(Integer accountLockCount) {

        this.accountLockCount = accountLockCount;
    }

    /**
     * 获取自动解锁时间
     * 
     * @return 自动解锁时间
     */
    @NotNull
    @Min(0)
    public Integer getAccountLockTime() {

        return accountLockTime;
    }

    /**
     * 设置自动解锁时间
     * 
     * @param accountLockTime
     *            自动解锁时间
     */
    public void setAccountLockTime(Integer accountLockTime) {

        this.accountLockTime = accountLockTime;
    }

    /**
     * 获取安全密匙有效时间
     * 
     * @return 安全密匙有效时间
     */
    @NotNull
    @Min(0)
    public Integer getSafeKeyExpiryTime() {

        return safeKeyExpiryTime;
    }

    /**
     * 设置安全密匙有效时间
     * 
     * @param safeKeyExpiryTime
     *            安全密匙有效时间
     */
    public void setSafeKeyExpiryTime(Integer safeKeyExpiryTime) {

        this.safeKeyExpiryTime = safeKeyExpiryTime;
    }

    /**
     * 获取上传文件最大限制
     * 
     * @return 上传文件最大限制
     */
    @NotNull
    @Min(0)
    public Integer getUploadMaxSize() {

        return uploadMaxSize;
    }

    /**
     * 设置上传文件最大限制
     * 
     * @param uploadMaxSize
     *            上传文件最大限制
     */
    public void setUploadMaxSize(Integer uploadMaxSize) {

        this.uploadMaxSize = uploadMaxSize;
    }

    /**
     * 获取允许上传图片扩展名
     * 
     * @return 允许上传图片扩展名
     */
    @Length(max = 200)
    public String getUploadImageExtension() {

        return uploadImageExtension;
    }

    /**
     * 设置允许上传图片扩展名
     * 
     * @param uploadImageExtension
     *            允许上传图片扩展名
     */
    public void setUploadImageExtension(String uploadImageExtension) {

        if (uploadImageExtension != null) {
            uploadImageExtension = uploadImageExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "")
                    .toLowerCase();
        }
        this.uploadImageExtension = uploadImageExtension;
    }

    /**
     * 获取允许上传Flash扩展名
     * 
     * @return 允许上传Flash扩展名
     */
    @Length(max = 200)
    public String getUploadFlashExtension() {

        return uploadFlashExtension;
    }

    /**
     * 设置允许上传Flash扩展名
     * 
     * @param uploadFlashExtension
     *            允许上传Flash扩展名
     */
    public void setUploadFlashExtension(String uploadFlashExtension) {

        if (uploadFlashExtension != null) {
            uploadFlashExtension = uploadFlashExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "")
                    .toLowerCase();
        }
        this.uploadFlashExtension = uploadFlashExtension;
    }

    /**
     * 获取允许上传媒体扩展名
     * 
     * @return 允许上传媒体扩展名
     */
    @Length(max = 200)
    public String getUploadMediaExtension() {

        return uploadMediaExtension;
    }

    /**
     * 设置允许上传媒体扩展名
     * 
     * @param uploadMediaExtension
     *            允许上传媒体扩展名
     */
    public void setUploadMediaExtension(String uploadMediaExtension) {

        if (uploadMediaExtension != null) {
            uploadMediaExtension = uploadMediaExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "")
                    .toLowerCase();
        }
        this.uploadMediaExtension = uploadMediaExtension;
    }

    /**
     * 获取允许上传文件扩展名
     * 
     * @return 允许上传文件扩展名
     */
    @Length(max = 200)
    public String getUploadFileExtension() {

        return uploadFileExtension;
    }

    /**
     * 设置允许上传文件扩展名
     * 
     * @param uploadFileExtension
     *            允许上传文件扩展名
     */
    public void setUploadFileExtension(String uploadFileExtension) {

        if (uploadFileExtension != null) {
            uploadFileExtension = uploadFileExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "")
                    .toLowerCase();
        }
        this.uploadFileExtension = uploadFileExtension;
    }

    /**
     * 获取图片上传路径
     * 
     * @return 图片上传路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getImageUploadPath() {

        return imageUploadPath;
    }

    /**
     * 设置图片上传路径
     * 
     * @param imageUploadPath
     *            图片上传路径
     */
    public void setImageUploadPath(String imageUploadPath) {

        if (imageUploadPath != null) {
            if (!imageUploadPath.startsWith("/")) {
                imageUploadPath = "/" + imageUploadPath;
            }
            if (!imageUploadPath.endsWith("/")) {
                imageUploadPath += "/";
            }
        }
        this.imageUploadPath = imageUploadPath;
    }

    /**
     * 获取Flash上传路径
     * 
     * @return Flash上传路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getFlashUploadPath() {

        return flashUploadPath;
    }

    /**
     * 设置Flash上传路径
     * 
     * @param flashUploadPath
     *            Flash上传路径
     */
    public void setFlashUploadPath(String flashUploadPath) {

        if (flashUploadPath != null) {
            if (!flashUploadPath.startsWith("/")) {
                flashUploadPath = "/" + flashUploadPath;
            }
            if (!flashUploadPath.endsWith("/")) {
                flashUploadPath += "/";
            }
        }
        this.flashUploadPath = flashUploadPath;
    }

    /**
     * 获取媒体上传路径
     * 
     * @return 媒体上传路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getMediaUploadPath() {

        return mediaUploadPath;
    }

    /**
     * 设置媒体上传路径
     * 
     * @param mediaUploadPath
     *            媒体上传路径
     */
    public void setMediaUploadPath(String mediaUploadPath) {

        if (mediaUploadPath != null) {
            if (!mediaUploadPath.startsWith("/")) {
                mediaUploadPath = "/" + mediaUploadPath;
            }
            if (!mediaUploadPath.endsWith("/")) {
                mediaUploadPath += "/";
            }
        }
        this.mediaUploadPath = mediaUploadPath;
    }

    /**
     * 获取文件上传路径
     * 
     * @return 文件上传路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getFileUploadPath() {

        return fileUploadPath;
    }

    /**
     * 设置文件上传路径
     * 
     * @param fileUploadPath
     *            文件上传路径
     */
    public void setFileUploadPath(String fileUploadPath) {

        if (fileUploadPath != null) {
            if (!fileUploadPath.startsWith("/")) {
                fileUploadPath = "/" + fileUploadPath;
            }
            if (!fileUploadPath.endsWith("/")) {
                fileUploadPath += "/";
            }
        }
        this.fileUploadPath = fileUploadPath;
    }

    /**
     * 获取发件人邮箱
     * 
     * @return 发件人邮箱
     */
    @NotEmpty
    @Email
    @Length(max = 200)
    public String getSmtpFromMail() {

        return smtpFromMail;
    }

    /**
     * 设置发件人邮箱
     * 
     * @param smtpFromMail
     *            发件人邮箱
     */
    public void setSmtpFromMail(String smtpFromMail) {

        this.smtpFromMail = smtpFromMail;
    }

    /**
     * 获取SMTP服务器地址
     * 
     * @return SMTP服务器地址
     */
    @NotEmpty
    @Length(max = 200)
    public String getSmtpHost() {

        return smtpHost;
    }

    /**
     * 设置SMTP服务器地址
     * 
     * @param smtpHost
     *            SMTP服务器地址
     */
    public void setSmtpHost(String smtpHost) {

        this.smtpHost = smtpHost;
    }

    /**
     * 获取SMTP服务器端口
     * 
     * @return SMTP服务器端口
     */
    @NotNull
    @Min(0)
    public Integer getSmtpPort() {

        return smtpPort;
    }

    /**
     * 设置SMTP服务器端口
     * 
     * @param smtpPort
     *            SMTP服务器端口
     */
    public void setSmtpPort(Integer smtpPort) {

        this.smtpPort = smtpPort;
    }

    /**
     * 获取SMTP用户名
     * 
     * @return SMTP用户名
     */
    @NotEmpty
    @Length(max = 200)
    public String getSmtpUsername() {

        return smtpUsername;
    }

    /**
     * 设置SMTP用户名
     * 
     * @param smtpUsername
     *            SMTP用户名
     */
    public void setSmtpUsername(String smtpUsername) {

        this.smtpUsername = smtpUsername;
    }

    /**
     * 获取SMTP密码
     * 
     * @return SMTP密码
     */
    @Length(max = 200)
    public String getSmtpPassword() {

        return smtpPassword;
    }

    /**
     * 设置SMTP密码
     * 
     * @param smtpPassword
     *            SMTP密码
     */
    public void setSmtpPassword(String smtpPassword) {

        this.smtpPassword = smtpPassword;
    }

    /**
     * 获取货币符号
     * 
     * @return 货币符号
     */
    @NotEmpty
    @Length(max = 200)
    public String getCurrencySign() {

        return currencySign;
    }

    /**
     * 设置货币符号
     * 
     * @param currencySign
     *            货币符号
     */
    public void setCurrencySign(String currencySign) {

        this.currencySign = currencySign;
    }

    /**
     * 获取货币单位
     * 
     * @return 货币单位
     */
    @NotEmpty
    @Length(max = 200)
    public String getCurrencyUnit() {

        return currencyUnit;
    }

    /**
     * 设置货币单位
     * 
     * @param currencyUnit
     *            货币单位
     */
    public void setCurrencyUnit(String currencyUnit) {

        this.currencyUnit = currencyUnit;
    }

    /**
     * 获取是否开启开发模式
     * 
     * @return 是否开启开发模式
     */
    @NotNull
    public Boolean getIsDevelopmentEnabled() {

        return isDevelopmentEnabled;
    }

    /**
     * 设置是否开启开发模式
     * 
     * @param isDevelopmentEnabled
     *            是否开启开发模式
     */
    public void setIsDevelopmentEnabled(Boolean isDevelopmentEnabled) {

        this.isDevelopmentEnabled = isDevelopmentEnabled;
    }

    /**
     * 获取Cookie路径
     * 
     * @return Cookie路径
     */
    @NotEmpty
    @Length(max = 200)
    public String getCookiePath() {

        return cookiePath;
    }

    /**
     * 设置Cookie路径
     * 
     * @param cookiePath
     *            Cookie路径
     */
    public void setCookiePath(String cookiePath) {

        if (cookiePath != null && !cookiePath.endsWith("/")) {
            cookiePath += "/";
        }
        this.cookiePath = cookiePath;
    }

    /**
     * 获取Cookie作用域
     * 
     * @return Cookie作用域
     */
    @Length(max = 200)
    public String getCookieDomain() {

        return cookieDomain;
    }

    /**
     * 设置Cookie作用域
     * 
     * @param cookieDomain
     *            Cookie作用域
     */
    public void setCookieDomain(String cookieDomain) {

        this.cookieDomain = cookieDomain;
    }

    @NotNull
    public Integer getCookieAge() {

        return cookieAge;
    }

    public void setCookieAge(Integer cookieAge) {

        this.cookieAge = cookieAge;
    }

    /**
     * 获取热门搜索关键词
     * 
     * @return 热门搜索关键词
     */
    public String[] getHotSearches() {

        return StringUtils.split(hotSearch, SEPARATOR);
    }

    /**
     * 获取禁用用户名
     * 
     * @return 禁用用户名
     */
    public String[] getDisabledUsernames() {

        return StringUtils.split(disabledUsername, SEPARATOR);
    }

    /**
     * 获取允许上传图片扩展名
     * 
     * @return 允许上传图片扩展名
     */
    public String[] getUploadImageExtensions() {

        return StringUtils.split(uploadImageExtension, SEPARATOR);
    }

    /**
     * 获取允许上传Flash扩展名
     * 
     * @return 允许上传Flash扩展名
     */
    public String[] getUploadFlashExtensions() {

        return StringUtils.split(uploadFlashExtension, SEPARATOR);
    }

    /**
     * 获取允许上传媒体扩展名
     * 
     * @return 允许上传媒体扩展名
     */
    public String[] getUploadMediaExtensions() {

        return StringUtils.split(uploadMediaExtension, SEPARATOR);
    }

    /**
     * 获取允许上传文件扩展名
     * 
     * @return 允许上传文件扩展名
     */
    public String[] getUploadFileExtensions() {

        return StringUtils.split(uploadFileExtension, SEPARATOR);
    }

    /**
     * 设置精度
     * 
     * @param amount
     *            数值
     * @return 数值
     */
    public BigDecimal setScale(BigDecimal amount) {

        if (amount == null) {
            return null;
        }
        int roundingMode;
        if (getPriceRoundType() == RoundType.roundUp) {
            roundingMode = BigDecimal.ROUND_UP;
        }
        else if (getPriceRoundType() == RoundType.roundDown) {
            roundingMode = BigDecimal.ROUND_DOWN;
        }
        else {
            roundingMode = BigDecimal.ROUND_HALF_UP;
        }
        return amount.setScale(getPriceScale(), roundingMode);
    }

    /**
     * 是否需要通知
     * 
     * @return
     */
    public Boolean isNotify() {

        return isNotify;
    }

    public void setIsNotify(Boolean isNotify) {

        this.isNotify = isNotify;
    }

    public Integer getConnectTimeout() {

        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {

        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {

        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {

        this.readTimeout = readTimeout;
    }

    public String getAvatarPath() {

        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {

        this.avatarPath = avatarPath;
    }

    public String getDefaultAvatar() {

        return defaultAvatar;
    }

    public void setDefaultAvatar(String defaultAvatar) {

        this.defaultAvatar = defaultAvatar;
    }

    public Integer getAvatarMaxSize() {

        return avatarMaxSize;
    }

    public void setAvatarMaxSize(Integer avatarMaxSize) {

        this.avatarMaxSize = avatarMaxSize;
    }

    /**
     * 工单附件
     * 
     * @return
     */
    public String getTicketAttachmentPath() {

        return ticketAttachmentPath;
    }

    /**
     * 工单附件
     * 
     * @param ticketAttachmentPath
     */
    public void setTicketAttachmentPath(String ticketAttachmentPath) {

        this.ticketAttachmentPath = ticketAttachmentPath;
    }

    public String getTempFilePath() {
    
        return tempFilePath;
    }

    public void setTempFilePath(String tempFilePath) {
    
        this.tempFilePath = tempFilePath;
    }

    
    public String getPartnerServlet() {
    
        return partnerServlet;
    }

    
    public void setPartnerServlet(String partnerServlet) {
    
        this.partnerServlet = partnerServlet;
    }

    
}