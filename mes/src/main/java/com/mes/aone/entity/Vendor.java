package com.mes.aone.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class Vendor {
    // id   auto_increment
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vendorId;

    // 업체명
    @Column(nullable = false, length = 50)
    private String vendorName;

    // 연락처
    @Column(nullable = false, length = 13)
    private String vendorTel;

    // 주소
    @Column(nullable = false)
    private String vendorAddr;

    // 사업자등록번호
    @Column(nullable = false, length = 15)
    private String vendorNumber;

    // 비고
    @Column
    private String vendorMemo;
}
