package com.pluralsight.dealership.services;

import JavaHelpers.ColorCodes;
import com.pluralsight.dealership.controllers.VehicleController;
import com.pluralsight.dealership.interfaces.LeaseDAO;
import com.pluralsight.dealership.models.LeaseContract;
import com.pluralsight.dealership.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("leases-service")
public class LeaseContractService implements LeaseDAO {
    private final DataSource dataSource;

    public LeaseContractService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    @Qualifier("vehicle")
    VehicleController vehicleRepo;

    @Override
    public List<LeaseContract> findAllLeaseContracts() {
        List<LeaseContract> leases = new ArrayList<>();
        LeaseContract lc;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT * FROM lease_contracts
                    """);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                lc = createLeaseContractObj(rs);
                leases.add(lc);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return leases;
    }

    @Override
    public List<LeaseContract> findLeaseContractById(int id) {
        List<LeaseContract> lease = new ArrayList<>();
        LeaseContract lc;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT * FROM lease_contracts
                    WHERE id = ?
                    """);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                lc = createLeaseContractObj(rs);
                lease.add(lc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lease;
    }

    @Override
    public LeaseContract saveLeaseContract(LeaseContract lc) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    INSERT INTO lease_contracts(vin, lease_date, customer_name, customer_email, expected_end_value, lease_fee, down_payment, monthly_payment) VALUES
                    (?, ?, ?, ?, ?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, lc.getVehicleVin());
            statement.setString(2, lc.getDate());
            statement.setString(3, lc.getCustomerName());
            statement.setString(4, lc.getCustomerEmail());
            statement.setDouble(5, lc.getExpectedEndValue());
            statement.setDouble(6, lc.getLeaseFee());
            statement.setDouble(7, lc.getDownPayment());
            statement.setDouble(8, lc.getMonthlyPayment());

            int rows = statement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rows);

            ResultSet genKeys = statement.getGeneratedKeys();

            if (genKeys.next()) {
                int contractId = genKeys.getInt(1);
                lc = new LeaseContract(contractId, lc.getVehicleVin(), lc.getDate(), lc.getCustomerName(), lc.getCustomerEmail(), lc.getVehiclePrice(), lc.getDownPayment());
                return lc;
            }

            //Confirmation message
            System.out.println(ColorCodes.SUCCESS + ColorCodes.ITALIC + "Lease contract added into database!" + ColorCodes.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void deleteLeaseContract(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    DELETE FROM lease_contracts WHERE id = ?
                    """);
            statement.setInt(1, id);

            //Executing and verifying DELETE query
            int rows = statement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rows);

            //Confirmation message
            System.out.println(ColorCodes.SUCCESS + ColorCodes.ITALIC + "Lease contract removed from database." + ColorCodes.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private LeaseContract createLeaseContractObj(ResultSet rs) throws SQLException {
        Vehicle v;

        int id = rs.getInt("id");
        int vehicleVin = rs.getInt("vin");
        String leaseDate = rs.getString("lease_date");
        String customerName = rs.getString("customer_name");
        String customerEmail = rs.getString("customer_email");
        double downPayment = rs.getDouble("down_payment");

        v = vehicleRepo.findVehicleByVin(vehicleVin);

        return new LeaseContract(id, vehicleVin, leaseDate, customerName, customerEmail, v.getPrice(), downPayment);
    }
}
