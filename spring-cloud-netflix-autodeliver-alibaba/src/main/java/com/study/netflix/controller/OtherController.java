package com.study.netflix.controller;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.out;

@RestController
@RequestMapping("/other")
public class OtherController {

    final Object a = new Object();

    @GetMapping
    public String getOther() {
        print();
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 20; i++) {
                print();
            }
        });
        return "other test";
    }

    private void print() {
        //a.hashCode();
        out.println("befor lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());//无锁：偏向锁？
        synchronized (a) {
            out.println("lock ing");
            out.println(ClassLayout.parseInstance(a).toPrintable());
        }

        out.println("after lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}
