package com.techelevator.service.Impl;

import com.techelevator.dao.impl.JDBCSpaceDAO;
import com.techelevator.dao.impl.JDBCVenueDAO;
import com.techelevator.model.Space;
import com.techelevator.model.Venue;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;
    public JDBCVenueDAO jdbcVenueDAO;
    public JDBCSpaceDAO jdbcSpaceDAO;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    /*
    Lists all venues in alphabetical order from the JDBCVenueDAO
     */
    public void listVenues(List<Venue> venues) {
        System.out.println();
        if (venues.size() > 0) {
            for (Venue venue : venues) {
                System.out.printf("%-35s %n", venue.getName());
            }
        } else {
            System.out.println("\n*** No results ***");
        }
    }


    public String getChoiceFromMainMenu() {
        String choice = null;
        System.out.println("What would you like to do?");
        while (true) {
            System.out.println("\t1) List Venues");
            System.out.println("\tQ) Quit");
            choice = in.nextLine();
            if (choice.equals("1")) {
                break;
            } else if (choice.equalsIgnoreCase("Q")) {
                System.out.println("Good bye");
                break;
            } else {
                System.out.println("Invalid selection");
            }

        }
        return choice;
    }

    public String displaySpaceOptions() {
        String choice = null;
        System.out.println("What would you like to do next?");
        while (true) {
            System.out.println("\t1) View Spaces");
            System.out.println("\t2) Search for Reservation");
            System.out.println("\tR) Return to Previous Screen");
            choice = in.nextLine();
            if (choice.equals("1")) {
                return choice;
            } else if(choice.equals("2")){

            }
            else if (choice.equalsIgnoreCase("R")) {
                System.out.println("This command should go back think how ");
                break;
            } else {
                System.out.println("Invalid selection");
            }

        }
        return choice;
    }
    public int displayVenueOptions(List<Venue> venueList) {

        int choice = 0;
        System.out.println();
        System.out.println("Which venue would you like to view?");
        while (true) {
            for (int i = 0; i < venueList.size(); i++) {
                System.out.println("\t" + (i + 1) + ") " + venueList.get(i).getName());
            }
            System.out.println("\tR) Return to Previous Screen");
            String userChoice = in.nextLine();

            try {
                choice = Integer.parseInt(userChoice);
                if (choice >= venueList.size()) {
                    choice = venueList.size() - 1;
                }
                break;

            } catch (NumberFormatException e) {
                //an error message will be displayed  if the choice will be null
            }
            if (userChoice.equalsIgnoreCase("R")) {
                getChoiceFromMainMenu();
            }
            if (choice == 0) {
                System.out.println(choice + " is invalid choice");
            }

        }
        return choice;
    }

    public void displayVenueDetails(Venue venue){
        String categoryString = "";

        // If multiple values, add a comma between each category
        if (venue.getCategoryList().size() > 1) {
            categoryString = String.join(", ", venue.getCategoryList());

        // For single categories, string equals the first (and only) list element
        } else {
            categoryString = venue.getCategoryList().get(0);
        }

        // Print details
        System.out.println(venue.getName() );
        System.out.println("Location: " + venue.getCityName() + ", " + venue.getState());
        System.out.println("Categories: " + categoryString);
        System.out.println("\n" + venue.getDescription());
    }

    public void showSpaceOptionsByVenue(Venue venue) {
        List<Space> allSpaces = jdbcSpaceDAO.getSpaceByVenue(venue.getVenueId());


        String choice = displaySpaceOptions(); // choice options for space
        if (choice.equals("1")) {
            System.out.println(venue.getName());
            System.out.println();
            System.out.printf("%-5s %-45s %-10s %-10s %-10s %-15s %n", "id", "Name", "Open", "Close", "Daily Rate", "Max. Occupancy");
            for(Space space: allSpaces){
                System.out.printf("%-5s %-45s %-10s %-10s %-10s %-15s %n", space.getSpaceId() , space.getName(), space.getOpenDate(),
                        space.getCloseDate(), "\\$" + space.getDailyRate(), space.getMaxOccupancy());
            }

            displaySpaceOptions();



        }
        else if(choice.equals("2")){
            System.out.println("Come up with search for reservation");
        }
        else if (choice.equals("R")) {
            getChoiceFromMainMenu();
        }
    }

}