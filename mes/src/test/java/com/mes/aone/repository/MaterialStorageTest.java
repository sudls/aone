package com.mes.aone.repository;


import com.mes.aone.constant.SalesStatus;
import com.mes.aone.dto.OrderDTO;
import com.mes.aone.service.MaterialService;
import com.mes.aone.service.SalesOrderService;
import com.mes.aone.util.Calculator;
import com.mes.aone.util.MESInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class MaterialStorageTest {

    @Autowired
    MaterialStorageRepository materialStorageRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    MaterialService materialService;
    @Autowired
    SalesOrderService salesOrderService;

    @Test
    public void confirmSales() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setSalesOrderId(10L);
        orderDTO.setProductName("양배추즙");
        orderDTO.setVendorId("ven-11");
        orderDTO.setSalesQty(2000);
        orderDTO.setSalesDate(LocalDateTime.now());
        orderDTO.setSalesStatus(SalesStatus.B);

        MESInfo mesInfo = new MESInfo();
        Calculator calculator = new Calculator(mesInfo);
        mesInfo.setProductName(orderDTO.getProductName()); //수주 제품명
        mesInfo.setSalesQty(orderDTO.getSalesQty()); // 수주량
        mesInfo.setSalesDay(orderDTO.getSalesDate()); // 수주일
        mesInfo.setSalesOrderId(orderDTO.getSalesOrderId()); // 수주 아이디

        // 예상납품일 계산기 실행
        if (mesInfo.getProductName().equals("양배추즙") || mesInfo.getProductName().equals("흑마늘즙")){ // 즙 공정
            String purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
            if (purchaseCheck.equals("enough")){ // 재고가 충분하면
                mesInfo.setEstDelivery(LocalDateTime.now()); // 당일 출고
            } else {
                calculator.materialArrived(); // 발주 원자재 도착시간 메서드 실행
                calculator.measurement(); // 원료계량 메서드 실행
                calculator.preProcessing(); // 전처리 메서드 실행
                calculator.extraction(); // 추출 메서드 실행
                calculator.fill();//충진 메서드 실행
                calculator.examination();//검사 메서드 실행
                calculator.cooling();//열교환 메서드 실행
                calculator.packaging(); // 포장 메서드 실행
            }
        }else { // 젤리스틱 공정
            String purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
            if (purchaseCheck.equals("enough")){ //재고가 충분하면
                mesInfo.setEstDelivery(LocalDateTime.now()); // 당일 출고
            } else {
                calculator.materialArrived(); // 발주 원자재 도착시간 메서드 실행
                calculator.measurement(); // 원료계량 메서드 실행
                calculator.extraction(); // 추출 메서드 실행
                calculator.fill();//충진 메서드 실행
                calculator.examination();//검사 메서드 실행
                calculator.cooling();//열교환 메서드 실행
                calculator.packaging(); // 포장 메서드 실행
            }
        }

        salesOrderService.createProcessPlan(mesInfo);
        salesOrderService.createPurchaseOrder(mesInfo);








    }

/*    @Test // 원자재 입출고 관리
    public void insertMaterialStorage(){
        MaterialStorage materialStorage = new MaterialStorage();

        Material material = materialRepository.findByMaterialName("석류액기스");
        materialStorage.setMaterialName(material);

        materialStorage.setMaterialQty(55);
        materialStorage.setMaterialStorageState(State.I);
        materialStorage.setMStorageDate(new Date());
        materialStorage.setUnit("kg");

        materialStorageRepository.save(materialStorage);

    }*/

    @Test // 원자재 입출고 현황
    public void selectMaterialStorage(){
        System.out.println("자재입출고" + materialService.getMaterialStorage().toString());
    }
}
