package com.study.netflix.service.impl;

import com.study.netflix.service.BussinessService;
import com.study.netflix.service.OrderService;
import com.study.netflix.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:17 上午
 **/
@Slf4j
@Service
public class BussinessServiceImpl implements BussinessService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private OrderService orderService;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "nacos-demo-tx")
    public void purchase(String userId, String commodityCode, int orderCount) {
        log.info("purchase begin ... xid: " + RootContext.getXID());
        storageService.deduct(commodityCode, orderCount);
        orderService.create(userId, commodityCode, orderCount);
        throw new RuntimeException("xxx");
    }
}
