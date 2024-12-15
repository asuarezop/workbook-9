package com.pluralsight.dealership.controllers;

import com.pluralsight.dealership.interfaces.LeaseDAO;
import com.pluralsight.dealership.models.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
    public List<LeaseContract> findLeaseContractById(@PathVariable Integer id) {
        return leaseManager.findLeaseContractById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public LeaseContract saveLeaseContract(@RequestBody LeaseContract c) {
        return leaseManager.saveLeaseContract(c);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteLeaseContract(@PathVariable Integer id) {
        leaseManager.deleteLeaseContract(id);
    }


}
