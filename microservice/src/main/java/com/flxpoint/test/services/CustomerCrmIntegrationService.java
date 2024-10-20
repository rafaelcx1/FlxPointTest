package com.flxpoint.test.services;

import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;

public interface CustomerCrmIntegrationService {
    void create(CustomerCrm customer);

    void update(CustomerCrm customer);

    void delete(Integer id);
}
