package com.flxpoint.test.controllers.documentation;

import com.flxpoint.test.controllers.requests.CreateCustomerRequest;
import com.flxpoint.test.controllers.requests.UpdateCustomerRequest;
import com.flxpoint.test.dtos.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

@Tag(name = "Customer")
public interface CustomerDocumentation {

    @Operation(summary = "Get Customer by Id")
    CustomerDTO getById(Integer id);

    @Operation(summary = "Get All Customers")
    PagedModel<CustomerDTO> getAll(Pageable pageable);

    @Operation(summary = "Create Customer")
    CustomerDTO create(CreateCustomerRequest request);

    @Operation(summary = "Update Customer")
    CustomerDTO update(UpdateCustomerRequest request);

    @Operation(summary = "Delete Customer by Id")
    void delete(Integer id);
}
