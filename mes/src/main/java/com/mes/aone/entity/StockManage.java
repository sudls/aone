package com.mes.aone.entity;

import com.mes.aone.constant.StockManageState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class StockManage {
    //id    auto_increment
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockManageId;

    // 입출고날짜
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime stockDate;

    // 수량
    @Column(nullable = false)
    private int stockManageQty;

    // 입출고상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockManageState stockManageState;

    // 제품명 : 양배추박스, 양배추포
    @Column(nullable = false, length = 50)
    private String stockManageName;

    //stock 엔티티와 연결
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "stockId", referencedColumnName = "stockId")
    private Stock stock;

}
