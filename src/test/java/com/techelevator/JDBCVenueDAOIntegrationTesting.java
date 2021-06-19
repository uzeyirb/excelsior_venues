package com.techelevator;

import com.techelevator.model.Venue;
import com.techelevator.model.VenueDAO;
import com.techelevator.model.jdbc.JDBCVenueDAO;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.util.List;

public class JDBCVenueDAOIntegrationTesting {

    /*
    Using this particular implementation of DataSource so that every database interaction is part of the same database
    session and hence the same database transaction

    SingleConnectionDataSource is JDBC class for connecting with database the reason why we have single connection
    is we need to hold same connection throughout the testing process.

     */
    private static SingleConnectionDataSource dataSource;
    private VenueDAO jdbcVenueDAO;
    private JdbcTemplate jdbcTemplate;


    @BeforeClass

    public static void setupDataSource() {
        //Instantiate and configure the datasource
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior_venues");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        /*
        Set autocommit = false to create the transaction scope which will prevent to save changes to db automatically
         */

        dataSource.setAutoCommit(false);
    }


    @AfterClass

    public static void destroyDataSource() {
        /*
        this method is used to disconnect and clear all changes during the test after we are done with testing
         */
        dataSource.destroy();
    }


    @Before

    public void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcVenueDAO = new JDBCVenueDAO(dataSource);

    }

    @After

    public void rollback() throws SQLException {
        /*
        this method after each individual test rolls back transaction
        reason why we have SQLException if it is unable to connect to db.
        we throw it and superclass in junit will handle it.
         */
        dataSource.getConnection().rollback();
    }


    @Test

    public void retrieve_Multiple_Venues() {

        // ARRANGE: Insert dummy data using either the DAO or the JdbcTemplate
        int expectedVenue = jdbcVenueDAO.getAllVenues().size() + 1;
        insertVenue("test", 3,"test");

        // ACT: Select the dummy data using the DAO

        int actualVenue = jdbcVenueDAO.getAllVenues().size();

        // ASSERT: Verify the data returned is the same as what was inserted.


        List<Venue> venueList = jdbcVenueDAO.getAllVenues();
        Assert.assertNotNull(venueList);
        Assert.assertEquals("There is difference between expected and actual venue number ", expectedVenue, actualVenue);


    }

    /*
    INSERT INTO venue (id, name, city_id, description) VALUES (default, 'test', 3, 'test');
     */
    private void insertVenue(String name, long city_id, String description) {

        String sql = "INSERT INTO venue (id, name, city_id, description) VALUES (default, ?, ?, ?)";
        jdbcTemplate.update(sql, name, city_id, description);

    }

}
