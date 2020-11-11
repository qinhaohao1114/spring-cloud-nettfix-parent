package com.study.netflix.controller;

import com.study.netflix.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:20 上午
 **/
@RestController
@RequestMapping("/resume")
@RefreshScope
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Value("${server.port}")
    private Integer port;

    //    @GetMapping("/openstate/{userId}")
//    public Integer findDefaultResumeState(@PathVariable Long userId){
//        return resumeService.findDefaultResumeByUserId(userId).getIsOpenResume();
//    }
    @GetMapping("/openstate/{userId}")
    public Integer findDefaultResumeState(@PathVariable Long userId) {
//        if (port.equals(9090)){
//            try {
//                TimeUnit.MINUTES.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        return port;
    }
}
