package com.flxpoint.test.services.impl;

import com.flxpoint.test.controllers.requests.CreateAddressRequest;
import com.flxpoint.test.controllers.requests.CreateCustomerRequest;
import com.flxpoint.test.controllers.requests.UpdateAddressRequest;
import com.flxpoint.test.controllers.requests.UpdateCustomerRequest;
import com.flxpoint.test.entities.Address;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;
import com.flxpoint.test.mappers.Mapper;
import com.flxpoint.test.mappers.impl.CreateCustomerRequestToCustomerMapper;
import com.flxpoint.test.mappers.impl.CustomerToCustomerCrmMapper;
import com.flxpoint.test.repositories.CustomerRepository;
import com.flxpoint.test.services.CustomerCrmIntegrationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    private CustomerServiceImpl test;
    private CustomerRepository customerRepository;
    private CustomerCrmIntegrationService customerCrmIntegrationService;
    private Mapper<Customer, CustomerCrm> customerCustomerCrmMapper;
    private Mapper<CreateCustomerRequest, Customer> createCustomerRequestMapper;


    @BeforeEach
    public void setUp() {
        this.customerRepository = mock(CustomerRepository.class);
        this.customerCrmIntegrationService = mock(CustomerCrmIntegrationService.class);
        this.customerCustomerCrmMapper = mock(CustomerToCustomerCrmMapper.class);
        this.createCustomerRequestMapper = mock(CreateCustomerRequestToCustomerMapper.class);

        this.test = new CustomerServiceImpl(
                customerRepository,
                customerCrmIntegrationService,
                customerCustomerCrmMapper,
                createCustomerRequestMapper
        );
    }


    @Test
    void testGetById_CustomerExists() {
        // Given
        Integer customerId = 1;
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // When
        Customer result = test.getById(customerId);

        // Then
        assertNotNull(result);
        assertEquals(customer, result);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetById_CustomerNotFound() {
        // Given
        Integer customerId = 1;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            test.getById(customerId);
        });

        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetAll() {
        // Given
        Pageable pageable = mock(Pageable.class);
        Page<Customer> customers = mock(Page.class);
        when(customerRepository.findAll(pageable)).thenReturn(customers);

        // When
        Page<Customer> result = test.getAll(pageable);

        // Then
        assertNotNull(result);
        assertEquals(customers, result);
        verify(customerRepository, times(1)).findAll(pageable);
    }

    @Test
    void testCreate() {
        // Given
        CreateCustomerRequest request = new CreateCustomerRequest();
        Customer customer = new Customer();
        CustomerCrm customerCrm = new CustomerCrm();

        when(createCustomerRequestMapper.map(request)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerCustomerCrmMapper.map(customer)).thenReturn(customerCrm);

        // When
        Customer result = test.create(request);

        // Then
        assertNotNull(result);
        verify(customerRepository, times(1)).save(customer);
        verify(customerCrmIntegrationService, times(1)).create(customerCrm);
    }

    @Test
    void testUpdate_CustomerExists() {
        // Given
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setId(1);
        request.setAddress(new UpdateAddressRequest());

        Customer existingCustomer = new Customer();
        existingCustomer.setAddress(new Address());

        CustomerCrm customerCrm = new CustomerCrm();

        when(customerRepository.findById(request.getId())).thenReturn(Optional.of(existingCustomer));
        when(customerCustomerCrmMapper.map(existingCustomer)).thenReturn(customerCrm);

        // When
        Customer result = test.update(request);

        // Then
        assertNotNull(result);
        verify(customerRepository, times(1)).findById(request.getId());
        verify(customerRepository, times(1)).save(existingCustomer);
        verify(customerCrmIntegrationService, times(1)).update(customerCrm);
    }

    @Test
    void testUpdate_CustomerNotFound() {
        // Given
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setId(1);

        when(customerRepository.findById(request.getId())).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            test.update(request);
        });

        assertEquals("Customer not found with id: 1", exception.getMessage());
        verify(customerRepository, times(1)).findById(request.getId());
    }

    @Test
    void testDelete_ExistentCustomer() {
        // Given
        Integer customerId = 1;
        when(customerRepository.existsById(customerId)).thenReturn(true);

        // When
        test.delete(customerId);

        // Then
        verify(customerRepository, times(1)).existsById(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
        verify(customerCrmIntegrationService, times(1)).delete(customerId);
    }

    @Test
    void testDelete_NonExistentCustomer() {
        // Given
        Integer customerId = 1;
        when(customerRepository.existsById(customerId)).thenReturn(false);

        // When
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            test.delete(customerId);
        });

        // Then
        assertEquals("Customer not found with id: 1", exception.getMessage());
        verify(customerRepository, times(1)).existsById(customerId);
        verify(customerRepository, times(0)).deleteById(customerId);
        verify(customerCrmIntegrationService, times(0)).delete(customerId);
    }
}

