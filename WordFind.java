/*
Name: Chris Rogers
Email: Rogersc21@winthrop.edu 
*/

import java.util.*;
import java.io.*;

public class WordFind {
    public static void main(String[] args) throws IOException {
        
        //Let user know if they did not pass an argument, then gently close program
        if(args.length < 1) {
            System.out.println("No input files passed to program. Please specify at least one file at the command line and try running the program again");
            return;
        }

        //Read in file from 1st argument and let user know if the file does not exist
        File inputFile = new File(args[0]);
        if(!inputFile.exists()) {
            System.out.println("Error opening file: " + inputFile + " please ensure this file is input correctly and try running program again");
            return;
        }
        Scanner myReader = new Scanner(inputFile);
        StringBuffer crossWordText = new StringBuffer();
        int textRows = 0;
        String textColumnLength = myReader.nextLine();
        //length-1 used to ignore last "bar" character that would get disposed of anyway
        int textColumns = (textColumnLength.length()-1)/2;
        int w=0;
        
        //Read in contents of file
        while(myReader.hasNextLine()) {
            if(w%2 != 0) {
                myReader.nextLine();
                ++w;
                continue;
            }
        
            //Add file contents to string buffer
            crossWordText.append("\n" + myReader.nextLine());
            textRows++;
            ++w;
        }
        myReader.close();

        //Delete every other line of input file to get rid of "--" characters
        for(int i = 1; i <= crossWordText.length(); i++) {
        crossWordText.deleteCharAt(i);
        }

        //Load a 2D array with the chars from the input file
        char[][] crossWordArray =  new char[textRows][textColumns];
        int p = 0;
        for(int i = 0; i < textRows; i++) {
            ++p;
            for(int j = 0; j < textColumns; j++) {
                crossWordArray[i][j] = crossWordText.charAt(p);
                ++p;
            }
        }

        //If 2nd argument missing, get words from user input. If not then read 2nd argument as file and search sequentially
        if(args.length < 2) {
            System.out.println("Input the word you would like to search for, followed by the return key. When you are done searching, press 1 to exit");
            String leaveLoop = "0";
            Scanner userInput;
            while(!"1".equals(leaveLoop)){
                userInput = new Scanner(System.in);
                String key = userInput.nextLine();
                key = key.toUpperCase();
                leaveLoop = key;
                findWord(key, crossWordArray);
            }
        }
        else{
            //Read in file with word list
            File userWordFile = new File(args[1]);
            myReader = new Scanner(userWordFile); 

            while(myReader.hasNextLine()) {
                String key = myReader.nextLine();
                key = key.toUpperCase();
                findWord(key, crossWordArray);
            }
        }
    }

    static void findWord(String key, char[][]crossWordArray) { 
        //Exit program if user inputs 1 in the terminal      
        if("1".equals(key)) {
            System.out.println("Program Complete. Exiting Now.");
            return;
        }
        //Sequentially check each direction and if word not found, let user know
        if(!horizontalCheck(key, crossWordArray)) {
            if(!verticalCheck(key, crossWordArray)) {
                if(diagonalCheck(key, crossWordArray)) {
                }
                else {System.out.print(key + " Not Found\n");}
            }
        }
    }
    
    static boolean horizontalCheck(String key, char[][]crossWordArray) {
        boolean found = false;
        int count = 1;
        int i = 0;
        int j = 0;
        int c = 0;
        int jTemp = 0;

        //Search Left To Right
        for(i = 0; i < crossWordArray.length && !found; ++i) {
            for(j = 0; j <= crossWordArray.length-key.length() && !found; ++j) {
                jTemp = j;
                c=0;
                while(key.charAt(c) == crossWordArray[i][j] || key.charAt(c) == ' '){
                    if(key.charAt(c) == ' '){
                        c++;
                    }
                    else{
                        j++;
                        c++;
                        if(count == key.length()) {
                            found = true;
                            System.out.println(key + " found starting at " + (i+1) + ", " + (jTemp+1) + " and oriented East");
                            break;
                        }
                        count++;
                    }
                }
                count = 1;
                if(!found) { 
                    j = jTemp;
                }
            }
        }

        //If not found, search right to left
        if(!found){
            for(i = 0; i < crossWordArray.length && !found; ++i) {
                for(j = crossWordArray.length - 1; j >= key.length()-1 && !found; --j) {
                    jTemp = j;
                    c=0;
                    while(key.charAt(c) == crossWordArray[i][j] || key.charAt(c) == ' '){
                        if(key.charAt(c) == ' '){
                            c++;
                        }
                        else{
                            j--;
                            c++;
                            if(count == key.length()) {
                                found = true;
                                System.out.println(key + " found starting at " + (i+1) + ", " + (jTemp+1) + " and oriented West");
                                break;
                            }
                            count++;
                        }
                    }
                    count = 1;
                    if(!found) { 
                        j = jTemp;
                    }
                }
            }
        }    
        return found;
    }
    
