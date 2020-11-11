package com.cloud.auth.bean;

import com.cloud.auth.annotation.Ig;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
public class Account implements UserDetails {

    @Ig
    private String password;

    //签发者
    private String iss = "dfocus";

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return lna;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
