# Airline Check-In System

- 120 passengers have booked tickets for a flight. 
- There are 120 seats on the flight that the passengers will try to occupy.

Assume that all the 120 passengers arrive at the airport at the same instant and
reach the kiosk to occupy the seats.

We will see how the 120 seats get occupied by going over different approaches of booking seats.


## Database Design

passengers

| id  | name |
|-----|------|

airlines

| id  | name |
|-----|------|

seats

| id  | seat_no | airline_id | passenger_id |
|-----|---------|------------|--------------|

## Approaches
- Approach 1 
  - Our system picks a seat at random for every passenger. 
- Approach 2
  - Our system pick the first available seat for every passenger.
- Approach 3 
  - Our system pick the first available seat for every passenger 
    - by applying an exclusive lock.
- Approach 4 
  - Our system pick the first available seat for every passenger 
    - by applying an exclusive lock, and
    - by skipping locked rows.
- Approach 5
  - Our system pick the first available seat for every passenger
    - by applying a shared lock
- Approach 6
  - Our system pick the first available seat for every passenger
    - by applying a shared lock, and
    - by skipping locked rows.
