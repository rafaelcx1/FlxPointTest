package com.flxpoint.test.mappers.impl;

import com.flxpoint.test.entities.Address;
import com.flxpoint.test.entities.Customer;
import com.flxpoint.test.integrations.crm.dtos.CustomerCrm;
import com.flxpoint.test.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerToCustomerCrmMapper extends Mapper<Customer, CustomerCrm> {

    @Override
    public CustomerCrm map(Customer from) {
        if (from == null) {
            return null;
        }

        CustomerCrm crmCustomer = new CustomerCrm();
        crmCustomer.setId(from.getId().toString());
        crmCustomer.setFullName(from.getFirstName() + " " + from.getLastName());
        crmCustomer.setContactEmail(from.getEmail());
        crmCustomer.setPrimaryPhone(from.getPhoneNumber());

        if (from.getAddress() != null) {
            crmCustomer.setLocation(
                    from.getAddress().getStreet() + ", " +
                            from.getAddress().getCity() + ", " +
                            from.getAddress().getState() + ", " +
                            from.getAddress().getZipCode()
            );
        }

        return crmCustomer;
    }

    @Override
    public Customer revert(CustomerCrm destination) {
        if (destination == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setId(Integer.parseInt(destination.getId()));

        String[] nameParts = destination.getFullName().split(" ", 2);
        if (nameParts.length > 0) {
            customer.setFirstName(nameParts[0]);
        }
        if (nameParts.length > 1) {
            customer.setLastName(nameParts[1]);
        }

        customer.setEmail(destination.getContactEmail());
        customer.setPhoneNumber(destination.getPrimaryPhone());

        if (destination.getLocation() != null) {
            Address address = getAddress(destination);

            customer.setAddress(address);
        }

        return customer;
    }

    private static Address getAddress(CustomerCrm destination) {
        String[] locationParts = destination.getLocation().split(", ");

        Address address = new Address();

        if (locationParts.length > 0 && !locationParts[0].isEmpty()) {
            address.setStreet(locationParts[0]);
        }
        if (locationParts.length > 1 && !locationParts[1].isEmpty()) {
            address.setCity(locationParts[1]);
        }
        if (locationParts.length > 2 && !locationParts[2].isEmpty()) {
            address.setState(locationParts[2]);
        }
        if (locationParts.length > 3 && !locationParts[3].isEmpty()) {
            address.setZipCode(locationParts[3]);
        }
        return address;
    }
}
