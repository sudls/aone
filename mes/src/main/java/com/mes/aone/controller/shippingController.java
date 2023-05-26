package com.mes.aone.controller;

import com.mes.aone.dto.ShipmentDTO;
import com.mes.aone.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class shippingController {

    private ShipmentRepository shipmentRepository;

    public shippingController(ShipmentRepository shipmentRepository){
        this.shipmentRepository=shipmentRepository;
    }
    @GetMapping(value="/shipping")
    public String shipmentPage(Model model){

        List<ShipmentDTO> shipmentDTOList = shipmentRepository.findShipmentDetails();
        model.addAttribute("shipments", shipmentDTOList);

        return"pages/shippingPage";
    }
}
