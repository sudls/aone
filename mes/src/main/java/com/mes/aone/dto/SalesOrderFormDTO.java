package com.mes.aone.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class SalesOrderFormDTO {
    private String selectedOrderIdList;

    private static ModelMapper modelMapper = new ModelMapper();
}
