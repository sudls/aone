package com.mes.aone.service;

import com.mes.aone.entity.Production;
import com.mes.aone.entity.QProduction;
import com.mes.aone.repository.ProductionRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductionService {

   private final ProductionRepository productionRepository;

   // 다중검색
    public List<Production> searchProduction(String productionName, String processPlanStage){
        QProduction qProduction = QProduction.production;
        BooleanBuilder builder = new BooleanBuilder();

        if(productionName != null){
            builder.and(qProduction.productionName.eq(productionName));
        }
        if(processPlanStage != null){
            builder.and(qProduction.processPlan.processStage.eq(processPlanStage));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "productionId");
        return (List<Production>) productionRepository.findAll(builder, sort);
    }


}
