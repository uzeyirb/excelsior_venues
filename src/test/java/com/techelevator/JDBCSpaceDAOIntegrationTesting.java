package com.techelevator;

import com.techelevator.dao.SpaceDAO;
import com.techelevator.dao.VenueDAO;
import com.techelevator.dao.impl.JDBCSpaceDAO;
import com.techelevator.dao.impl.JDBCVenueDAO;
import com.techelevator.model.Space;
import com.techelevator.model.Venue;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.util.List;

public class JDBCSpaceDAOIntegrationTesting {

    /*
    Using this particular implementation of DataSource so that every database interaction is part of the same database
    session and hence the same database transaction

    SingleConnectionDataSource is JDBC class for connecting with database the reason why we have single connection
    is we need to hold same connection throughout the testing process.

     */
    private static SingleConnectionDataSource dataSource;
    private SpaceDAO jdbcSpaceDAO;
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
        jdbcSpaceDAO = new JDBCSpaceDAO(dataSource);

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

    public void retrieve_space_details(){
        // ARRANGE: Insert dummy data using either the DAO or the JdbcTemplate
      int expectedSpace = jdbcSpaceDAO.getAllSpaces().size() + 1 ;
      insertSpace("test", 111L);

        // ACT: Select the dummy data using the DAO

        int actualSpace = jdbcSpaceDAO.getAllSpaces().size();

        // ASSERT: Verify the data returned is the same as what was inserted.

        List<Space> spacesList = jdbcSpaceDAO.getAllSpaces();
        Assert.assertNotNull(spacesList);
        Assert.assertEquals("There is difference between expected and actual space number",
                expectedSpace, actualSpace);

    }

    private void insertSpace(String name, Long id){
        String sql = "INSERT INTO space (id,  name) VALUES (?, ?)";

        jdbcTemplate.update(sql, name, id);

    }

    private void AssertSpaceEqual(Space expectedSpace, Space actualSpace){
        Assert.assertEquals(" Space id did not return expected value " +  expectedSpace.getName(),
                expectedSpace.getSpaceId() , actualSpace.getSpaceId());
        Assert.assertEquals("Space name did not return expected value" + expectedSpace.getName(),
                actualSpace.getName());

    }

}
