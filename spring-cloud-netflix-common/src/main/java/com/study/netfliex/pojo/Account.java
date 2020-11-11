package com.study.netfliex.pojo;


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

    @Override
    public String toString() {
        return "Account{" +
                "iss='" + iss + '\'' +
                ", iat=" + iat +
                ", sub=" + sub +
                ", typ='" + typ + '\'' +
                ", tid='" + tid + '\'' +
                ", isa='" + isa + '\'' +
                ", lna='" + lna + '\'' +
                ", uty='" + uty + '\'' +
                ", exp='" + exp + '\'' +
                '}';
    }
}
