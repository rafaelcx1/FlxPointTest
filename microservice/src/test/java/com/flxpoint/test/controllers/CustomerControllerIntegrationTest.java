package com.flxpoint.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flxpoint.test.controllers.requests.CreateAddressRequest;
import com.flxpoint.test.controllers.requests.CreateCustomerRequest;
import com.flxpoint.test.controllers.requests.UpdateAddressRequest;
import com.flxpoint.test.controllers.requests.UpdateCustomerRequest;
import com.flxpoint.test.entities.Address;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.integrations.crm.CustomerCrmIntegration;
import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;
import com.flxpoint.test.repositories.CustomerRepository;
import com.flxpoint.test.test_utils.AbstractIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerCrmIntegration customerCrmIntegration;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void afterEach() {
        customerRepository.deleteAll();
    }

    @Test
    void createCustomer_ShouldCreateCustomerAndCallCrmIntegration() throws Exception {
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

        when(customerCrmIntegration.create(any(CustomerCrm.class))).thenReturn(new CustomerCrm());

        // When/Then
        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("Last Name"))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"))
                .andExpect(jsonPath("$.address.street").value("Test Street"))
                .andExpect(jsonPath("$.address.city").value("Test City"))
                .andExpect(jsonPath("$.address.state").value("SP"))
                .andExpect(jsonPath("$.address.zipCode").value("12345"));

        verify(customerCrmIntegration, times(1)).create(any(CustomerCrm.class));
    }

    @Test
    void updateCustomer_ShouldUpdateCustomerAndCallCrmIntegration() throws Exception {
        // Given
        Customer customer = mockCustomer();
        customer.setFirstName("Old Name");
        customerRepository.save(customer);

        UpdateCustomerRequest updateRequest = getUpdateCustomerRequest(customer);
        updateRequest.setFirstName("New Name");

        when(customerCrmIntegration.update(anyString(), any(CustomerCrm.class))).thenReturn(new CustomerCrm());

        // When
        mockMvc.perform(put("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(updateRequest.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.phoneNumber").value(customer.getPhoneNumber()))
                .andExpect(jsonPath("$.address.street").value(customer.getAddress().getStreet()))
                .andExpect(jsonPath("$.address.city").value(customer.getAddress().getCity()))
                .andExpect(jsonPath("$.address.state").value(customer.getAddress().getState()))
                .andExpect(jsonPath("$.address.zipCode").value(customer.getAddress().getZipCode()));

        // Then
        verify(customerCrmIntegration, times(1)).update(anyString(), any(CustomerCrm.class));

        Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
        assertTrue(updatedCustomer.isPresent());
        assertEquals(updateRequest.getFirstName(), updatedCustomer.get().getFirstName());
    }

    @Test
    void deleteCustomer_ShouldRemoveCustomerAndCallCrmIntegration() throws Exception {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("To be deleted");

        customerRepository.save(customer);

        doNothing().when(customerCrmIntegration).delete(customer.getId());

        // When
        mockMvc.perform(delete("/v1/customers/" + customer.getId()))
                .andExpect(status().isOk());

        // Then
        verify(customerCrmIntegration, times(1)).delete(customer.getId());
        assertFalse(customerRepository.findById(customer.getId()).isPresent());
    }


    private Customer mockCustomer() {
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

        return customer;
    }

    private UpdateCustomerRequest getUpdateCustomerRequest(Customer customer) {
        UpdateCustomerRequest updateRequest = new UpdateCustomerRequest();
        updateRequest.setId(customer.getId());
        updateRequest.setFirstName(customer.getFirstName());
        updateRequest.setLastName(customer.getLastName());
        updateRequest.setEmail(customer.getEmail());
        updateRequest.setPhoneNumber(customer.getPhoneNumber());
        updateRequest.setAddress(new UpdateAddressRequest());
        updateRequest.getAddress().setId(customer.getAddress().getId());
        updateRequest.getAddress().setCity(customer.getAddress().getCity());
        updateRequest.getAddress().setState(customer.getAddress().getState());
        updateRequest.getAddress().setStreet(customer.getAddress().getStreet());
        updateRequest.getAddress().setZipCode(customer.getAddress().getZipCode());

        return updateRequest;
    }
}
