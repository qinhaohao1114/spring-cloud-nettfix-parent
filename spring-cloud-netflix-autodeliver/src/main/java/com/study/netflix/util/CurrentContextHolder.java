package com.study.netflix.util;

import com.study.netfliex.pojo.Account;

public class CurrentContextHolder {

    private final static ThreadLocal<Account> threadLocal = new ThreadLocal<>();

    public static void put(Account account) {
        threadLocal.remove();
        threadLocal.set(account);
    }

    public static Account get() {
        return threadLocal.get();
    }
}
