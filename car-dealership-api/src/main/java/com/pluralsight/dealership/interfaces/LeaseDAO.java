package com.pluralsight.dealership.interfaces;

import com.pluralsight.dealership.models.LeaseContract;

import java.util.List;

public interface LeaseDAO {
    List<LeaseContract> findAllLeaseContracts();
    List<LeaseContract> findLeaseContractById(int id);
    void saveLeaseContract(LeaseContract c);
    void deleteLeaseContract(LeaseContract c);
}
