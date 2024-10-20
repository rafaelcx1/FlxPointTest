package com.flxpoint.test.integrations.crm.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CustomerCrm {

    private String id;
    private String fullName;
    private String contactEmail;
    private String primaryPhone;
    private String location;
}
