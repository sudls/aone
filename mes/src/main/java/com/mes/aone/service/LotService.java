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

    public List<Lot> getBackwardLot(String lotNum) {
        List<Lot> lotList = new ArrayList<>();
        while (lotRepository.getBackwardLot(lotNum) != null){ // 로트가 존재하는지 확인
            lotList.add(lotRepository.getBackwardLot(lotNum)); // 리스트에 해당 로트를 추가
            lotNum = lotRepository.getBackwardLot(lotNum).getParentLotNum(); // lotNum을 부모로트로 초기화
        }
        return lotList;
    }

    public List<Lot> getForwardLot(String lotNum) {
        List<Lot> lotList = new ArrayList<>();
        List<Lot> beforeLotList = new ArrayList<>();
        List<Lot> currentLotList = new ArrayList<>();

        lotList.add(lotRepository.getBackwardLot(lotNum));
        beforeLotList.add(lotRepository.getBackwardLot(lotNum));

        while (true){
            for (int i = 0; i < beforeLotList.size(); i++){
                currentLotList.addAll(lotRepository.getForwardLot(beforeLotList.get(i).getLotNum()));
            }
            lotList.addAll(currentLotList); // 조회한 로트 추가
            beforeLotList.clear(); // 이전 로트리스트 삭제
            beforeLotList.addAll(currentLotList); // 이전 로트리스트 재설정
            currentLotList.clear(); // 현재 로트리스트 삭제

            if (beforeLotList.get(0).getProduction().getProcessPlan().getProcessStage().equals("포장")) break;
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
