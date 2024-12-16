package com.pluralsight.dealership.services;

import JavaHelpers.ColorCodes;
import com.pluralsight.dealership.controllers.VehicleController;
import com.pluralsight.dealership.interfaces.SalesDAO;
import com.pluralsight.dealership.models.SalesContract;
import com.pluralsight.dealership.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("sales-service")
public class SalesContractService implements SalesDAO {
    private final DataSource dataSource;

    public SalesContractService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    @Qualifier("vehicle")
    VehicleController vehicleRepo;

    @Override
    public List<SalesContract> findAllSalesContracts() {
        List<SalesContract> sales = new ArrayList<>();
        SalesContract sc;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT * FROM sales_contracts
                    """);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sc = createSalesContractObj(rs);
                sales.add(sc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sales;
    }

    @Override
    public List<SalesContract> findSalesContractById(int id) {
        List<SalesContract> sale = new ArrayList<>();
        SalesContract sc;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    SELECT * FROM sales_contracts
                    WHERE id = ?
                    """);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                sc = createSalesContractObj(rs);
                sale.add(sc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sale;
    }

    @Override
    public SalesContract saveSalesContract(SalesContract sc) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    INSERT INTO sales_contracts(vin, sale_date, customer_name, customer_email, sales_tax, recording_fee, processing_fee, down_payment, monthly_payment, financed) VALUES
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, sc.getVehicleVin());
            statement.setString(2, sc.getDate());
            statement.setString(3, sc.getCustomerName());
            statement.setString(4, sc.getCustomerEmail());
            statement.setDouble(5, sc.getSalesTax());
            statement.setDouble(6, sc.getRecordingFee());
            statement.setDouble(7, sc.getProcessingFee());
            statement.setDouble(8, sc.getDownPayment());
            statement.setDouble(9, sc.getMonthlyPayment());
            statement.setBoolean(10, sc.isFinanced());

            int rows = statement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rows);

            ResultSet genKeys = statement.getGeneratedKeys();

            if (genKeys.next()) {
                int contractId = genKeys.getInt(1);
                sc = new SalesContract(contractId, sc.getVehicleVin(), sc.getDate(), sc.getCustomerName(), sc.getCustomerEmail(), sc.getVehiclePrice(), sc.getDownPayment());
                return sc;
            }

            //Confirmation message
            System.out.println(ColorCodes.SUCCESS + ColorCodes.ITALIC + "Sales contract added into database!" + ColorCodes.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void deleteSalesContract(int id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    DELETE FROM sales_contracts WHERE id = ?
                    """);
            statement.setInt(1, id);

            //Executing and verifying DELETE query
            int rows = statement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rows);

            //Confirmation message
            System.out.println(ColorCodes.SUCCESS + ColorCodes.ITALIC + "Sales contract removed from database." + ColorCodes.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SalesContract createSalesContractObj(ResultSet rs) throws SQLException {
        Vehicle v;

        int id = rs.getInt("id");
        int vehicleVin = rs.getInt("vin");
        String saleDate = rs.getString("sale_date");
        String customerName = rs.getString("customer_name");
        String customerEmail = rs.getString("customer_email");
        double downPayment = rs.getDouble("down_payment");

        v = vehicleRepo.findVehicleByVin(vehicleVin);

        return new SalesContract(id, vehicleVin, saleDate, customerName, customerEmail, v.getPrice(), downPayment);
    }
}
