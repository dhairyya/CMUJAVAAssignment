package com.dhairyya;

/* Created on 2nd July
 * This class creates an object of RecommendMoviesForNewUsers and recommend movie recommendations to the users in NewUsers.csv file
 */
public class RecommendationSystem {

    public static void main(String[] args) {
        RecommendMoviesForNewUsers rmnu = new RecommendMoviesForNewUsers();
        //initialSetup() does the cleanUp(task 1 and task2) operation and also set up a data structure as specified in Task 3
        rmnu.initialSetup();
        //Task 4 and Task 5 are performed in this step
        rmnu.recommendMoviesToNewUsersInCSV();
    }

}
