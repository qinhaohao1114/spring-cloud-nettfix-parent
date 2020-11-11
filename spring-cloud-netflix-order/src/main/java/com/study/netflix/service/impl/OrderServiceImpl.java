package com.study.netflix.service.impl;

import com.study.netfliex.pojo.Order;
import com.study.netfliex.pojo.Resume;
import com.study.netflix.dao.OrderDao;
import com.study.netflix.service.AccountService;
import com.study.netflix.service.OrderService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:17 上午
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountService accountService;

    @Override
    public Order create(Order order) {
        log.info("Order Service Begin ... xid: " + RootContext.getXID());

        String commodityCode = order.getCommodityCode();
        Integer orderCount = order.getCount();
        // 计算订单金额
        int orderMoney = calculate(commodityCode, orderCount);

        // 从账户余额扣款
        accountService.debit(order.getUserId(), orderMoney);

        order.setMoney(orderMoney);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info(
                "Order Service SQL: insert into order_tbl (user_id, commodity_code, count, money) values ({}, {}, {}, {})",
                order.getUserId(), commodityCode, orderCount, orderMoney);

        jdbcTemplate.update(con -> {
            PreparedStatement pst = con.prepareStatement(
                    "insert into order_tbl (user_id, commodity_code, count, money) values (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setObject(1, order.getUserId());
            pst.setObject(2, order.getCommodityCode());
            pst.setObject(3, order.getCount());
            pst.setObject(4, order.getMoney());
            return pst;
        }, keyHolder);

        order.setId(keyHolder.getKey().longValue());

        log.info("Order Service End ... Created " + order);
        return order;
    }

    private int calculate(String commodityId, int orderCount) {
        return 200 * orderCount;
    }
}
