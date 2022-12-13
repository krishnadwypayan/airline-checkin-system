package org.example;

import org.example.model.Seat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    public static List<String> getNamesFromFile() {
        List<String> names = new ArrayList<>();
        final String filePath = "/Users/krishnadwypayan/Documents/Personal/Development/airline-checkin-system/airline-checkin-system/src/main/resources/passengers.txt";
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath))) {
            String name;
            while ((name = buffer.readLine()) != null) {
                names.add(name);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    public static String getSeatNo(int n) {
        char alpha = (char) ('A' + (n % 6));
        return String.valueOf((n / 6) + 1) + alpha;
    }

    public static void printAirlineSeats(List<Seat> seats) {
        char[][] config = new char[6][seats.size()/6 + 1];
        for (Seat seat : seats) {
            final int row = seat.getSeatNo().charAt(seat.getSeatNo().length() - 1) - 'A';
            final int col = Integer.parseInt(seat.getSeatNo().substring(0, seat.getSeatNo().length() - 1));
            if (seat.getPassengerId() != 0) {
                config[row][col] = 'X';
            } else {
                config[row][col] = '.';
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 1; j < config[i].length; j++) {
                System.out.print(config[i][j] + " ");
            }
            System.out.println();
            if (i == 2) {
                System.out.println();
            }
        }
    }
}
