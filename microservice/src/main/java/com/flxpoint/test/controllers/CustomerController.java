package com.flxpoint.test.controllers;

import com.flxpoint.test.controllers.documentation.CustomerDocumentation;
import com.flxpoint.test.controllers.requests.CreateCustomerRequest;
import com.flxpoint.test.controllers.requests.UpdateCustomerRequest;
import com.flxpoint.test.dtos.CustomerDTO;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.mappers.Mapper;
import com.flxpoint.test.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/customers")
public class CustomerController implements CustomerDocumentation {

    private final CustomerService customerService;
    private final Mapper<Customer, CustomerDTO> mapper;

    @Override
    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Integer id) {
        return mapper.map(customerService.getById(id));
    }

    @Override
    @GetMapping
    public PagedModel<CustomerDTO> getAll(Pageable pageable) {
        return new PagedModel<>(mapper.mapAll(customerService.getAll(pageable)));
    }

    @Override
    @PostMapping
    public CustomerDTO create(@RequestBody @Valid CreateCustomerRequest request) {
        return mapper.map(customerService.create(request));
    }

    @Override
    @PutMapping
    public CustomerDTO update(@RequestBody @Valid UpdateCustomerRequest request) {
        return mapper.map(customerService.update(request));
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        customerService.delete(id);
    }
}
