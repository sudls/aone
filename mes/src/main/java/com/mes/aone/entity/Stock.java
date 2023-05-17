package com.mes.aone.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class Stock {
    // id    auto_increment
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stockId;

    // 제품명
    @Column(name="stock_name", nullable = false, length = 50)
    private String stockName;

    // 수량
    @Column(nullable = false)
    private int stockQty;

}
