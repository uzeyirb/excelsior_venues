package com.techelevator;

import javax.sql.DataSource;

import com.techelevator.service.Impl.Menu;
import com.techelevator.model.Space;
import com.techelevator.model.Venue;
import com.techelevator.dao.impl.JDBCSpaceDAO;
import com.techelevator.dao.impl.JDBCVenueDAO;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;

public class ExcelsiorCLI {



public JDBCVenueDAO jdbcVenueDAO;
public JDBCSpaceDAO jdbcSpaceDAO;

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior_venues");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        ExcelsiorCLI application = new ExcelsiorCLI(dataSource);
        application.run();
    }


	public ExcelsiorCLI(DataSource datasource) {

		jdbcVenueDAO = new JDBCVenueDAO(datasource);
        jdbcSpaceDAO = new JDBCSpaceDAO(datasource);


    }

    public void run() {
        showVenueOptions();


    }

    private void showVenueOptions() {
        List<Venue> allVenues = jdbcVenueDAO.getAllVenues();
        Menu menu = new Menu(System.in, System.out);

        String choice = menu.getChoiceFromMainMenu();
        if (choice.equals("1")) {
            int userChoice = menu.displayVenueOptions(allVenues);
            Long venueId = allVenues.get(userChoice -1).getVenueId(); // removed -1  to match the id number with venue
            Venue venue = jdbcVenueDAO.getVenue(venueId);
            menu.displayVenueDetails(venue);
            showSpaceOptionsByVenue(venue);
        }
        else if (choice.equals("R")) {
            menu.getChoiceFromMainMenu();
        }
    }

    public void showSpaceOptionsByVenue(Venue venue) {
        List<Space> allSpaces = jdbcSpaceDAO.getSpaceByVenue(venue.getVenueId());
        Menu menu = new Menu(System.in, System.out);

        String choice = menu.displaySpaceOptions(); // choice options for space
        if (choice.equals("1")) {
            System.out.println(venue.getName());
            System.out.println();
            System.out.printf("%-5s %-45s %-10s %-10s %-10s %-15s %n", "id", "Name", "Open", "Close", "Daily Rate", "Max. Occupancy");
            for(Space space: allSpaces){
                System.out.printf("%-5s %-45s %-10s %-10s %-10s %-15s %n", space.getSpaceId() , space.getName(), space.getOpenDate(),
                        space.getCloseDate(), "\\$" + space.getDailyRate(), space.getMaxOccupancy());
            }
            menu.displaySpaceOptions();

        }
        else if(choice.equals("2")){
            System.out.println("Come up with search for reservation");
        }
        else if (choice.equals("R")) {
            menu.getChoiceFromMainMenu();
        }
    }


}
