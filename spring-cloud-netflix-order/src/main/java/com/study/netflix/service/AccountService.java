package com.study.netflix.service;

public interface AccountService {

    void debit(String userId, Integer orderMoney);
}
