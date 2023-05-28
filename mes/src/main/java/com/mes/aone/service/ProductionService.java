package com.mes.aone.service;

import com.mes.aone.entity.Production;
import com.mes.aone.entity.QProduction;
import com.mes.aone.repository.ProductionRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductionService {
    @Autowired
   private ProductionRepository productionRepository;

    @Transactional
    public List<Production> searchProduction(String productionName, String processPlanStage){
        QProduction qProduction = QProduction.production;
        BooleanBuilder builder = new BooleanBuilder();

        if(productionName != null){
            builder.and(qProduction.productionName.eq(productionName));
        }
        if(processPlanStage != null){
            builder.and(qProduction.processPlan.processStage.eq(processPlanStage));
        }
        System.out.println("여기ㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣ");
        return (List<Production>) productionRepository.findAll(builder);
    }



}
