package com.mes.aone.service;

import com.mes.aone.entity.QWorkResult;
import com.mes.aone.entity.WorkResult;
import com.mes.aone.repository.WorkResultRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkResultService {
    private final WorkResultRepository workResultRepository;

    // 기간검색
    public List<WorkResult> searchBetweenDate(LocalDateTime startDateTime, LocalDateTime endDateTime){
        QWorkResult qWorkResult = QWorkResult.workResult;
        BooleanBuilder builder = new BooleanBuilder();

        if (startDateTime != null && endDateTime != null) {
            builder.and(qWorkResult.workFinishDate.between(startDateTime, endDateTime));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "workOrder.workOrderId");
        return (List<WorkResult>) workResultRepository.findAll(builder, sort);
    }
}
