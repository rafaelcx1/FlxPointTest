package com.flxpoint.test.services;

import com.flxpoint.test.controllers.requests.CreateCustomerRequest;
import com.flxpoint.test.controllers.requests.UpdateCustomerRequest;
import com.flxpoint.test.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Customer getById(Integer id);

    Page<Customer> getAll(Pageable pageable);

    Customer create(CreateCustomerRequest request);

    Customer update(UpdateCustomerRequest request);

    void delete(Integer id);
}
