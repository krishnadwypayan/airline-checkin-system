package org.example;

import org.example.model.Seat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbUtils {

    public static Connection getConnection() {
        Connection connection = null;
        try {
//            connection = DriverManager.getConnection("jdbc:postgres://localhost:5432/postgres");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/airlines", "root", "password");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void selectQuery(final String table) {
        try {
            final Connection connection = getConnection();
            final Statement statement = connection.createStatement();
            final String sql = "SELECT * FROM " + table + ";";
            final ResultSet resultSet = statement.executeQuery(sql);
            final ResultSetMetaData metadata = resultSet.getMetaData();
            int columnCount = metadata.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.println(metadata.getColumnName(i) + ", ");
            }
            System.out.println();
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(resultSet.getString(i)).append(", ");
                }
                System.out.println(row);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Seat> getSeatsConfig(final int airlineId) {
        final List<Seat> seats = new ArrayList<>();
        try {
            final Connection connection = getConnection();
            final PreparedStatement statement = connection.prepareStatement("SELECT seat_no, passenger_id FROM seats WHERE airline_id=?");
            statement.setInt(1, airlineId);

            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Seat seat = new Seat();
                seat.setSeatNo(resultSet.getString(1));
                seat.setPassengerId(resultSet.getInt(2));
                seats.add(seat);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seats;
    }

    public static void insertIntoPassengers() {
        try {
            final Connection connection = getConnection();
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO passengers (id, name) VALUES (?, ?)");
            final List<String> names = CommonUtils.getNamesFromFile();
            for (int i = 0; i < names.size(); i++) {
                statement.setInt(1, i + 1);
                statement.setString(2, names.get(i));
                statement.executeUpdate();
            }
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertIntoSeats(final int airlineId) {
        try {
            final Connection connection = getConnection();
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO seats (id, seat_no, airline_id) VALUES (?, ?, ?)");
            for (int i = 0; i < 120; i++) {
                statement.setInt(1, i + 1);
                statement.setString(2, CommonUtils.getSeatNo(i));
                statement.setInt(3, airlineId);
                statement.executeUpdate();
            }
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetAirlineSeatBookingState(final int airlineId) {
        try {
            final Connection connection = getConnection();
            final PreparedStatement statement = connection.prepareStatement("UPDATE seats SET passenger_id=0 WHERE airline_id=?");
            statement.setInt(1, airlineId);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
