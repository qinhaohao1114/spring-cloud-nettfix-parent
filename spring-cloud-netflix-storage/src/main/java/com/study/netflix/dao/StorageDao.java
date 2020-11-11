package com.study.netflix.dao;

import com.study.netfliex.pojo.Order;
import com.study.netfliex.pojo.Resume;
import com.study.netfliex.pojo.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:15 上午
 **/
public interface StorageDao extends JpaRepository<Storage, Integer> {

}
