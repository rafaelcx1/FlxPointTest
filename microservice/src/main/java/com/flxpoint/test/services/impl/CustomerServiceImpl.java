package com.flxpoint.test.services.impl;

import com.flxpoint.test.controllers.requests.CreateCustomerRequest;
import com.flxpoint.test.controllers.requests.UpdateCustomerRequest;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;
import com.flxpoint.test.mappers.Mapper;
import com.flxpoint.test.repositories.CustomerRepository;
import com.flxpoint.test.services.CustomerCrmIntegrationService;
import com.flxpoint.test.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerCrmIntegrationService customerCrmIntegrationService;
    private final Mapper<Customer, CustomerCrm> customerCustomerCrmMapper;
    private final Mapper<CreateCustomerRequest, Customer> mapper;


    @Override
    public Customer getById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Override
    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer create(CreateCustomerRequest request) {
        Customer entity = mapper.map(request);

        customerRepository.save(entity);

        CustomerCrm map = customerCustomerCrmMapper.map(entity);

        customerCrmIntegrationService.create(map);

        return entity;
    }

    @Override
    public Customer update(UpdateCustomerRequest request) {
        Customer existingCustomer = customerRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + request.getId()));

        BeanUtils.copyProperties(request, existingCustomer, "id", "address");
        BeanUtils.copyProperties(request.getAddress(), existingCustomer.getAddress(), "id");

        customerRepository.save(existingCustomer);

        customerCrmIntegrationService.update(customerCustomerCrmMapper.map(existingCustomer));

        return existingCustomer;
    }

    @Override
    public void delete(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }

        customerCrmIntegrationService.delete(id);
        customerRepository.deleteById(id);
    }
}
