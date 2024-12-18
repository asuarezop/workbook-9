package com.pluralsight.dealership.interfaces;

import com.pluralsight.dealership.models.SalesContract;

import java.util.List;

public interface SalesDAO {
    List<SalesContract> findAllSalesContracts();
    List<SalesContract> findSalesContractById(int id);
    SalesContract saveSalesContract(SalesContract c);
    void deleteSalesContract(int id);
}
