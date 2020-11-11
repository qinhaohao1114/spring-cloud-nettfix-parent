package com.study.netflix.service.impl;

import com.study.netfliex.pojo.AccountTbl;
import com.study.netflix.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void debit(String userId, Integer orderMoney) {
        String url = "http://localhost:9070/account";
        AccountTbl accountTbl = new AccountTbl();
        accountTbl.setUserId(userId);
        accountTbl.setMoney(orderMoney);
        restTemplate.put(url, accountTbl);
    }
}
