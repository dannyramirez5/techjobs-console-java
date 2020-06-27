package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) { //The main application runner

        // 1. Present the user with choices on how to view data: list or search.
        // 2. Based on that choice, prompt them for the column to apply the choice to. In the case of a search, we also ask for a search term.
        // 3. Carry out the request to the JobData class via one of its public methods
        // 4. Display the results of the request
        // 5. Repeat.



        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        // 1. Present the user with choices on how to view data: list or search.
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            // 2. Based on that choice, prompt them for the column to apply the choice to
            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    // 3. Carry out the request to the JobData class via one of its public methods
                    //the all selection searches through all the columns, duh.
                    printJobs(JobData.findAll());
                } else {
                    // 3. Carry out the request to the JobData class via one of its public methods
                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }
                // 2. In the case of a search, we also ask for a search term
            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine().toLowerCase();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
//                    System.out.println("Search all fields not yet implemented.");
                } else {
                    // 3. Carry out the request to the JobData class via one of its public methods
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));//searchField is the Search by (All, Position Type, Employer, Location, Skill)
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary

    private static String getUserSelection(String menuHeader /*menuHeader is a String that is passed to display above the menu to provide context
                                                              for what the user is being asked. EX: "View Jobs By:", "List"*/
                                        , HashMap<String, String> choices /*choices is a HashMap of choices for the user. For example in the first
                                         line of the while loop in the main method. actionChoices is being passed in as choices. actionChoices is
                                         a HashMap of key value pairs "search", "Search | "list", "List". */
                                         ) {

//**********All this does is display a menu title which is from the menuHeader variable
// and a list of choices, from the choices variable, and returns the user's response.***********

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()]; //creates an array, of the size of how many items are in choices.

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {
            //Since this is a do-while loop, the do code always runs at least once.

            //the menuHeader prints to the console
            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        //All this does is prints to the console a list of jobs in a nicely formatted manner.
        //someJobs is an array of hashmaps

        if (someJobs.size() < 1){
            System.out.println("\n NO RESULTS BRO!");
        }
        else {
            for (HashMap<String, String> jobData : someJobs) {
                for (Map.Entry<String, String> job : jobData.entrySet()) {
                    System.out.println(job.getKey() + ": " + job.getValue());
                }
                System.out.println("\n");

            }
        }
    }
}
