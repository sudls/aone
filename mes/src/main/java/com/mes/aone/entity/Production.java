package com.mes.aone.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Production {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productionId;  //생산테이블id

    @Column(length = 50, nullable = false)
    private String productionName;  //제품명

    @Column(nullable = false)
    private int productionQty;  //생산수량

    @OneToOne
    @JoinColumn(name = "processStage")
    private ProcessPlan processPlan;   //공정단계

    @Column(length = 50, nullable = false)
    private String lotNumber;   //lot번호
}

