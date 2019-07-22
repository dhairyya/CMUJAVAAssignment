package com.dhairyya;

import java.io.*;

/* Created on 1st July
 * This class reads the file provided for assignment and performs task 1 and task 2 and generate a new CSV file at the end of it
 * The input file and all files used in this assignment are specified using relative file path
 */
public class CleanData {

    /*
     * This method performs Task2 that is to capitalize the first letter of each word in the movie name
     * Takes in a string holding the movie name
     * Returns a string holding the movie name in which first letter of each word is capitalized
     */
    private String capitalizeMovieName(String movieName) {
        //local variables
        int length = movieName.length(), beg = 2, end = 2;
        //string builder for efficient memory management and to deal with the concept of immutable string
        StringBuilder result = new StringBuilder();
        //convert the first letter of the movie name to upper case and add it to the string builder
        result.append((movieName.substring(0, 2)).toUpperCase());
        //till we reach the end of the movie name iterate through each character checking for whitespaces
        while (end < length) {
            //when a whitespace is encountered in the movie name
            if (movieName.charAt(end) == ' ') {
                //append the characters of the previous word to the string builder from the second character of that word till the very end of it
                // so for abacus we append only bacus to the string builder
                result.append(movieName.substring(beg, end));
                //move to the second character of the upcoming word
                beg = end + 2;
                //convert the first letter of the word to upper case and add it to the string builder
                result.append((movieName.substring(end, end + 2)).toUpperCase());
                end++;
            }//when a whitespace is not encountered then keep moving along
            else
                end++;
        }
        //append the characters of the last word of the movie name to the string builder from the second character of that word till the very end of it
        result.append(movieName.substring(beg, end));
        //return the content of String Builder in string format(capitalized movie name)
        return result.toString();
    }

    /*
     * This method performs task 1 and task 2(using capitalizeMovieName), i.e, to read data about ratings from RatingsInput.csv and clean them as specified in task 1 and task 2 and generate a new CSV file at the very end of it
     * Takes in no input
     * Returns no output
     */
    public void readAndWrite() {
        //local variables to store data read from the file(RatingsInput.csv)
        String row, userName, movieName;
        int userID, userAge, rating, movieID;
        try {
            //creation of reader object to read data from RatingsInput.csv file(input file)
            BufferedReader fileReader = new BufferedReader(new FileReader("RatingsInput.csv"));
            //creation of writer object to write date into RatingsInputAfterCleaning.csv file(output file)
            FileWriter fileWriter = new FileWriter("RatingsInputAfterCleaning.csv");

            //unless there is data present in the file keep reading from it and processing it
            while ((row = fileReader.readLine()) != null) {

                //if the data read contains the string userID then proceed to the else part else to the if part
                if (!(row.contains("UserID"))) {

                    //split the data read from input file using comma as csv files are delimited by commas
                    String[] data = row.split(",");

                    //extract data from the file based on their position in the row of data read
                    userID = Integer.parseInt(data[0]);
                    userName = data[1];
                    userAge = Integer.parseInt(data[2]);
                    //task 1 separating movieID from the movie name and removing " present before movieID
                    movieID = Integer.parseInt(data[4].substring(1));
                    //task 2 to capitalizee moviename using capitalizeMovieName method
                    movieName = capitalizeMovieName("\"" + data[5]);
                    rating = Integer.parseInt(data[6]);

                    //write data back to the output file after clean up operation(task1 and task2) has been performed
                    fileWriter.append((userID + "," + userName + "," + userAge + "," + movieID + "," + movieName + "," + rating + "\n"));
                }//if the data read contains the string userID then write the data to the output file as it is
                else
                    fileWriter.append(row + "\n");
            }
            //write data to the output file RatingsInputAfterCleaning.csv by clearing the fileWriter buffer
            fileWriter.flush();
            //close system critical resources
            fileWriter.close();
            fileReader.close();
        }//error handling code
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("RatingsInput.csv file not found");
        } catch (IOException e) {
            System.out.println("Input Output Exception occurred while reading and writing data");
        }
        System.out.println("Data Clean up Operation Completed Successfully and results can be found in RatingsInputAfterCleaning.csv");
    }
}
