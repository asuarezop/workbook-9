package com.pluralsight.dealership.services;

import JavaHelpers.ColorCodes;
import com.pluralsight.dealership.interfaces.DealershipDAO;
import com.pluralsight.dealership.models.Dealership;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("dealership-service")
public class DealershipService implements DealershipDAO {
    private final DataSource dataSource;

    public DealershipService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Retrieving all dealerships from database
    @Override
    public List<Dealership> findAllDealerships() {
        List<Dealership> dealerships = new ArrayList<>();
        Dealership d;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM dealerships");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                d = createDealershipObj(rs);
                dealerships.add(d);
            }

            return dealerships;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dealership saveDealership(Dealership d) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    INSERT INTO dealerships(name, address, phone) VALUES
                    (?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, d.getName());
            statement.setString(2, d.getAddress());
            statement.setString(3, d.getPhone());

            //Executing and verifying INSERT query
            int rows = statement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rows);

            ResultSet genKeys = statement.getGeneratedKeys();

            if (genKeys.next()) {
                int dealershipId = genKeys.getInt(1);
                d = new Dealership(dealershipId, d.getName(), d.getAddress(), d.getPhone());

                return d;
            }

            //Printing out dealership to console (testing to check if dealership obj worked)
            UserInterface.printDealershipHeader();
            System.out.println(d);

            //Confirmation message
            System.out.println(ColorCodes.SUCCESS + ColorCodes.ITALIC + "Dealership was added to database!" + ColorCodes.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void removeDealership(Dealership d) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("""
                    DELETE FROM dealerships WHERE id = ?
                    """);
            statement.setInt(1, d.getId());

            //Executing and verifying DELETE query
            int rows = statement.executeUpdate();
            System.out.printf("Rows updated: %d\n", rows);

            //Confirmation message
            System.out.println(ColorCodes.SUCCESS + ColorCodes.ITALIC + "Dealership removed from database." + ColorCodes.RESET);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Dealership findDealershipById(int id) {
        Dealership d;

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM dealerships WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                d = createDealershipObj(rs);
                return d;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private Dealership createDealershipObj(ResultSet rs) throws SQLException {
        int dealershipId = rs.getInt("id");
        String dealershipName = rs.getString("name");
        String dealershipAddress = rs.getString("address");
        String dealershipPhoneNum = rs.getString("phone");

        return new Dealership(dealershipId, dealershipName, dealershipAddress, dealershipPhoneNum);
    }
}

