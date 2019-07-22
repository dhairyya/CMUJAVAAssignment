package com.dhairyya;

import java.io.*;
import java.util.List;

/* Created on 2nd July
 * This class creates an object of CleanData in order to perform Task 1 and Task 2
 * An object of RecommendMovies where task 3 and task 4 are performed.
 */
public class RecommendMoviesForNewUsers {

    //Instance variables
    private CleanData cd;
    private RecommendMovies rm;

    //default constructor
    RecommendMoviesForNewUsers() {
        cd = new CleanData();
        rm = new RecommendMovies();
    }

    /*
     * Initial setup method uses the objects of CleanData and RecommendMovies in order to perform task 1, task 2 and task 3
     * Takes in no input
     * Returns no output
     */
    public void initialSetup() {
        //Task 1 and task 2 performed
        cd.readAndWrite();
        //task 3 performed
        rm.CreateMovieMap();
        //displaying the data structure obtained in task 3
        rm.displayMovieMap();
    }

    /*
     * This method performs task 5, i.e, to read data about users from NewUsers.csv and recommend movies based on their age using recommendMovies method of RecommendMovies class(task4)
     * Takes in no input
     * Returns no output
     */
    public void recommendMoviesToNewUsersInCSV() {
        //local variables to store data read from the file(newUsers.csv)
        String row, userName, movieName;
        int userAge, noOfMoviesToRecommend;

        //to store the list of movies recommended by recommendMovies method
        List<String> movieNames;

        try {
            //creation of reader object to read data from NewUsers.csv file(input file)
            BufferedReader fileReader = new BufferedReader(new FileReader("NewUsers.csv"));
            //creation of writer object to write date into NewUsersAfterRecommending.csv file(output file)
            FileWriter fileWriter = new FileWriter("NewUsersAfterRecommending.csv");

            //unless there is data present in the file keep reading from it and processing it
            while ((row = fileReader.readLine()) != null) {
                //if the data read contains the string userName then proceed to the else part else to the if part
                if (!(row.contains("UserName"))) {
                    //split the data read from input file using comma as csv files are delimited by commas
                    String[] data = row.split(",");

                    //extract data from the file based on their position in the row of data read
                    userName = data[0];
                    userAge = Integer.parseInt(data[1]);
                    noOfMoviesToRecommend = Integer.parseInt(data[2]);

                    fileWriter.append(userName + "," + userAge + "," + noOfMoviesToRecommend + ",\"");

                    //get list of number of movies suitable to be recommended for people of this userAge
                    movieNames = rm.recommendMovies(userAge, noOfMoviesToRecommend);

                    //loop through the list of movies and write it into the output file using the proper output format
                    for (int index = 0; index < movieNames.size(); index++) {
                        movieName = movieNames.get(index);
                        fileWriter.append(movieName.substring(1, movieName.length() - 1));
                        if (index != (movieNames.size() - 1))
                            fileWriter.append(",");
                        else
                            fileWriter.append("\"\n");
                    }
                } //if the data read contains the string userName then write the data to the output file as it is
                else
                    fileWriter.append(row + "\n");
            }
            //write data to the output file NewUsersAfterRecommending.csv by clearing the fileWriter buffer
            fileWriter.flush();
            //close system critical resources
            fileWriter.close();
            fileReader.close();
        }//error handling code
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("NewUsers.csv file not found");
        } catch (IOException e) {
            System.out.println("Input Output Exception occurred while reading and writing data");
        }
        System.out.println("Recommending Movies for New Users Completed and recommendation can be found in NewUsersAfterRecommending.csv");
    }
}
