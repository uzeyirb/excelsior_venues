package com.techelevator.dao;

import com.techelevator.model.Booking;
import com.techelevator.model.Space;

import java.time.LocalDate;
import java.util.List;

public interface BookingDAO {

   List<Booking> getAllBookings();
   Booking getBookingBySpaceId(Long spaceId);
   List<Space> getSpaceByToFromDate(Long spaceId, LocalDate fromDate, LocalDate toDate);
   Booking createBooking(Booking newBooking);
   void saveBooking(Booking saveBooking);

}
