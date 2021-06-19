package com.techelevator.dao;

import com.techelevator.model.ReservationDetails;
import com.techelevator.model.Space;
import com.techelevator.model.Venue;

import java.util.List;

public interface SpaceDAO {
    /*
    list of all venues from database
     */
    List<Space> getAllSpaces();

    List<Space> getSpaceByVenue(Long venueId);

    List<ReservationDetails> getAllReservation();

}
