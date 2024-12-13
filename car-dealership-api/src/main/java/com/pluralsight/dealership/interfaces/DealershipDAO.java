package com.pluralsight.dealership.interfaces;

import com.pluralsight.dealership.models.Dealership;

import java.util.List;

public interface DealershipDAO {
    Dealership findDealershipById(int id);
    List<Dealership> findAllDealerships();
    Dealership saveDealership(Dealership d);
    void removeDealership(int id);
}
