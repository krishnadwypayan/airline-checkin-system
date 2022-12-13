package org.example.approaches;

import org.example.CommonUtils;
import org.example.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class RandomBookingStrategy extends BookingStrategy {

    private static final Random RANDOM = new Random();

    @Override
    protected void bookSeat(final int airlineId, final int passengerId) {
        try {
            final Connection connection = DbUtils.getConnection();
            final PreparedStatement statement = connection.prepareStatement("UPDATE seats SET passenger_id=? WHERE seat_no=? AND airline_id=?");
            statement.setInt(1, passengerId);
            statement.setString(2, CommonUtils.getSeatNo(RANDOM.nextInt(120)));
            statement.setInt(3, airlineId);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
