package com.study.netflix.service.impl;

import com.study.netfliex.pojo.AccountTbl;
import com.study.netflix.dao.AccountDao;
import com.study.netflix.service.AccountService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:17 上午
 **/
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public AccountTbl create(AccountTbl accountTbl) {

        return accountDao.save(accountTbl);
    }

    @Override
    public void update(AccountTbl accountTbl) {
        log.info("Account Service ... xid: " + RootContext.getXID());
        log.info("Deducting balance SQL: update account_tbl set money = money - {} where user_id = {}", accountTbl.getMoney(),
                accountTbl.getUserId());

        jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", new Object[]{accountTbl.getMoney(), accountTbl.getUserId()});
        log.info("Account Service End ... ");
    }
}
