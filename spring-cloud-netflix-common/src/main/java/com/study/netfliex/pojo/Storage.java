package com.study.netfliex.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "storage_tbl")
public class Storage {
    @Id
    private Long id;

    private String commodityCode;

    private Integer count;
}
