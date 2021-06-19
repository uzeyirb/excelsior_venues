package com.techelevator.dao.impl;

import com.techelevator.dao.SpaceDAO;
import com.techelevator.model.ReservationDetails;
import com.techelevator.model.Space;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JDBCSpaceDAO implements SpaceDAO {
    private JdbcTemplate jdbcTemplate;


    public JDBCSpaceDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    @Override
    public List<Space> getAllSpaces() {
        List<Space> spaces = new ArrayList<>();
        String sql = "SELECT  id, venue_id, name, is_accessible, open_from, open_to, cast(daily_rate as numeric) as daily_rate , max_occupancy " +
                " FROM space " +
                " WHERE venue_id = ? " +
                " Group BY venue_id, id ";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
        while(rows.next()) { // moves cursor to the first row

            Space space = new Space();
            space.setSpaceId(rows.getLong("id"));
            space.setName(rows.getString("name"));
            space.setOpenDate(rows.getString("open_from"));
            space.setCloseDate(rows.getString("open_to"));
            space.setDailyRate(rows.getBigDecimal("daily_rate"));
            space.setMaxOccupancy(rows.getLong("max_occupancy"));
            spaces.add(space);
        }
        System.out.println(spaces);
        return spaces;
    }
    @Override
    public List<Space> getSpaceByVenue(Long venueId) {
        //Use query to select space details
        List<Space> spaces = new ArrayList<>();
        String sql = "SELECT  id, venue_id, name, is_accessible, open_from, open_to, cast(daily_rate as numeric) as daily_rate , max_occupancy " +  //
                " FROM space " +  //
                " WHERE venue_id = ? " +
                " Group BY venue_id, id ";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, venueId);
        while(rows.next()) {
            // moves cursor to the first row

            Space space = new Space();
            space.setSpaceId(rows.getLong("id"));
            space.setName(rows.getString("name"));
            space.setOpenDate(rows.getString("open_from"));
            space.setCloseDate(rows.getString("open_to"));
            space.setDailyRate(rows.getBigDecimal("daily_rate"));
            space.setMaxOccupancy(rows.getLong("max_occupancy"));
            spaces.add(space);
        }
        return spaces;
    }

    @Override
    public List<ReservationDetails> getAllReservation() {
        return null;
    }


}
