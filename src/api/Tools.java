package api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Tools {

    public static String fileReader(String path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String fileContent = "";
            String line = br.readLine();
            while (line != null){
                fileContent+=line+"\n";
                line = br.readLine();
            }
            return fileContent;
        } catch (FileNotFoundException e) {
            System.out.println("Could not read the file");
            return null;
        } catch (IOException e) {
            System.out.println("Could not read line");
            return null;
        }
    }

    public static List<String> linesReader(String path){
        String[] lines = fileReader(path).split("\n");
        List<String> linesList = new ArrayList<>();
        IntStream.range(0, lines.length).forEach(i->linesList.add(lines[i]));
        return linesList;
    }


    public static void fileWriter(String content, String path, String fileName){
        try {
            FileWriter fw = new FileWriter(path+"/"+fileName+".txt");
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] popFirstIndex(String[] array) {
        String[] newArray = new String[array.length-1];
        IntStream.range(0, newArray.length).forEach(i->newArray[i] = array[i+1]);
        return newArray;
    }
}
