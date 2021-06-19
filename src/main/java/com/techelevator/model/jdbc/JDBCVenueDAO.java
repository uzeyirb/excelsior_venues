package com.techelevator.model.jdbc;

import com.techelevator.model.Venue;
import com.techelevator.model.VenueDAO;
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
        String sql = "SELECT id, name, city_id, description FROM venue ORDER BY name";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);

        while (result.next()) {
            Venue venue = mapRowToVenue(result);
            venues.add(venue);
        }
        return venues;
    }


    @Override
    public Venue getVenue(Long venueId) {
        Venue venue = null;
        String sql = "\n" +
                "SELECT venue.id, venue.name, venue.description, city.name , state.abbreviation , ARRAY_AGG(category.name)  FROM venue\n" +
                "                JOIN city ON city.id = venue.city_id\n" +
                "                JOIN state ON city.state_abbreviation = state.abbreviation\n" +
                "                JOIN category_venue ON category_venue.venue_id = venue.id\n" +
                "                JOIN category ON category_venue.category_id = category.id\n" +
                "                GROUP BY  venue.name, venue.description, city.name, venue.id, state.abbreviation\n" +
                "                Order by venue.name";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

        if(rows.next()){
           venue = new Venue ();
           venue.setId(rows.getLong("id"));
           venue.setName(rows.getString("name"));
           venue.setDescription(rows.getString("description"));
           venue.setCityName(rows.getString("name"));
           venue.setState(rows.getString("abbreviation"));
           //venue.setCategory(rows.getObject("category.name"));
        }

        return venue;
    }

    private Venue mapRowToVenue(SqlRowSet result){

        Venue venue = new Venue();
        venue.setId(result.getLong("id"));
        venue.setName(result.getString("name"));
        venue.setCityId(result.getLong("city_id"));
        venue.setDescription(result.getString("description"));

        String sql = "SELECT category.name FROM category WHERE id IN" +
                "(SELECT category_id FROM category_venue WHERE venue_id = ?)";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, venue.getId());

        List<String> categoryList = new ArrayList<String>();
        while (rows.next()) {
            categoryList.add(result.getString("name"));

        }
        venue.setCategory(categoryList);

        return venue;

    }




}


