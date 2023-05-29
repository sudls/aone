package com.mes.aone.controller;

import com.mes.aone.dto.ShipmentDTO;
import com.mes.aone.entity.Shipment;
import com.mes.aone.repository.ShipmentRepository;
import com.mes.aone.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class shippingController {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentService shipmentService;
    
    @GetMapping(value="/shipping")
    public String shipmentPage(Model model){

        List<ShipmentDTO> shipmentDTOList = shipmentRepository.findShipmentDetails();
        model.addAttribute("shipments", shipmentDTOList);

        return"pages/shippingPage";
    }

    // 출하현황 search
    @GetMapping(value="/shipping/search")
    public String shipmentPage( @RequestParam(required = false) Long salesOrderId,
                                @RequestParam(required = false) String vendorId,
                                @RequestParam(required = false) String shipmentProduct,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                @RequestParam(required = false) String shipmentState, Model model){
        // 날짜 변환
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate != null && endDate != null){
            startDateTime =  LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime =  LocalDateTime.of(endDate, LocalTime.MAX);
        }

        List<Shipment> shipmentList = shipmentService.searchShipment(salesOrderId, vendorId, shipmentProduct, startDateTime, endDateTime, shipmentState);
        List<ShipmentDTO> shipmentDTOList = ShipmentDTO.of(shipmentList);
        model.addAttribute("shipments", shipmentDTOList);
        return"pages/shippingPage";
    }


}
