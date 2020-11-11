package com.study.netfliex.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "account_tbl")
public class AccountTbl {

    @Id
    private Long id;

    private String userId;

    private Integer money;


}
