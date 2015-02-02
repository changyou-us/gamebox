/*
 * Copyright 2013-2014 gamebox. All rights reserved.
 * Support: http://www.gamebox.com
 * Team: WG DEV
 * Project:niubai
 * Package:com.gamebox.model
 * File:BaseEntity.java
 * Date:2014年12月15日
 */
package com.gamebox.model;


/*
 * Copyright 2012-2013 gamebox. All rights reserved.
 * Support: http://www.gamebox.com
 * Team: WG DEV
 */

import java.io.Serializable;
import java.util.Date;

import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity - 基类
 * 
 * @author Wu Ji
 * @version 1.0
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -67188388306700736L;

    /** "ID"属性名称 */
    public static final String ID_PROPERTY_NAME = "id";

    /** "创建日期"属性名称 */
    public static final String CREATE_DATE_PROPERTY_NAME = "createDate";

    /** "修改日期"属性名称 */
    public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";

    /** ID */
    private Integer id;

    /** 创建日期 */
    private Date createDate;

    /** 修改日期 */
    private Date modifyDate;

    /**
     * 获取ID
     * 
     * @return ID
     */
    @JsonProperty
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     * 
     * @param id
     *            ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取创建日期
     * 
     * @return 创建日期
     */
    @JsonProperty
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建日期
     * 
     * @param createDate
     *            创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改日期
     * 
     * @return 修改日期
     */
    @JsonProperty
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 设置修改日期
     * 
     * @param modifyDate
     *            修改日期
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * 重写equals方法
     * 
     * @param obj
     *            对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        BaseEntity other = (BaseEntity) obj;
        return getId() != null ? getId().equals(other.getId()) : false;
    }

    /**
     * 重写hashCode方法
     * 
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

}