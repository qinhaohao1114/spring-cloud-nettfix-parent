package com.study.netflix.controller;

import com.study.netfliex.pojo.Order;
import com.study.netflix.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:20 上午
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order create(@RequestBody Order order) {

        return orderService.create(order);
    }
}
