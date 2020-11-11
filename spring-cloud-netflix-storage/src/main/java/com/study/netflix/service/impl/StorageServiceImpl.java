package com.study.netflix.service.impl;

import com.study.netfliex.pojo.Storage;
import com.study.netflix.dao.StorageDao;
import com.study.netflix.service.StorageService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:17 上午
 **/
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageDao storageDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Storage create(Storage storage) {

        return storageDao.save(storage);
    }

    @Override
    public void update(Storage storage) {
        log.info("Storage Service Begin ... xid: " + RootContext.getXID());
        log.info("Deducting inventory SQL: update storage_tbl set count = count - {} where commodity_code = {}",
                storage.getCount(), storage.getCommodityCode());
        jdbcTemplate.update("update storage_tbl set count = count - ? where commodity_code = ?",
                new Object[]{storage.getCount(), storage.getCommodityCode()});
        log.info("Storage Service End ... ");
    }
}
