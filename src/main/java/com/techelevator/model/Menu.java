package com.techelevator.model;

import com.techelevator.model.jdbc.JDBCVenueDAO;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;
    public JDBCVenueDAO jdbcVenueDAO;

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

    public int displayVenueOptions(List<Venue> venueList) {

        int choice = 0;
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
        System.out.println(venue.getName() );
        System.out.println("Location: " + venue.getCityName() + ", " + venue.getState());
        System.out.println("Categories: ");
        System.out.println("\n" + venue.getDescription());
    }
}