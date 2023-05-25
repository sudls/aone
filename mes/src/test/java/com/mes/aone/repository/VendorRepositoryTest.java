package com.mes.aone.repository;

import com.mes.aone.entity.Vendor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class VendorRepositoryTest {
    @Autowired
    VendorRepository vendorRepository;


}
