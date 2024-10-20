package com.flxpoint.test.mappers.impl;

import com.flxpoint.test.entities.Address;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerToCustomerCrmMapperTest {

    private CustomerToCustomerCrmMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CustomerToCustomerCrmMapper();
    }

    @Test
    void testMap_WithValidCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setId(1);
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
        CustomerCrm crmCustomer = mapper.map(customer);

        // Then
        assertNotNull(crmCustomer);
        assertEquals("1", crmCustomer.getId());
        assertEquals("Test Last Name", crmCustomer.getFullName());
        assertEquals("test@test.com", crmCustomer.getContactEmail());
        assertEquals("123456789", crmCustomer.getPrimaryPhone());
        assertEquals("Test Street, Test City, SP, 12345", crmCustomer.getLocation());
    }

    @Test
    void testMap_WithNullCustomer() {
        // When
        CustomerCrm crmCustomer = mapper.map(null);

        // Then
        assertNull(crmCustomer);
    }

    @Test
    void testMap_WithNullAddress() {
        // Given
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Test");
        customer.setLastName("Last Name");
        customer.setEmail("test@test.com");
        customer.setPhoneNumber("123456789");
        customer.setAddress(null);

        // When
        CustomerCrm crmCustomer = mapper.map(customer);

        // Then
        assertNotNull(crmCustomer);
        assertEquals("1", crmCustomer.getId());
        assertEquals("Test Last Name", crmCustomer.getFullName());
        assertEquals("test@test.com", crmCustomer.getContactEmail());
        assertEquals("123456789", crmCustomer.getPrimaryPhone());
        assertNull(crmCustomer.getLocation());
    }

    @Test
    void testRevert_WithValidCustomerCrm() {
        // Given
        CustomerCrm crmCustomer = new CustomerCrm();
        crmCustomer.setId("1");
        crmCustomer.setFullName("Test Last Name");
        crmCustomer.setContactEmail("test@test.com");
        crmCustomer.setPrimaryPhone("123456789");
        crmCustomer.setLocation("Test Street, Test City, SP, 12345");

        // When
        Customer customer = mapper.revert(crmCustomer);

        // Then
        assertNotNull(customer);
        assertEquals(1, customer.getId());
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
    void testRevert_WithNullCustomerCrm() {
        // When
        Customer customer = mapper.revert(null);

        // Then
        assertNull(customer);
    }

    @Test
    void testRevert_WithNullLocation() {
        // Given
        CustomerCrm crmCustomer = new CustomerCrm();
        crmCustomer.setId("1");
        crmCustomer.setFullName("Test Last Name");
        crmCustomer.setContactEmail("test@test.com");
        crmCustomer.setPrimaryPhone("123456789");
        crmCustomer.setLocation(null);

        // When
        Customer customer = mapper.revert(crmCustomer);

        // Then
        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertEquals("Test", customer.getFirstName());
        assertEquals("Last Name", customer.getLastName());
        assertEquals("test@test.com", customer.getEmail());
        assertEquals("123456789", customer.getPhoneNumber());
        assertNull(customer.getAddress());
    }

    @Test
    void testRevert_WithPartialLocation() {
        // Given
        CustomerCrm crmCustomer = new CustomerCrm();
        crmCustomer.setId("1");
        crmCustomer.setFullName("Test Last Name");
        crmCustomer.setContactEmail("test@test.com");
        crmCustomer.setPrimaryPhone("123456789");
        crmCustomer.setLocation("Test Street, Test City, SP");

        // When
        Customer customer = mapper.revert(crmCustomer);

        // Then
        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertEquals("Test", customer.getFirstName());
        assertEquals("Last Name", customer.getLastName());
        assertEquals("test@test.com", customer.getEmail());
        assertEquals("123456789", customer.getPhoneNumber());
        assertNotNull(customer.getAddress());
        assertEquals("Test Street", customer.getAddress().getStreet());
        assertEquals("Test City", customer.getAddress().getCity());
        assertEquals("SP", customer.getAddress().getState());
        assertNull(customer.getAddress().getZipCode());
    }
}
