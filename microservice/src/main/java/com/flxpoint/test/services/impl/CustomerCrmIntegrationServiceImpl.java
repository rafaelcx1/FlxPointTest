package com.flxpoint.test.services.impl;

import com.flxpoint.test.integrations.crm.CustomerCrmIntegration;
import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;
import com.flxpoint.test.services.CustomerCrmIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerCrmIntegrationServiceImpl implements CustomerCrmIntegrationService {

    private final CustomerCrmIntegration customerCrmIntegration;

    @Async
    @Retryable(maxAttempts = 20, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void create(CustomerCrm customerCrm) {
        String customerId = customerCrm.getId();

        try {
            log.info("Creating customer (id: {}) in CRM", customerId);
            customerCrmIntegration.create(customerCrm);
            log.info("Customer (id: {}) created in CRM", customerId);
        } catch (Exception e) {
            log.error("Error when creating customer (id: {}) in CRM", customerId);
            throw e;
        }
    }

    @Async
    @Retryable(maxAttempts = 20, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void update(CustomerCrm customerCrm) {
        String customerId = customerCrm.getId();

        try {
            log.info("Updating customer (id: {}) in CRM", customerId);
            customerCrmIntegration.update(customerId, customerCrm);
            log.info("Customer (id: {}) updated in CRM", customerId);
        } catch (Exception e) {
            log.error("Error when updating customer (id: {}) in CRM", customerId);
            throw e;
        }
    }

    @Async
    @Retryable(maxAttempts = 20, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void delete(Integer customerId) {
        try {
            log.info("Deleting customer (id: {}) in CRM", customerId);
            customerCrmIntegration.delete(customerId);
            log.info("Customer (id: {}) deleted in CRM", customerId);
        } catch (Exception e) {
            log.error("Error when deleting customer (id: {}) in CRM", customerId);
            throw e;
        }
    }
}
