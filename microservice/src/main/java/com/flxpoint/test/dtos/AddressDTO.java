package com.flxpoint.test.dtos;

import lombok.Data;

@Data
public class AddressDTO {
    private Integer id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
