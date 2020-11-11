package com.cloud.auth.dao;

import com.study.netfliex.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
}
