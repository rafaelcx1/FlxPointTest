package com.flxpoint.test.mappers.impl;

import com.flxpoint.test.controllers.requests.CreateAddressRequest;
import com.flxpoint.test.controllers.requests.CreateCustomerRequest;
import com.flxpoint.test.entities.Address;
import com.flxpoint.test.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateCustomerRequestToCustomerMapperTest {

    private CreateCustomerRequestToCustomerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CreateCustomerRequestToCustomerMapper();
    }

    @Test
    void testMap_WithValidCreateCustomerRequest() {
        // Given
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Test");
        request.setLastName("Last Name");
        request.setEmail("test@test.com");
        request.setPhoneNumber("123456789");

        CreateAddressRequest addressRequest = new CreateAddressRequest();
        addressRequest.setStreet("Test Street");
        addressRequest.setCity("Test City");
        addressRequest.setState("SP");
        addressRequest.setZipCode("12345");
        request.setAddress(addressRequest);

        // When
        Customer customer = mapper.map(request);

        // Then
        assertNotNull(customer);
        assertEquals("Test", customer.getFirstName());
        assertEquals("Last Name", customer.getLastName());
        assertEquals("test@test.com", customer.getEmail());
        assertEquals("123456789", customer.getPhoneNumber());
        assertNotNull(customer.getAddress());
        assertEquals("Test Street", customer.getAddress().getStreet());
        assertEquals("Test City", customer.getAddress().getCity());
        assertEquals("SP", customer.getAddress().getState());
        assertEquals("12345", customer.getAddress().getZipCode());
    }

    @Test
    void testMap_WithNullCreateCustomerRequest() {
        // When
        Customer customer = mapper.map(null);

        // Then
        assertNull(customer);
    }

    @Test
    void testMap_WithNullAddress() {
        // Given
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Test");
        request.setLastName("Last Name");
        request.setEmail("test@test.com");
        request.setPhoneNumber("123456789");
        request.setAddress(null);

        // When
        Customer customer = mapper.map(request);

        // Then
        assertNotNull(customer);
        assertEquals("Test", customer.getFirstName());
        assertEquals("Last Name", customer.getLastName());
        assertEquals("test@test.com", customer.getEmail());
        assertEquals("123456789", customer.getPhoneNumber());
        assertNull(customer.getAddress());
    }

    @Test
    void testRevert_WithValidCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Last Name");
        customer.setEmail("test@test.com");
        customer.setPhoneNumber("123456789");

        Address address = new Address();
        address.setStreet("Test Street");
        address.setCity("Test City");
        address.setState("SP");
        address.setZipCode("12345");
        customer.setAddress(address);

        // When
        CreateCustomerRequest request = mapper.revert(customer);

        // Then
        assertNotNull(request);
        assertEquals("Test", request.getFirstName());
        assertEquals("Last Name", request.getLastName());
        assertEquals("test@test.com", request.getEmail());
        assertEquals("123456789", request.getPhoneNumber());
        assertNotNull(request.getAddress());
        assertEquals("Test Street", request.getAddress().getStreet());
        assertEquals("Test City", request.getAddress().getCity());
        assertEquals("SP", request.getAddress().getState());
        assertEquals("12345", request.getAddress().getZipCode());
    }

    @Test
    void testRevert_WithNullCustomer() {
        // When
        CreateCustomerRequest request = mapper.revert(null);

        // Then
        assertNull(request);
    }

    @Test
    void testRevert_WithNullAddress() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Last Name");
        customer.setEmail("test@test.com");
        customer.setPhoneNumber("123456789");
        customer.setAddress(null);

        // When
        CreateCustomerRequest request = mapper.revert(customer);

        // Then
        assertNotNull(request);
        assertEquals("Test", request.getFirstName());
        assertEquals("Last Name", request.getLastName());
        assertEquals("test@test.com", request.getEmail());
        assertEquals("123456789", request.getPhoneNumber());
        assertNull(request.getAddress());
    }
}

