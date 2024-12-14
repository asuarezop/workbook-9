package com.pluralsight.dealership.controllers;

import com.pluralsight.dealership.interfaces.SalesDAO;
import com.pluralsight.dealership.models.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Qualifier("sales")
@RequestMapping(path = "/sales")
public class SalesController {

    @Autowired
    @Qualifier("sales-service")
    SalesDAO salesManager;

    @GetMapping
    public List<SalesContract> findAllSalesContracts() {
        return salesManager.findAllSalesContracts();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public SalesContract saveSalesContract(@RequestBody SalesContract c) {
        return salesManager.saveSalesContract(c);
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSalesContract(@PathVariable Integer id) {
        salesManager.deleteSalesContract(id);
    }
}
