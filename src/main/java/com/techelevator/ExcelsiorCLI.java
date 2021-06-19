package com.techelevator;

import com.techelevator.model.Menu;
import com.techelevator.model.Venue;
import com.techelevator.model.jdbc.JDBCVenueDAO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.List;

public class ExcelsiorCLI {



    public JDBCVenueDAO jdbcVenueDAO;


    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior_venues");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        ExcelsiorCLI application = new ExcelsiorCLI(dataSource);
        application.run();
    }



    public ExcelsiorCLI(DataSource datasource) {
        // create your DAOs here
        jdbcVenueDAO = new JDBCVenueDAO(datasource);


    }

    public void run() {
        List<Venue> allVenues = jdbcVenueDAO.getAllVenues();
        Menu menu = new Menu(System.in, System.out);

        String choice = menu.getChoiceFromMainMenu();
        if (choice.equals("1")) {
            //menu.listVenues(allVenues);

            int userChoice = menu.displayVenueOptions(allVenues);
            Long venueId = allVenues.get(userChoice -1).getId();
            Venue venue = jdbcVenueDAO.getVenue(venueId);


            menu.displayVenueDetails(venue);



        } else if (choice.equals("R")) {
            menu.getChoiceFromMainMenu();
        }


    }


}
