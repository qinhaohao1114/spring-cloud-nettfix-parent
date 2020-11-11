package com.study.netflix.service.impl;

import com.study.netfliex.pojo.Storage;
import com.study.netflix.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void deduct(String commodityCode, Integer orderCount) {
        String url = "http://localhost:9090/storage";
        Storage storage = new Storage();
        storage.setCommodityCode(commodityCode);
        storage.setCount(orderCount);
        restTemplate.put(url, storage);
    }
}
