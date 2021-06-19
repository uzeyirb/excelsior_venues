package com.techelevator.service.Impl;

import java.util.Scanner;

public class BookingImpl {




    private String getUserInput(String prompt) {
        String userInput = "";
        Scanner in = new Scanner(System.in);
        while (userInput.isEmpty())
        {
            System.out.print(prompt);
            userInput = in.nextLine();
        }
        return userInput;
    }
}
