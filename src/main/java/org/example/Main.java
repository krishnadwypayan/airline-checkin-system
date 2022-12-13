package org.example;

import org.example.approaches.FirstAvailableSeatBookingStrategy;
import org.example.approaches.FirstAvailableSeatExclusiveLockBookingStrategy;
import org.example.approaches.FirstAvailableSeatExclusiveSkipLockBookingStrategy;
import org.example.approaches.RandomBookingStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        DbUtils.insertIntoPassengers();
//        DbUtils.insertIntoSeats(1);

        final BookingContext bookingContext = new BookingContext();
        final Scanner sc = new Scanner(System.in);

        System.out.println("\n\nWelcome to Airline Check-In System!");
        while (true) {
            System.out.println("\n\nChoose a check-in approach you would like to test...");
            System.out.println("[1] Random Check-In");
            System.out.println("[2] First Available Seat Check-In");
            System.out.println("[3] First Available Seat Exclusive Lock Check-In");
            System.out.println("[4] First Available Seat Exclusive Lock with Skip Locked Check-In");
            System.out.println("[10] Reset DB state");

            final int option = sc.nextInt();

            System.out.println("Testing for airline: (name = AIRINDIA-101, id = 1) ...");

            switch (option) {
                case 1:
                    bookingContext.setBookingStrategy(new RandomBookingStrategy());
                    bookingContext.bookSeats(1);
                    break;
                case 2:
                    bookingContext.setBookingStrategy(new FirstAvailableSeatBookingStrategy());
                    bookingContext.bookSeats(1);
                    break;
                case 3:
                    bookingContext.setBookingStrategy(new FirstAvailableSeatExclusiveLockBookingStrategy());
                    bookingContext.bookSeats(1);
                    break;
                case 4:
                    bookingContext.setBookingStrategy(new FirstAvailableSeatExclusiveSkipLockBookingStrategy());
                    bookingContext.bookSeats(1);
                    break;
                case 10:
                    DbUtils.resetAirlineSeatBookingState(1);
                    break;
                default:
                    DbUtils.resetAirlineSeatBookingState(1);
                    System.out.println("Enter a valid input");
            }
        }
    }
}