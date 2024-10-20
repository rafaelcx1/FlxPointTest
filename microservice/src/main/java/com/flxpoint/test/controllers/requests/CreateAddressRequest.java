package com.flxpoint.test.controllers.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateAddressRequest {

    @NotEmpty(message = "The field 'street' is required")
    private String street;

    @NotEmpty(message = "The field 'city' is required")
    private String city;

    @NotEmpty(message = "The field 'state' is required")
    private String state;

    @NotEmpty(message = "The field 'zipCode' is required")
    private String zipCode;
}
