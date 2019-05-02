/**
 * @author: Felipe Mantovani ---- 2017192
 * @date: 22/4/2/2019
 */
package api;

import models.PrintableRow;
import org.apache.commons.io.FileUtils;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

/**
 * This class is the api with a set of utilities that will be used in the whole project. It has compact algorithms methods
 * to serve and help all the other classes throughout the system.
 */

public class Tools {


    /**
     * Takes a string path of a file, read its content and returns the full string of the whole content of the file
     * @param path
     * @return
     */
    private static String fileReader(String path){
        try {
            //instanciating Buffered Reader
            BufferedReader br = new BufferedReader(new FileReader(path));
            String fileContent = "";
            //reading line by line and appending to the content variable along with a \n break line
            String line = br.readLine();
            while (line != null){
                fileContent+=line+"\n";
                line = br.readLine();
            }
            //returning file content
            return fileContent;
        } catch (FileNotFoundException e) {
            System.out.println("Could not read the file");
            return null;
        } catch (IOException e) {
            System.out.println("Could not read line");
            return null;
        }
    }

    /**
     * Takes the string path of the file, reads its content and split it at every \n regex pattern returning an array of
     * String where each array element represents the line of the file.
     * EG:
     * params: "Line1\nLine2\nLine3" -> {Line1, Line2, Line3}
     *
     * @param path
     * @return
     */
    public static List<String> linesReader(String path){
        String[] lines = fileReader(path).split("\n");
        List<String> linesList = new ArrayList<>();
        //Lambda way of iterating over a range.
        IntStream.range(0, lines.length).forEach(i->linesList.add(lines[i]));
        return linesList;
    }


