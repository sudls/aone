package com.mes.aone.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter @ToString

public class Vendor {
    // id
    @Id
    @Column(nullable = false)
    private String vendorId;

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
    private String registration_Number;

    // 비고
    @Column
    private String vendorMemo;

}
