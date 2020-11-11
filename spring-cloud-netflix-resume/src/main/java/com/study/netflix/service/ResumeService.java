package com.study.netflix.service;

import com.study.netfliex.pojo.Resume;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:16 上午
 **/
public interface ResumeService {

    Resume findDefaultResumeByUserId(Long userId);

}
