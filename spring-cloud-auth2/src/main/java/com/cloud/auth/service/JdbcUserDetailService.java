package com.cloud.auth.service;

import com.cloud.auth.bean.Account;
import com.cloud.auth.dao.UserDao;
import com.study.netfliex.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JdbcUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = userDao.findByUsername(username);
        Account account = new Account();
        account.setTid("1");
        account.setLna(users.getUsername());
        account.setPassword(users.getPassword());
        account.setSub(users.getId());
        account.setUty("1");
        account.setTyp("1");
        account.setIsa("1");
        return account;
    }
}
