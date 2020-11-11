package com.study.netflix.service;

import com.study.netfliex.pojo.Order;
import com.study.netfliex.pojo.Resume;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:16 上午
 **/
public interface BussinessService {

    /**
     * 用户订购商品
     *
     * @param userId        用户ID
     * @param commodityCode 商品编号
     * @param orderCount    订购数量
     */
    void purchase(String userId, String commodityCode, int orderCount);
}
