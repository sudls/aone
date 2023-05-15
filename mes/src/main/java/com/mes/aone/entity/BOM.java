package com.mes.aone.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name= "BOM")
public class BOM {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bomId;

    @Column(length = 50, nullable = false)
    private String bomProduct;

    @Column(length = 50, name = "material_1")
    private String material1;

    @Column(name = "amount_1")
    private Integer amount1;

    @Column(length = 50, name = "material_2")
    private String material2;

    @Column(name = "amount_2")
    private Integer amount2;

    @Column(length = 50, name = "material_3")
    private String material3;

    @Column(name = "amount_3")
    private Integer amount3;

}
