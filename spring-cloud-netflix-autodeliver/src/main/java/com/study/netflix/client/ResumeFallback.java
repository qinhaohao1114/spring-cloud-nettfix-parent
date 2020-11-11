package com.study.netflix.client;

import org.springframework.stereotype.Component;

@Component
public class ResumeFallback implements ResumeFeignClient {
    @Override
    public Integer findResumeOpenState(Long userId) {
        return -6;
    }
}
