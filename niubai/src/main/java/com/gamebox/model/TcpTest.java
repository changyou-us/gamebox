/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV Project:niubai
 * Package:com.gamebox.model File:TcpTest.java Date:2014年12月15日
 */
package com.gamebox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gamebox.model.BaseEntity;

/**
 * @author wuji
 * 
 */
public class TcpTest extends BaseEntity {

    private static final long serialVersionUID = 9179315887074341179L;

    private String name;

    @JsonProperty
    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

}
