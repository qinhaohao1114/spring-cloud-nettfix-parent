package com.study.netflix.service;

public interface StorageService {
    void deduct(String commodityCode, Integer orderCount);
}
