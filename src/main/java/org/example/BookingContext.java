package org.example;

import org.example.approaches.BookingStrategy;
import org.example.model.Seat;

import java.util.List;

public class BookingContext {
    private BookingStrategy bookingStrategy;

    public void bookSeats(final int airlineId) {
        if (bookingStrategy != null) {
            bookingStrategy.book(airlineId);
        }

        final List<Seat> seatsConfig = DbUtils.getSeatsConfig(1);
        CommonUtils.printAirlineSeats(seatsConfig);
    }

    public void setBookingStrategy(BookingStrategy bookingStrategy) {
        this.bookingStrategy = bookingStrategy;
    }
}
