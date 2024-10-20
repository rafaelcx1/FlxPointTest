package com.flxpoint.test.mappers.impl;

import com.flxpoint.test.dtos.AddressDTO;
import com.flxpoint.test.entities.Address;
import com.flxpoint.test.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AddressToAddressDTOMapper extends Mapper<Address, AddressDTO> {

    @Override
    public AddressDTO map(Address from) {
        if (from == null) {
            return null;
        }

        AddressDTO dto = new AddressDTO();
        dto.setId(from.getId());
        dto.setStreet(from.getStreet());
        dto.setCity(from.getCity());
        dto.setState(from.getState());
        dto.setZipCode(from.getZipCode());

        return dto;
    }

    @Override
    public Address revert(AddressDTO destination) {
        if (destination == null) {
            return null;
        }

        Address address = new Address();
        address.setId(destination.getId());
        address.setStreet(destination.getStreet());
        address.setCity(destination.getCity());
        address.setState(destination.getState());
        address.setZipCode(destination.getZipCode());

        return address;
    }
}
