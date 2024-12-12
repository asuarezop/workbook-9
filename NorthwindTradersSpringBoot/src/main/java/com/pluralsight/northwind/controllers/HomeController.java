package com.pluralsight.northwind.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(
            @RequestParam(defaultValue = "World") String country
    ) {
        return "Hello " + country + "!";
    }
}
