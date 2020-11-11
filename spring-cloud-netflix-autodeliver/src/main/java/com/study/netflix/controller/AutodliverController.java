package com.study.netflix.controller;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import com.study.netflix.client.ResumeFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author qinhaohao
 * @Date 2020/7/31 11:31 上午
 **/
@RestController
@RequestMapping("/autodeliver")
@RefreshScope
public class AutodliverController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ResumeFeignClient resumeFeignClient;


    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${now.ip}")
    private String ip;


    @GetMapping
    private String getDeliver() {

        return "autodeliver test";
    }

    //    @GetMapping("/checkState/{userId}")
//    public Integer findResumeOpenState(@PathVariable Long userId){
////        Integer forObject = restTemplate.getForObject("http://localhost:9090/resume/openstate/" + userId, Integer.class);
////        System.out.println("====>调用简历微服务，获取到用户"+userId+"的默认简历状态为:" +forObject);
////        return forObject;
//        List<ServiceInstance> instances = discoveryClient.getInstances("netflix-service-resume");
//        ServiceInstance serviceInstance = instances.get(0);
//        String host = serviceInstance.getHost();
//        int port = serviceInstance.getPort();
//        String url="http://"+host+":"+port+"resume/openstate/"+userId;
//        Integer forObject = restTemplate.getForObject(url, Integer.class);
//        System.out.println("====>调用简历微服务，获取到用户"+userId+"的默认简历状态为:" +forObject);
//        return forObject;
//    }

    @GetMapping("/ip")
    public String getIp() {

        return ip;
    }

    @GetMapping("/checkState/{userId}")
    public Integer findResumeOpenState(@PathVariable Long userId) {
//        Integer forObject = restTemplate.getForObject("http://localhost:9090/resume/openstate/" + userId, Integer.class);
//        System.out.println("====>调用简历微服务，获取到用户"+userId+"的默认简历状态为:" +forObject);
//        return forObject;
        String url = "http://netflix-service-resume/resume/openstate/" + userId;
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        System.out.println("====>调用简历微服务，获取到用户" + userId + "的默认简历状态为:" + forObject);
        return forObject;
    }

    @GetMapping("/feign/checkState/{userId}")
    public Integer findFeignResumeOpenState(@PathVariable Long userId) {
//        Integer forObject = restTemplate.getForObject("http://localhost:9090/resume/openstate/" + userId, Integer.class);
//        System.out.println("====>调用简历微服务，获取到用户"+userId+"的默认简历状态为:" +forObject);
//        return forObject;
        Integer result = resumeFeignClient.findResumeOpenState(userId);
        System.out.println("====>调用简历微服务，获取到用户" + userId + "的默认简历状态为:" + result);
        return result;
    }

//    @GetMapping("/checkStateTimeout/{userId}")
//    @HystrixCommand(
//            threadPoolKey = "findResumeOpenStateTimeOut",
//            threadPoolProperties = {
//                    @HystrixProperty(name = "coreSize",value = "1"),
//                    @HystrixProperty(name = "maxQueueSize",value = "20")
//            },
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds" ,value = "2000"),
//                    @HystrixProperty(name =
//                            "metrics.rollingStats.timeInMilliseconds",value = "8000"),
//                    @HystrixProperty(name =
//                            "circuitBreaker.requestVolumeThreshold",value = "2"),
//                    @HystrixProperty(name =
//                            "circuitBreaker.errorThresholdPercentage",value = "50"),
//                    @HystrixProperty(name =
//                            "circuitBreaker.sleepWindowInMilliseconds",value = "3000")
//            }
//    )
//    public Integer findResumeOpenStateTimeOut(@PathVariable Long userId){
//        String url = "http://netflix-service-resume/resume/openstate/" + userId;
//        Integer forObject = restTemplate.getForObject(url, Integer.class);
//        System.out.println("====>调用简历微服务，获取到用户" + userId + "的默认简历状态为:" + forObject);
//        return forObject;
//    }
//
//    public Integer myFallBack(Long userId){
//        return -1;
//    }
//
//    @GetMapping("/checkStateTimeoutFallback/{userId}")
//    @HystrixCommand(
//            threadPoolKey = "findResumeOpenStateTimeOutFallBack",
//            threadPoolProperties = {
//                    @HystrixProperty(name = "coreSize",value = "2"),
//                    @HystrixProperty(name = "maxQueueSize",value = "20")
//            },
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds" ,value = "2000")
//            },fallbackMethod = "myFallBack"
//    )
//    public Integer findResumeOpenStateTimeOutFallBack(@PathVariable Long userId){
//        String url = "http://netflix-service-resume/resume/openstate/" + userId;
//        Integer forObject = restTemplate.getForObject(url, Integer.class);
//        System.out.println("====>调用简历微服务，获取到用户" + userId + "的默认简历状态为:" + forObject);
//        return forObject;
//    }

}
