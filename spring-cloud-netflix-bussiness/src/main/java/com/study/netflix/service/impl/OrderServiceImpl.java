package com.study.netflix.service.impl;

import com.study.netfliex.pojo.Order;
import com.study.netflix.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void create(String userId, String commodityCode, int orderCount) {
        String url = "http://localhost:9080/order";
        Order order = new Order();
        order.setUserId(userId);
        order.setCount(orderCount);
        order.setCommodityCode(commodityCode);
        restTemplate.postForEntity(url, order, Order.class);
    }
}
