package com.mes.aone.service;

import com.mes.aone.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;

//    public String createVendor(Vendor vendor){
//        vendor.createVendor();
//        vendorRepository.save(vendor);
//
//        return vendor.getVendorId();
//    }

}
