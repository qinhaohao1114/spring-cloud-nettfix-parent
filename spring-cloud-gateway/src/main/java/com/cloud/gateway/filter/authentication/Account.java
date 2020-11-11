package com.cloud.gateway.filter.authentication;

import lombok.Data;

@Data
public class Account {


    //签发者
    private String iss;

    //签发时间
    private Integer iat;

    //accountId
    private Long sub;

    //type
    private String typ;

    //tenantId
    private String tid;

    //IsAdmin
    private String isa;

    //登录名
    private String lna;

    //UserType
    private String uty;

    //过期时间
    private String exp;
}
