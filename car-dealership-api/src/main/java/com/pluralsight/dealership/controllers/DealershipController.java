package com.pluralsight.dealership.controllers;

import com.pluralsight.dealership.interfaces.DealershipDAO;
import com.pluralsight.dealership.models.Dealership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Qualifier("dealership")
@RequestMapping(path = "/dealership")
public class DealershipController {

    @Autowired
    @Qualifier("dealership-service")
    DealershipDAO dealershipManager;

    @RequestMapping(method = RequestMethod.GET)
    public List<Dealership> findAllDealerships() {
        return dealershipManager.findAllDealerships();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Dealership findDealershipById(@PathVariable Integer id) {
        return dealershipManager.findDealershipById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Dealership saveDealership(@RequestBody Dealership dealership) {
       return dealershipManager.saveDealership(dealership);
    }

    public void removeDealership(Dealership dealership) {
        dealershipManager.removeDealership(dealership);
    }
}
