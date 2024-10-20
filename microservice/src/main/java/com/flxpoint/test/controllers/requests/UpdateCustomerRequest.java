package com.flxpoint.test.controllers.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCustomerRequest {

    @NotNull(message = "The field 'id' must not be null")
    private Integer id;


    @NotEmpty(message = "The field 'firstName' is required")
    private String firstName;

    @NotEmpty(message = "The field 'lastName' is required")
    private String lastName;

    @NotEmpty(message = "The field 'email' is required")
    private String email;

    @NotEmpty(message = "The field 'phoneNumber' is required")
    private String phoneNumber;

    @NotNull(message = "The field 'address' must not be null")
    private CreateAddressRequest address;
}
