package com.pluralsight.dealership.controllers;

import com.pluralsight.dealership.interfaces.LeaseDAO;
import com.pluralsight.dealership.models.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Qualifier("leases")
@RequestMapping(path = "/leases")
public class LeaseController {

    @Autowired
    @Qualifier("leases-service")
    LeaseDAO leaseManager;

    @GetMapping
    public List<LeaseContract> findAllLeaseContracts() {
        return leaseManager.findAllLeaseContracts();
    }


}
