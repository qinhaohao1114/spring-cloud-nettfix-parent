package com.study.netflix.service;

import com.study.netfliex.pojo.Order;
import com.study.netfliex.pojo.Resume;
import com.study.netfliex.pojo.Storage;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:16 上午
 **/
public interface StorageService {

    Storage create(Storage storage);

    void update(Storage storage);
}
