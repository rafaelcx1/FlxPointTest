package com.flxpoint.test.integrations.crm;

import com.flxpoint.test.integrations.crm.config.CustomerCrmFeignConfig;
import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "crm", url = "${integrations.crm.base-url}", configuration = CustomerCrmFeignConfig.class)
public interface CustomerCrmIntegration {

    @PostMapping
    CustomerCrm create(@RequestBody CustomerCrm request);

    @PutMapping("{id}")
    CustomerCrm update(@PathVariable String id, @RequestBody CustomerCrm request);

    @DeleteMapping("{id}")
    void delete(@PathVariable Integer id);
}
