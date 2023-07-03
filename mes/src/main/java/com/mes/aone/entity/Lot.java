package com.mes.aone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Lot {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lotID;

    private String lotNum;    // 로트번호

    private String parentLotNum;    // 부모로트번호

    @OneToOne
    @JoinColumn(name = "production_id")
    private Production production;   //공정단계
}
