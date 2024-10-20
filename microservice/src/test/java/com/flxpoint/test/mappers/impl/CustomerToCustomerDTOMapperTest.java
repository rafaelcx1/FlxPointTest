package com.flxpoint.test.mappers.impl;

import com.flxpoint.test.dtos.AddressDTO;
import com.flxpoint.test.dtos.CustomerDTO;
import com.flxpoint.test.entities.Address;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.mappers.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerToCustomerDTOMapperTest {

    @Mock
    private Mapper<Address, AddressDTO> addressResponseMapper;

    @InjectMocks
    private CustomerToCustomerDTOMapper mapper;


    @Test
    void testMap_WithValidCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("First");
        customer.setLastName("Last");
        customer.setEmail("test@test.com");
        customer.setPhoneNumber("123456789");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setCity("Test City");
        address.setState("SP");
        address.setZipCode("12345");
        customer.setAddress(address);

        AddressDTO addressDTO = new AddressDTO();
        when(addressResponseMapper.map(address)).thenReturn(addressDTO);

        // When
        CustomerDTO customerDTO = mapper.map(customer);

        // Then
        assertNotNull(customerDTO);
        assertEquals(1, customerDTO.getId());
        assertEquals("First", customerDTO.getFirstName());
        assertEquals("Last", customerDTO.getLastName());
        assertEquals("test@test.com", customerDTO.getEmail());
        assertEquals("123456789", customerDTO.getPhoneNumber());
        assertNotNull(customerDTO.getAddress());
        verify(addressResponseMapper, times(1)).map(address);
    }

    @Test
    void testMap_WithNullCustomer() {
        // When
        CustomerDTO customerDTO = mapper.map(null);

        // Then
        assertNull(customerDTO);
    }

    @Test
    void testMap_WithNullAddress() {
        // Given
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("First");
        customer.setLastName("Last");
        customer.setEmail("test@test.com");
        customer.setPhoneNumber("123456789");
        customer.setAddress(null);

        // When
        CustomerDTO customerDTO = mapper.map(customer);

        // Then
        assertNotNull(customerDTO);
        assertEquals(1, customerDTO.getId());
        assertEquals("First", customerDTO.getFirstName());
        assertEquals("Last", customerDTO.getLastName());
        assertEquals("test@test.com", customerDTO.getEmail());
        assertEquals("123456789", customerDTO.getPhoneNumber());
        assertNull(customerDTO.getAddress());
        verify(addressResponseMapper, never()).map(any(Address.class));
    }

    @Test
    void testRevert_WithValidCustomerDTO() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1);
        customerDTO.setFirstName("First");
        customerDTO.setLastName("Last");
        customerDTO.setEmail("test@test.com");
        customerDTO.setPhoneNumber("123456789");

        AddressDTO addressDTO = new AddressDTO();
        customerDTO.setAddress(addressDTO);

        Address address = new Address();
        when(addressResponseMapper.revert(addressDTO)).thenReturn(address);

        // When
        Customer customer = mapper.revert(customerDTO);

        // Then
        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertEquals("First", customer.getFirstName());
        assertEquals("Last", customer.getLastName());
        assertEquals("test@test.com", customer.getEmail());
        assertEquals("123456789", customer.getPhoneNumber());
        assertNotNull(customer.getAddress());
        verify(addressResponseMapper, times(1)).revert(addressDTO);
    }

    @Test
    void testRevert_WithNullCustomerDTO() {
        // When
        Customer customer = mapper.revert(null);

        // Then
        assertNull(customer);
    }

    @Test
    void testRevert_WithNullAddress() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1);
        customerDTO.setFirstName("First");
        customerDTO.setLastName("Last");
        customerDTO.setEmail("test@test.com");
        customerDTO.setPhoneNumber("123456789");
        customerDTO.setAddress(null);

        // When
        Customer customer = mapper.revert(customerDTO);

        // Then
        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertEquals("First", customer.getFirstName());
        assertEquals("Last", customer.getLastName());
        assertEquals("test@test.com", customer.getEmail());
        assertEquals("123456789", customer.getPhoneNumber());
        assertNull(customer.getAddress());
        verify(addressResponseMapper, never()).revert(any(AddressDTO.class));
    }
}

