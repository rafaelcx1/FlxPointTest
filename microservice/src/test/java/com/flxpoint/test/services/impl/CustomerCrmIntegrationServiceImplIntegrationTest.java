package com.flxpoint.test.services.impl;

import com.flxpoint.test.integrations.crm.CustomerCrmIntegration;
import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;
import com.flxpoint.test.services.CustomerCrmIntegrationService;
import com.flxpoint.test.test_utils.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerCrmIntegrationServiceImplIntegrationTest extends AbstractIntegrationTest {

    @MockBean
    private CustomerCrmIntegration customerCrmIntegration;

    @Autowired
    private CustomerCrmIntegrationService customerCrmIntegrationService;

    @Test
    void createCustomerCrm_ShouldRetryOnFailureAndEventuallySucceed() throws Exception {
        CustomerCrm customerCrm = new CustomerCrm();
        customerCrm.setId("123");

        when(customerCrmIntegration.create(any(CustomerCrm.class)))
                .thenThrow(new RuntimeException("CRM is down"))
                .thenReturn(customerCrm);

        customerCrmIntegrationService.create(customerCrm);

        Thread.sleep(4000);

        verify(customerCrmIntegration, times(2)).create(customerCrm);
    }
}
