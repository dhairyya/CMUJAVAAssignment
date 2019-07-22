package com.dhairyya;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/* Created on 1st July
 * This class reads the file created by CleanData(end of task 1 and task 2) and setups data structure as specified in task 3
 * Also has a method to recommend movies as specified in task 4
 */
public class RecommendMovies {

    //Instance variables
    private Map<Integer, Map<Integer, List<String>>> movieMap;
    private Map<Integer, List<String>> innerMap;
    private List<String> movieNames;

    /*
     * This method sets up the data structure as specified in task 3 by reading RatingsInputAfterCleaning.csv file formed by CleanData class
     * Takes in no input
     * Returns no output
     */
    public void CreateMovieMap() {
        //local variables to store data read from the file(RatingsInputAfterCleaning.csv)
        String row, movieName;
        int userAge, rating;

        //map to store the entire data structure
        movieMap = new HashMap<>();

        try {
            //creation of reader object to read data from RatingsInputAfterCleaning.csv file(input file)
            BufferedReader fileReader = new BufferedReader(new FileReader("RatingsInputAfterCleaning.csv"));

            //unless there is data present in the file keep reading from it and processing it
            while ((row = fileReader.readLine()) != null) {
                //if the data read contains the string userID then do not do anything
                if (!(row.contains("UserID"))) {
                    //split the data read from input file using comma as csv files are delimited by commas
                    String[] data = row.split(",");

                    //extract relevant data from the file based on their position in the row of data read
                    userAge = Integer.parseInt(data[2]);
                    movieName = data[4];
                    rating = Integer.parseInt(data[5]);

                    //code to set the data structure
                    //check if the map contains an entry corresponding to the key userAge. if yes go to the else part
                    if (!(movieMap.containsKey(userAge))) {
                        //an empty array list created
                        movieNames = new ArrayList<>();
                        //movieName read from the file added to the empty list created
                        movieNames.add(movieName);
                        //an empty tree mao is created which sort the keys in the reverse order(descending order. eg:- 5,4,3,2,1)
                        innerMap = new TreeMap<>(Collections.reverseOrder());
                        //put the key rating along with the list of movies as value in the map created
                        innerMap.put(rating, movieNames);
                        //put the userAge as key along with the innermap containing rating and list of movies as value
                        movieMap.put(userAge, innerMap);
                    } else {
                        //get the map(rating,list of movies) stored as value corresponding to the key userAge from movieMap
                        innerMap = movieMap.get(userAge);
                        //check if the map contains an entry corresponding to the key rating. if yes go to the else part
                        if (!(innerMap.containsKey(rating))) {
                            //an empty array list created
                            movieNames = new ArrayList<>();
                            //movieName read from the file added to the empty list created
                            movieNames.add(movieName);
                        } else {
                            //get the list of movies corresponding to the rating read from the input file
                            movieNames = innerMap.get(rating);
                            //add the movieName read from the input file to the list of movies
                            movieNames.add(movieName);
                        }
                        //put the key rating along with the list of movies as value in the map
                        innerMap.put(rating, movieNames);
                        //put the userAge as key along with the innermap containing rating and list of movies as value
                        movieMap.put(userAge, innerMap);
                    }
                }
            }
            //close system critical resources
            fileReader.close();
        }//error handling code
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("RatingsInput.csv file not found");
        } catch (IOException e) {
            System.out.println("Input Output Exception occurred while reading and writing data");
        }
        System.out.println("Map Created Completed Successfully");
    }

    /*
     * This method performs Task4 using recommendations method
     * This method acts as a check to see whether an entry corresponding to the userAge is present in the map or not, if yes recommendations method is called with that age group
     * Else we find the closest age group and recommend movies from that age group
     * Takes in age of the user and number of movies to be recommended for that age group as input
     * Returns list of string containing the names of the movies recommended
     */
    public List<String> recommendMovies(int userAge, int numberOfMoviesToRecommend) {
        //local variable
        int closestUserAge = 0;
        //if the movieMap does not contain the age group, we find the closest age group by iterating through the entire movie map
        if (!(movieMap.containsKey(userAge))) {
            int diff=Integer.MAX_VALUE,key;
            //iterate through the entire map to find the closest age group to the userAge passed in as input to this method
            for (Map.Entry<Integer, Map<Integer, List<String>>> entry : movieMap.entrySet()) {
                key=entry.getKey();
                //abs returns the absolute value.(modulus)
                //if the difference of the userAge and the key is less than the difference update the difference and the closestAgeGroup
                if(Math.abs(key - userAge)<diff){
                    closestUserAge = key;
                    diff = Math.abs(key-userAge);
                }
            }
            //update userAge to closestUserAge
            userAge = closestUserAge;
        }
        //call recommendations method and return its result to this method's caller
        // if userAge passed in as input exists in movie Map call recommendations with this value
        //else call recommendations with the closest UserAge value
        return recommendations(userAge,numberOfMoviesToRecommend);
    }

    /*
     * This method performs Task4
     * Takes in age of the user and number of movies to be recommended for that age group as input
     * Returns list of string containing the names of the movies recommended
     */
    private  List<String> recommendations(int userAge,int numberOfMoviesToRecommend){
        //an empty array list to hold the recommended movie names
        List<String> movieNamesToRecommend = new ArrayList<>();
        //get the map corresponding to the userAge from Movie Map
        innerMap = movieMap.get(userAge);
        System.out.print("Recommendation for people with age " + userAge+ " is [");
        //iterate through the map to recommend movies from best to worst rating until the list contain the exact number of movies to recommend or the map is exhausted
        for (Map.Entry<Integer, List<String>> entry : innerMap.entrySet()) {
            movieNames = entry.getValue();
            for (String value : movieNames) {
                //when number of movies to recommend is less than 1 return the list to the caller method
                if (numberOfMoviesToRecommend < 1){
                    System.out.println("]");
                    return movieNamesToRecommend;
                }
                //add movie name to the list
                movieNamesToRecommend.add(value);
                //display movie name on the console
                System.out.print(value+" ");
                //keep decrementing number of movies to recommend as we keep adding movies to the list
                numberOfMoviesToRecommend--;
            }
        }
        //if the map is exhausted we return all the number of movies recommended for that age group in the map
        return movieNamesToRecommend;
    }

    /*
     * This method displays the data structure setup in createMovieMap() method(task3)
     * Takes in no input
     * Returns no output
     */
    public void displayMovieMap() {
        //local variables
        int userAge, rating;

        //loop to iterate through the data structure and display the contents of the data structure
        for (Map.Entry<Integer, Map<Integer, List<String>>> entry : movieMap.entrySet()) {
            userAge = entry.getKey();
            innerMap = entry.getValue();
            System.out.print(userAge + "=>{");
            //loop to iterate through the map holding rating and list of movies for each age group
            for (Map.Entry<Integer, List<String>> innerEntry : innerMap.entrySet()) {
                rating = innerEntry.getKey();
                System.out.print(rating + "=>[");
                movieNames = innerEntry.getValue();
                //loop to iterate through the list of movies
                for (String value : movieNames)
                    System.out.print(value + " ");
                System.out.print("],");
            }
            System.out.println("}");
        }
        System.out.println("Map Displayed Successfully");
    }
}
