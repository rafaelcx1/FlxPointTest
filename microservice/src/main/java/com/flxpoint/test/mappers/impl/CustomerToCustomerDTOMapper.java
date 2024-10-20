package com.flxpoint.test.mappers.impl;

import com.flxpoint.test.dtos.AddressDTO;
import com.flxpoint.test.dtos.CustomerDTO;
import com.flxpoint.test.entities.Address;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerToCustomerDTOMapper extends Mapper<Customer, CustomerDTO> {

    private final Mapper<Address, AddressDTO> addressResponseMapper;

    @Override
    public CustomerDTO map(Customer from) {
        if (from == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(from.getId());
        customerDTO.setFirstName(from.getFirstName());
        customerDTO.setLastName(from.getLastName());
        customerDTO.setEmail(from.getEmail());
        customerDTO.setPhoneNumber(from.getPhoneNumber());

        if (from.getAddress() != null) {
            customerDTO.setAddress(addressResponseMapper.map(from.getAddress()));
        }

        return customerDTO;
    }

    @Override
    public Customer revert(CustomerDTO destination) {
        if (destination == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(destination.getId());
        customer.setFirstName(destination.getFirstName());
        customer.setLastName(destination.getLastName());
        customer.setEmail(destination.getEmail());
        customer.setPhoneNumber(destination.getPhoneNumber());

        if (destination.getAddress() != null) {
            customer.setAddress(addressResponseMapper.revert(destination.getAddress()));
        }

        return customer;
    }
}

