package com.mes.aone.repository;

import com.mes.aone.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Vendor findByVendorId(String vendorId); //원자재 테이블이 참조

}
