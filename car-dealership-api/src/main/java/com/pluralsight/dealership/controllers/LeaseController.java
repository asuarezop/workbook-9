package com.pluralsight.dealership.controllers;

import com.pluralsight.dealership.interfaces.LeaseDAO;
import com.pluralsight.dealership.models.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    List<LeaseContract> findLeaseContractById(@PathVariable Integer id) {
        return leaseManager.findLeaseContractById(id);
    }


}
