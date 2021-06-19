package com.techelevator.dao;

import com.techelevator.model.Venue;

import java.util.List;

public interface VenueDAO {
    /*
    list of all venues from database
     */
    List<Venue> getAllVenues();

    Venue getVenue(Long venueId);

}
