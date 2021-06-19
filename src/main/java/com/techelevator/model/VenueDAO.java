package com.techelevator.model;

import java.util.List;

public interface VenueDAO {
    /*
    list of all venues from database
     */
    public List<Venue> getAllVenues();

    public Venue getVenue(Long venueId);

}
