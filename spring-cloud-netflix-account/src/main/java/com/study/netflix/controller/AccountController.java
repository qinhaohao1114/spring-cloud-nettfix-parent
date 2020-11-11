package com.study.netflix.controller;

import com.study.netfliex.pojo.AccountTbl;
import com.study.netflix.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:20 上午
 **/
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PutMapping
    public void update(@RequestBody AccountTbl accountTbl) {

        accountService.update(accountTbl);
    }
}