    static boolean verticalCheck(String key, char[][]crossWordArray) {
        boolean found = false;
        int count = 1;
        int i = 0;
        int j = 0;
        int c = 0;
        int iTemp = 0;

        //Search Up-Down
        for(i = 0; i < crossWordArray.length-key.length() && !found; ++i) {
            for(j = 0; j < crossWordArray.length && !found; ++j) {              
                iTemp = i;
                c=0;
                while((i < crossWordArray.length && key.charAt(c) == crossWordArray[i][j]) || key.charAt(c) == ' '){
                    if(key.charAt(c) == ' '){
                        c++;
                    }
                    else{
                        i++;
                        c++;
                        if(count == key.length()) {
                            found = true;
                            System.out.println(key + " found starting at " + (iTemp+1) + ", " + (j+1) + " and oriented South");
                            break;
                        }
                        count++;
                    }
                }
                count = 1;
                if(!found) { 
                    i = iTemp;
                }
            }
        }
        //If not found, Search Down-Up
        if(!found){
            for(i = crossWordArray.length-1; i >= key.length() && !found; --i) {
                for(j = 0; j < crossWordArray.length && !found; ++j) {              
                    iTemp = i;
                    c=0;
                    while(i >= 0 && key.charAt(c) == crossWordArray[i][j] || key.charAt(c) == ' '){
                        if(key.charAt(c) == ' '){
                            c++;
                        }
                        else{
                            i--;
                            c++;
                            if(count == key.length()) {
                                found = true;
                                System.out.println(key + " found starting at " + (iTemp+1) + ", " + (j+1) + " and oriented North");
                                break;
                            }
                            count++;
                        }
                    }
                    count = 1;
                    if(!found) { 
                        i = iTemp;
                    }
                }
            }
        }
        return found;
    }

    static boolean diagonalCheck(String key, char[][]crossWordArray) {
        boolean found = false;
        int count = 1;
        int i = 0;
        int j = 0;
        int c = 0;
        int iTemp = 0;
        int jTemp = 0;

        //Search Diagonally Right-Down
        for(i = 0; i <= crossWordArray.length-key.length() && !found; ++i) {
            for(j = 0; j <= crossWordArray.length-key.length() && !found; ++j) {
                iTemp = i;
                jTemp = j;
                c = 0;
                count = 1;
                while(j < crossWordArray.length && i < crossWordArray.length && key.charAt(c) == crossWordArray[i][j] || key.charAt(c) == ' '){
                    if(key.charAt(c) == ' '){
                        continue;
                    }
                    else{
                        i++;
                        j++;
                        c++;
                        if(count == key.length()) {
                            found = true;
                            System.out.println(key + " found starting at " + (iTemp+1) + ", " + (jTemp+1) + " and oriented Southeast");
                            break;
                        }
                        count++;
                    }
                }
                if(!found) {
                    i = iTemp;
                    j = jTemp;
                }
            }
        } 

        //If not found Search Diagonally Right-Up
        if(!found){  
            for(i = key.length()-1; i < crossWordArray.length && !found; ++i) {
                for(j = 0; j <= crossWordArray.length - key.length() && !found; ++j){
                    iTemp = i;
                    jTemp = j;
                    c = 0;
                    count = 1;
                    while(j < crossWordArray.length && i >= 0 && key.charAt(c) == crossWordArray[i][j] || key.charAt(c) == ' '){
                        if(key.charAt(c) == ' '){
                            c++;
                            count++;
                            continue;
                        }
                        else{
                            i--;
                            j++;
                            c++;
                            if(count == key.length()) {
                                found = true;
                                System.out.println(key + " found starting at " + (iTemp+1) + ", " + (jTemp+1) + " and oriented Northeast");
                                break;
                            }
                            count++;
                        }
                    }
                    if(!found) {
                        i = iTemp;
                        j = jTemp;
                    }
                }
            }
        }

        //If not found Search Diagonally Left-Down
        if(!found){  
            for(i = 0; i <= crossWordArray.length-key.length() && !found; ++i) {
                for(j = key.length()-1; j < crossWordArray.length && !found; ++j){
                    iTemp = i;
                    jTemp = j;
                    c = 0;
                    count = 1;
                    while(i < crossWordArray.length && j >= 0 &&key.charAt(c) == crossWordArray[i][j] || key.charAt(c) == ' '){
                        if(key.charAt(c) == ' '){
                            //c++;
                            continue;
                        }
                        else{
                            i++;
                            j--;
                            c++;
                            if(count == key.length()) {
                                found = true;
                                System.out.println(key + " found starting at " + (iTemp+1) + ", " + (jTemp+1) + " and oriented Southwest");
                                break;
                            }
                            count++;
                        }
                    }
                    if(!found) {
                        i = iTemp;
                        j = jTemp;
                    }
                }
            }
        }

        //If not found Search Diagonally Left-Up
        if(!found){  
            for(i = key.length()-1; i < crossWordArray.length && !found; ++i) {
                for(j = key.length()-1; j < crossWordArray.length && !found; ++j){
                    iTemp = i;
                    jTemp = j;
                    c = 0;
                    count = 1;
                    while(((j >= 0 && i >= 0) && key.charAt(c) == crossWordArray[i][j]) || key.charAt(c) == ' '){
                        if(key.charAt(c) == ' '){
                            //c++;
                            continue;
                        }
                        else{
                            i--;
                            j--;
                            c++;
                            if(count == key.length()) {
                                found = true;
                                System.out.println(key + " found starting at " + (iTemp+1) + ", " + (jTemp+1) + " and oriented Northwest");
                                break;
                            }
                            count ++;
                        }
                    }
                    if(!found) {
                        i = iTemp;
                        j = jTemp;
                    }
                }
            }
        }
        return found;
    
    }
}