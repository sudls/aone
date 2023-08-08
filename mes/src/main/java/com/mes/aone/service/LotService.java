package com.mes.aone.service;


import com.mes.aone.entity.Lot;
import com.mes.aone.entity.Production;
import com.mes.aone.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LotService {

    private final LotRepository lotRepository;

    public List<Lot> getLot(String lotNum) {
        List<Lot> lotList = new ArrayList<>();
        while (lotRepository.getLot(lotNum) != null){
            lotList.add(lotRepository.getLot(lotNum)); // 리스트에 해당 로트의 생산계획 추가
            lotNum = lotRepository.getLot(lotNum).getParentLotNum(); // 로트를 부모로트로 바꿈
        }
        return lotList;
    }

    public List<Lot> getFinalLotList() {
        List<Lot> allLotList = new ArrayList<>();
        List<Lot> finalLotList = new ArrayList<>();
        allLotList = lotRepository.findAll(Sort.by(Sort.Direction.DESC, "lotID"));
        for (int i = 0; i < allLotList.size(); i++){
            if (allLotList.get(i).getProduction().getProcessPlan().getProcessStage().equals("포장")){
                finalLotList.add(allLotList.get(i));
            }
        }
        return finalLotList;
    }

}
