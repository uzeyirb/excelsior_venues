package com.techelevator.dao.impl;

import com.techelevator.dao.BookingDAO;
import com.techelevator.model.Booking;
import com.techelevator.model.Space;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCBookingDAO implements BookingDAO {
    private JdbcTemplate jdbcTemplate;

    public JDBCBookingDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT reservation_id, number_of_attendees, start_date, end_date, reserved_for,  space.name FROM reservation\n" +
                "JOIN space ON reservation.space_id = space.id\n" +
                "ORDER BY reservation.reservation_id";
        SqlRowSet rows =jdbcTemplate.queryForRowSet(sql);
       while(rows.next()){
           bookings.add(mapRowToBooking(rows));
       }


        return bookings;
    }

    @Override
    public Booking getBookingBySpaceId(Long spaceId) {
        return null;
    }

    @Override
    public List<Space> getSpaceByToFromDate(Long spaceId, LocalDate fromDate, LocalDate toDate) {
        List<Space> searchById = new ArrayList<>();
        String sql = "SELECT reservation_id,  space.name, reserved_for, end_date  ,start_date , number_of_attendees  FROM reservation" +
                "JOIN space ON reservation.space_id = space.id" +
                "ORDER BY reservation.reservation_id";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, spaceId, fromDate, toDate.plusDays(1)); // what is deal with +1
        while (result.next()){
            searchById.add(mapRowToSpace(result));
        }
        return searchById;
    }

    @Override
    public Booking createBooking(Booking newBooking) {
        return null;
    }

    @Override
    public void saveBooking(Booking saveBooking) {

    }

    private Booking mapRowToBooking(SqlRowSet rows){
        Booking booking = new Booking();
        booking.setReservation_id(rows.getLong("reservation_id"));
        booking.setSpace_id(rows.getLong("space_id"));
        booking.setName(rows.getString("name"));
        if(rows.getDate("start_date") != null) {
            booking.setFrom_date(rows.getDate("start_date").toLocalDate());
        }
        if(rows.getDate("end_date") != null){
            booking.setTo_date(rows.getDate("end_date").toLocalDate());
        }

        return booking;
    }

    private Space mapRowToSpace(SqlRowSet rows){
        Space space = new Space();
        space.setSpaceId(rows.getLong("id"));
        space.setName(rows.getString("name"));
        space.setAccessible(rows.getBoolean("is_accessible"));
        space.setOpenDate(rows.getString("open_from"));
        space.setCloseDate(rows.getString("open_to"));
        space.setDailyRate(rows.getBigDecimal("daily_rate"));
        space.setMaxOccupancy(rows.getLong("max_occupancy"));


        return space;
    }
}
