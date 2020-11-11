package com.study.netflix.service;

public interface OrderService {

    void create(String userId, String commodityCode, int orderCount);
}
