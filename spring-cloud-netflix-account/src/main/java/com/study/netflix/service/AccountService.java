package com.study.netflix.service;

import com.study.netfliex.pojo.AccountTbl;
import com.study.netfliex.pojo.Order;
import com.study.netfliex.pojo.Resume;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:16 上午
 **/
public interface AccountService {

    AccountTbl create(AccountTbl accountTbl);

    void update(AccountTbl accountTbl);
}
