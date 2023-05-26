package com.mes.aone.repository;

import com.mes.aone.entity.Facility;
import com.mes.aone.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, String> {

    Facility findByFacilityId(String facilityId);

}
