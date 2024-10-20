package com.flxpoint.test.mappers.impl;

import com.flxpoint.test.controllers.requests.CreateAddressRequest;
import com.flxpoint.test.controllers.requests.CreateCustomerRequest;
import com.flxpoint.test.entities.Address;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomerRequestToCustomerMapper extends Mapper<CreateCustomerRequest, Customer> {

    @Override
    public Customer map(CreateCustomerRequest from) {
        if (from == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setFirstName(from.getFirstName());
        customer.setLastName(from.getLastName());
        customer.setEmail(from.getEmail());
        customer.setPhoneNumber(from.getPhoneNumber());

        if (from.getAddress() != null) {
            Address address = new Address();
            address.setStreet(from.getAddress().getStreet());
            address.setCity(from.getAddress().getCity());
            address.setState(from.getAddress().getState());
            address.setZipCode(from.getAddress().getZipCode());
            customer.setAddress(address);
        }

        return customer;
    }

    @Override
    public CreateCustomerRequest revert(Customer destination) {
        if (destination == null) {
            return null;
        }

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setFirstName(destination.getFirstName());
        createCustomerRequest.setLastName(destination.getLastName());
        createCustomerRequest.setEmail(destination.getEmail());
        createCustomerRequest.setPhoneNumber(destination.getPhoneNumber());

        if (destination.getAddress() != null) {
            CreateAddressRequest addressRequest = new CreateAddressRequest();
            addressRequest.setStreet(destination.getAddress().getStreet());
            addressRequest.setCity(destination.getAddress().getCity());
            addressRequest.setState(destination.getAddress().getState());
            addressRequest.setZipCode(destination.getAddress().getZipCode());
            createCustomerRequest.setAddress(addressRequest);
        }

        return createCustomerRequest;
    }
}

