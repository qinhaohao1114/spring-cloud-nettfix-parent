package com.study.netflix.controller;

import com.study.netfliex.pojo.Order;
import com.study.netflix.service.BussinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:20 上午
 **/
@RestController
@RequestMapping("/bussiness")
public class BussinessController {

    @Autowired
    private BussinessService bussinessService;

    @PostMapping
    public void create(@RequestBody Order order) {

        bussinessService.purchase(order.getUserId(), order.getCommodityCode(), order.getCount());
    }
}
