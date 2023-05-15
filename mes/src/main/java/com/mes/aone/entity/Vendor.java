package com.mes.aone.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Vendor {
    // id   auto_increment
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long vendorId;

    // 업체명
    @Column(length = 50)
    @NotNull
    private String vendorName;

    // 연락처
    @Column(length = 12)
    @NotNull
    private String vendorTel;

    // 주소
    @Column
    @NotNull
    private String vendorAddr;

    // 사업자등록번호
    @Column(length = 15)
    @NotNull
    private String vendorNumber;

    // 비고
    @Column
    private String vendorMemo;
}
