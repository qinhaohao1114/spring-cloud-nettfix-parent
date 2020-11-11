package com.study.netflix.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// name：调⽤的服务名称，和服务提供者yml⽂件中spring.application.name保持⼀致
@FeignClient(name = "netflix-service-resume", fallback = ResumeFallback.class, path = "/resume")
public interface ResumeFeignClient {
    //调⽤的请求路径
    @RequestMapping(value = "/openstate/{userId}", method =
            RequestMethod.GET)
    Integer findResumeOpenState(@PathVariable(value = "userId")
                                        Long userId);
}