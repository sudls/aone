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

    private String process1;    //원료계량

    private String process2;    //전처리

    private String process3;    //ㅊ출

    private String process4;    //혼합및살균

    private String process5;    //충진

    private String process6;    //검사

    private String process7;    //포장
}
