package com.flxpoint.test.mappers.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.flxpoint.test.dtos.AddressDTO;
import com.flxpoint.test.entities.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressToAddressDTOMapperTest {

    private AddressToAddressDTOMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AddressToAddressDTOMapper();
    }

    @Test
    void testMap_WithValidAddress() {
        // Given
        Address address = new Address();
        address.setId(1);
        address.setStreet("Test street");
        address.setCity("Test City");
        address.setState("SP");
        address.setZipCode("12345");

        // When
        AddressDTO dto = mapper.map(address);

        // Then
        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Test street", dto.getStreet());
        assertEquals("Test City", dto.getCity());
        assertEquals("SP", dto.getState());
        assertEquals("12345", dto.getZipCode());
    }

    @Test
    void testMap_WithNullAddress() {
        // When
        AddressDTO dto = mapper.map(null);

        // Then
        assertNull(dto);
    }

    @Test
    void testRevert_WithValidAddressDTO() {
        // Given
        AddressDTO dto = new AddressDTO();
        dto.setId(1);
        dto.setStreet("Test Street");
        dto.setCity("Test City");
        dto.setState("SP");
        dto.setZipCode("12345");

        // When
        Address address = mapper.revert(dto);

        // Then
        assertNotNull(address);
        assertEquals(1, address.getId());
        assertEquals("Test Street", address.getStreet());
        assertEquals("Test City", address.getCity());
        assertEquals("SP", address.getState());
        assertEquals("12345", address.getZipCode());
    }

    @Test
    void testRevert_WithNullAddressDTO() {
        // When
        Address address = mapper.revert(null);

        // Then
        assertNull(address);
    }
}

