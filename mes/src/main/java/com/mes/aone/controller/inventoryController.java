package com.mes.aone.controller;

import com.mes.aone.constant.SalesStatus;
import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.MaterialStorageDTO;
import com.mes.aone.dto.MaterialStorageNumDTO;
import com.mes.aone.dto.OrderDTO;
import com.mes.aone.entity.Stock;
import com.mes.aone.entity.StockManage;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import com.mes.aone.service.StockManageService;
import com.mes.aone.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class inventoryController {
    @GetMapping(value = "/inventory1")
    public String inventoryPage1(Model model) {
        //원자재 입출고 현황 (더미)
        List<MaterialStorageDTO> materialStorageDTOList = new ArrayList<>();

        String[] materialNames = {"양배추", "콜라겐", "흑마늘", "벌꿀", "석류액기스", "매실액기스", "스틱파우치", "파우치", "박스"};

        Random random = new Random();

        for (int i = 1; i <= 30; i++) {
            MaterialStorageDTO materialStorageDTO = new MaterialStorageDTO();
            materialStorageDTO.setMaterialName(materialNames[random.nextInt(materialNames.length)]);
            materialStorageDTO.setMStorageState(StockManageState.I);
            materialStorageDTO.setMaterialQty(random.nextInt(1000));
            materialStorageDTO.setMaterialUnit("kg");
            materialStorageDTO.setMStorageDate(LocalDateTime.now());
            materialStorageDTOList.add(materialStorageDTO);
        }
        model.addAttribute("materialStorageDTOList", materialStorageDTOList);

        //원자재 총 수량(더미)
        List<MaterialStorageNumDTO> materialStorageNumDTOList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            MaterialStorageNumDTO materialStorageNumDTO = new MaterialStorageNumDTO();
            materialStorageNumDTO.setMaterialName(materialNames[i]);
            materialStorageNumDTO.setMaterialQty(random.nextInt(1000));
            materialStorageNumDTOList.add(materialStorageNumDTO);
        }
        model.addAttribute("materialStorageNumDTOList", materialStorageNumDTOList);

        return "pages/inventoryPage1";
    }

    @GetMapping(value = "/inventory2")
    public String inventoryPage2() {
        return "pages/inventoryPage2";
    }

    private final StockRepository stockRepository;
    private final StockManageService stockManageService;
    private final StockManageRepository stockManageRepository;

    private final StockService stockService;

    public inventoryController(StockRepository stockRepository, StockManageService stockManageService, StockManageRepository stockManageRepository, StockService stockService) {
        this.stockRepository = stockRepository;
        this.stockManageService = stockManageService;
        this.stockManageRepository = stockManageRepository;
        this.stockService = stockService;
    }
    //완제품 총 수량 조회 , 완제품 입출고 내역 조회
    @GetMapping(value="/inventory3")
    public String inventoryPage3(Model model){
        List<Stock> stockList = stockRepository.findAll();
        model.addAttribute("stockList", stockList);

        List<StockManage> stockManageList = stockManageService.getAllStockManage();
        model.addAttribute("stockManageList", stockManageList);

        return"pages/inventoryPage3";
    }

    //완제품 입출고 내역 조회 및 필터링
    @GetMapping(value = "/inventory3/search")
    public String inventoryPage3(
            @RequestParam(value = "searchProduct", required = false) String searchProduct,
            @RequestParam(value = "searchState", required = false) StockManageState searchState,
            Model model
    ) {
        List<Stock> stockList = stockRepository.findAll();
        model.addAttribute("stockList", stockList);

        List<StockManage> stockManageList;
        if (searchProduct != null && !searchProduct.isEmpty() && searchState != null) {
            stockManageList = stockManageRepository.findByStockManageNameContainingAndStockManageState(searchProduct, searchState);
        } else if (searchProduct != null && !searchProduct.isEmpty()) {
            stockManageList = stockManageRepository.findByStockManageNameContaining(searchProduct);
        } else if (searchState != null) {
            stockManageList = stockManageRepository.findByStockManageState(searchState);
        } else {
            stockManageList = stockManageRepository.findAll();
        }
        model.addAttribute("stockManageList", stockManageList);

        return "pages/inventoryPage3";
    }
}