    /**
     * Takes the content to be written, the folder path of the file and the name of the file without extension and writes
     * the content to that file clearing all old content.
     * @param content
     * @param path
     * @param fileName
     */
    public static void fileWriter(String content, String path, String fileName){
        try {
            FileWriter fw = new FileWriter(path+"/"+fileName+".txt");
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Takes a list of titles and returns a 2D array of strings of the data contained in each title object of the list
     * EG:
     * [
     * Title{
     *     id: "1";
     *     name: "title";
     *     available: False
     * },
     * Title{
     *      id: "2";
     *      name: "title2";
     *      available: true
     *}
     *
     * returns -> {
     *     {"1", "title", "false"},
     *     {"2", title2", "true"}
     * }
     * @param titleList
     * @return
     */
    public static String[][] convertListOfObjectTo2DStringArray(List titleList) {
        String[][] data = new String[titleList.size()][];
        //iterate through the list and convert the state of each title object to an Array of string of the values
        IntStream.range(0, data.length).forEach(i->data[i] = ((PrintableRow)titleList.get(i))
                .getRowValuesFromFields());
        return data;
    }


    /**
     * takes a diretory string path and list every file contained in that folder including the ones contained in the subfolders
     * (Recursively)
     * @param directory
     * @return
     */
    public static List<File> listAllFilesOfDirectoryRecursively(String directory){
        return (List<File>) FileUtils.listFiles(new File(directory), new String[]{"txt"}, true);
    }


    /**
     * Takes a directory string path and returns every file contained in that folder except the subfolders (Not recursive)
     * @param directory
     * @return
     */
    public static List<File> listAllFilesOfDirectory(String directory){
        return (List<File>) FileUtils.listFiles(new File(directory), new String[]{"txt"}, false);
    }


    /**
     * Prompts user to type an input from keyboard. It loops if input is blank
     * @param instructions
     * @return
     */
    public static String input(String instructions){
        String input = "";
        do{
            System.out.print(instructions+" ");
            input = new Scanner(System.in).nextLine().trim();
            if(input.length() == 0){
                System.out.println("You have not typed anything. Please, try again");
            }
        }while(input.length() == 0);
        return input;
    }

    /**
     * This method serves the same purposes of the the input with expected inputs, but it takes a map as parameter
     * instead of arrays of expected input. The map itself is the expected input.
     * @param instructions
     * @param map
     * @return
     */
    public static String input(String instructions, Map<String, String> map) {
        String[] mappedString = new String[map.size()];
        List<String> mapValues = new ArrayList<>();
        map.forEach((k,v)-> mapValues.add(v));
        IntStream.range(0, map.size()).forEach(i-> {
            try {
                mappedString[i] = Class.forName(mapValues.get(i)).getSimpleName();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return input(instructions, mappedString);
    }

    /**
     * This method forces user to enter one of the given expected inputs passed as parameter.
     * If user enter a string that is not included in the expectedInput parameter list, it will loop and ask user to try again
     * until he/she succed.
     * This will help in case user needs to either type CD, DVD or BLUE_RAY or CREDIT and DEBIT. Anything different from that
     * will force user to re-enter input.
     * usage example:
     * input("Type either CREDIT or DEBIT", "CREDIT", "DEBIT"); -> expected: CREDIT or DEBIT strings inputted.
     * @param instructions
     * @param expectedInputs
     * @return
     */
    public static String input(String instructions, String... expectedInputs) {
        String input = "";
        do{
            input = input(instructions);
            if(!inputIsExpected(input, expectedInputs)){
                System.out.println("Wrong input, you should have typed");
                for(String expectedInput : expectedInputs){
                    System.out.print(expectedInput+" ");
                }
                System.out.println();
            }
        }while (!inputIsExpected(input, expectedInputs));
        return input.toUpperCase();
    }


    /**
     * Prompts user to enter an input from keyboard without worrying about validation or blank inputs.
     * @param instructions
     * @return
     */
    public static String skipableInput(String instructions){
        System.out.println(instructions);
        return new Scanner(System.in).nextLine().trim();
    }

    /**
     * This serves the skippable input porpuses, but this overloaded methods restricts the skipable input to return
     * just the expected inputs passed as parameters
     * @param instructions
     * @param expectedInputs
     * @return
     */
    public static String skipableInput(String instructions, String... expectedInputs) {
        String input = "";
        do{
            System.out.println(instructions);
            input = new Scanner(System.in).nextLine();
            if(!inputIsExpected(input, expectedInputs)){
                System.out.println("Wrong input, you should have typed");
                for(String expectedInput : expectedInputs){
                    System.out.print(expectedInput+" ");
                }
                System.out.println();
            }
        }while (!inputIsExpected(input, expectedInputs) && !input.equalsIgnoreCase(""));
        return input.toUpperCase();
    }

    /**
     * Prompts user to enter phone by forcing to be in the correct phone format but also allows user to skip by entering an
     * empty string.
     * @param s
     * @return
     */
    public static String skipableInputPhone(String s) {
        String input = "";
        do{
            System.out.println(s);
            input = new Scanner(System.in).nextLine();
            if(!validatePhone(input) && !input.equalsIgnoreCase("")){
                System.out.println("Phone not valid, try again");
                System.out.println("Phone must have numbers and have length of 9");
                pause(1);
            }
        }while (!validatePhone(input) && !input.equalsIgnoreCase(""));
        return input;
    }

    /**
     * Forces user to type a phone string by validating what he/she types.
     * @return
     */
    public static String inputPhone(){

        String input = "";
        do{
            input = input("Enter phone");
            if(!validatePhone(input)){
                System.out.println("Phone not valid, try again");
                System.out.println("Phone must have numbers and have length of 9");
                pause(1);
            }
        }while (!validatePhone(input));
        return input;
    }



    /**
     * Forces user to enter an Integer numeric Input.
     * @param instructions
     * @return
     */
    public static Integer integerInput(String instructions){
        return Integer.valueOf(numericInput(instructions));
    }

    /**
     * Forces user to type a String numeric input
     * @param instructions
     * @return
     */
    public static String numericInput(String instructions){
        String input;
        do {
            input = input(instructions);
        }while (!stringIsNumeric(input));
        return input;
    }

    /**
     * Takes a String and validates it against being numeric or not.
     * @param numericString
     * @return
     */
    public static boolean stringIsNumeric(String numericString) {
        try{
            Double.valueOf(numericString);
            return true;
        }catch (Exception e){
            System.out.println("String is not numeric");
            return false;
        }
    }

    /**
     * Takes a message and breaks line above and below the message and pauses the system for 1 second.
     * @param message
     */
    public static void breakLinesPrinting(Object message){
        System.out.println();
        System.out.println(message);
        System.out.println();
        Tools.pause(1);
    }


    /**
     * Takes the directory and name of file, not worrying about it extension, and returns true if file exists within directory
     * or false otherwise.
     * @param directory
     * @param fileName
     * @return
     */
    public static Boolean checkIfFileExistsWithinDirectory(String directory, String fileName) {

        Collection files = FileUtils.listFiles(new File(directory), new String[]{"txt"}, true);

        for(Iterator iterator = files.iterator(); iterator.hasNext();){
            File f = (File) iterator.next();
            if(f.getName().toLowerCase().equals(fileName.toLowerCase()+".txt")){
                return true;
            }
        }
        return false;
    }

    /**
     * returns today date string format to dd-MM-yyyy based on the System.currentTimeMillis epoch time.
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Timestamp(System.currentTimeMillis()));
    }


    /**
     * Pauses the thread for the given number of seconds passed as parameter.
     * @param seconds
     */
    public static void pause(Integer seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Takes a phone string and validates it by checking if it has length of 9 and if it has just numbers.
     * @param phone
     * @return
     */
    public static Boolean validatePhone(String phone) {
        return phone.length() == 9 && stringIsNumeric(phone);
    }




    /**
     * This method helps method above on validating whether input entered by user is contained in the parameter list.
     * @param input
     * @param expectedInputs
     * @return
     */
    public static Boolean inputIsExpected(String input, String... expectedInputs){

        for(String expectedInput : expectedInputs){
            if(input.equalsIgnoreCase(expectedInput)){
                return true;
            }
        }
        return false;
    }


    /**
     * Takes two different dates in EPOCH time format, subtract second by the first and recalculates the number of days
     * between date1 and date2.
     * This method is a helper for calculating how many days member had the rent.
     * @param initialDate
     * @param returnDate
     * @return
     */
    public static Double calculateTime(Long initialDate, Long returnDate) {
        return Double.parseDouble(String.valueOf(((((returnDate - initialDate)/1000)/60)/60)/24));
    }


    /**
     * Gets our conventional date format and converts it into American date format by inverting its order:
     * usage:
     * convertToAmericanStringFormat(11-05-2019) -> 2019-05-11
     * @param date1
     * @return
     */
    public static String convertToAmericanStringFormat(String date1){
        String newDateString = "";
        String[] brokenDateString = date1.split("-");
        for(int i = brokenDateString.length-1; i >= 0; i--){
            newDateString += (i==brokenDateString.length-1?"":"-")+brokenDateString[i];
        }
        return newDateString;
    }

}
