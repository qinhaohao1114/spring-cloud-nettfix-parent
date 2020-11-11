package com.study.netflix.service.impl;

import com.study.netfliex.pojo.Resume;
import com.study.netflix.dao.ResumeDao;
import com.study.netflix.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:17 上午
 **/
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;

    @Override
    public Resume findDefaultResumeByUserId(Long userId) {
        Resume resume = new Resume();
        resume.setUserId(userId);
        resume.setIsDefault(1);
        Example<Resume> example = Example.of(resume);
        return resumeDao.findOne(example).get();
    }
}
