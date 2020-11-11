package com.study.netflix.controller;

import com.study.netfliex.pojo.Order;
import com.study.netfliex.pojo.Storage;
import com.study.netflix.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:20 上午
 **/
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PutMapping
    public void update(@RequestBody Storage storage) {

        storageService.update(storage);
    }
}
