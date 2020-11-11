package com.study.netfliex.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "order_tbl")
public class Order {
    @Id
    private Long id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;

}
