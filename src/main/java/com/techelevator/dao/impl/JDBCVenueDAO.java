package com.techelevator.dao.impl;

import com.techelevator.dao.VenueDAO;
import com.techelevator.model.Venue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JDBCVenueDAO implements VenueDAO {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();


    public JDBCVenueDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Venue> getAllVenues() {
        List<Venue> venues = new ArrayList<Venue>();
        String sql = "SELECT id FROM venue ORDER BY name";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);

        while (result.next()) {
            Venue venue = getVenue(result.getLong("id"));
            venues.add(venue);
        }
        return venues;
    }


    @Override
    public Venue getVenue(Long venueId) {
//        String sql = "SELECT venue.id, venue.name, venue.description, city.name AS city, " +
//                "state.abbreviation, ARRAY_AGG(category.name) FROM venue " +
//                "JOIN city ON city.id = venue.city_id " +
//                "JOIN state ON city.state_abbreviation = state.abbreviation " +
//                "JOIN category_venue ON category_venue.venue_id = venue.id " +
//                "JOIN category ON category_venue.category_id = category.id " +
//                "WHERE venue.id = ? " +
//                "GROUP BY venue.name, venue.description, city.name, venue.id, state.abbreviation " +
//                "Order by venue.name ";
        String sql = "SELECT venue.id, venue.name, venue.description, city.name AS city, state.abbreviation FROM venue " +
                "JOIN city ON city.id = venue.city_id " +
                "JOIN state ON state.abbreviation = city.state_abbreviation " +
                "WHERE venue.id = ? " +
                "ORDER BY venue.name";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, venueId);
        rows.next(); // Moves cursor to the first row
        Venue venue = new Venue();
        venue.setVenueId(rows.getLong("id"));
        venue.setName(rows.getString("name"));
        venue.setDescription(rows.getString("description"));
        venue.setCityName(rows.getString("city"));
        venue.setState(rows.getString("abbreviation"));

        venue.setCategoryList(getVenueCategoryList(venueId));

        return venue;
    }

    //Why we are not using getAllVenuesSpaces??
    public List<Venue> getAllVenuesSpaces() {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT id FROM space ORDER BY name";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);

        while (result.next()) {
            Venue venue = getVenue(result.getLong("id"));
            venues.add(venue);

        }
        return venues;
    }

    private List<String> getVenueCategoryList(long venueId) {

        // Use query to select category names matching venueId
        String sql = "SELECT category.name FROM category WHERE id IN" +
                "(SELECT category_id FROM category_venue WHERE venue_id = ?)";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, venueId);

        // Set up list and add categories
        List<String> categoryList = new ArrayList<String>();
        while (rows.next()) {
            categoryList.add(rows.getString("name"));
        }

        return categoryList;
    }


}


