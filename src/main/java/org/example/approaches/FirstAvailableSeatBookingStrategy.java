package org.example.approaches;

import org.example.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstAvailableSeatBookingStrategy extends BookingStrategy {
    @Override
    protected void bookSeat(int airlineId, int passengerId) {
        try {
            final Connection connection = DbUtils.getConnection();
            // get the first available seat
            PreparedStatement statement = connection
                    .prepareStatement("SELECT id from seats WHERE airline_id=? AND passenger_id=0 ORDER BY id LIMIT 1");
            statement.setInt(1, airlineId);
            final ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                final int seatId = resultSet.getInt(1);

                // book the seat
                statement = connection.prepareStatement("UPDATE seats SET passenger_id=? WHERE id=?");
                statement.setInt(1, passengerId);
                statement.setInt(2, seatId);
                statement.executeUpdate();
                connection.commit();
            } else {
                System.out.println("Seat not booked for passenger: " + passengerId);
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
