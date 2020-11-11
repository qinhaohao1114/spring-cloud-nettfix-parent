package com.study.netflix.dao;

import com.study.netfliex.pojo.AccountTbl;
import com.study.netfliex.pojo.Order;
import com.study.netfliex.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:15 上午
 **/
public interface AccountDao extends JpaRepository<AccountTbl, Integer> {

}
