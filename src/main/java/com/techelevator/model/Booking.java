package com.techelevator.model;

import java.time.LocalDate;

public class Booking {
    private long reservation_id;
    private long space_id;
    private String name;
    private LocalDate from_date;
    private LocalDate to_date;


    public long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public long getSpace_id() {
        return space_id;
    }

    public void setSpace_id(long space_id) {
        this.space_id = space_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFrom_date() {
        return from_date;
    }

    public void setFrom_date(LocalDate from_date) {
        this.from_date = from_date;
    }

    public LocalDate getTo_date() {
        return to_date;
    }

    public void setTo_date(LocalDate to_date) {
        this.to_date = to_date;
    }


}
