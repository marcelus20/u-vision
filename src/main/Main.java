package main;

import asg.cliche.ShellFactory;
import controller.Controller;

import java.io.File;
import java.io.IOException;



public class Main {



    private static void configRecordStructure(){
        File directory = new File("records");
        if(!directory.exists()){
            directory.mkdirs();
            new File(directory.getPath()+"/members").mkdirs();
            new File(directory.getPath()+"/members/cards").mkdirs();
            new File(directory.getPath()+"/rents").mkdirs();
            new File(directory.getPath()+"/titles").mkdirs();
        }
    }

    public static void main(String[] args) {


        String option = "lft - Look For Titles\n" +
                "lfm - Look For Members\n" +
                "lfr - Look For Rents\n" +
                "at - Add Titles\n" +
                "am - Add Members\n" +
                "um - Update Members\n" +
                "iar - Insert a Rent\n" +
                "rar - Return a Rent\n";

        configRecordStructure();

        try {
            ShellFactory
                    .createConsoleShell(option,
                            "Ultra-Vision CLI App - Created by: Felipe Mantovani - 2017192",
                            Controller.getInstance())
                    .commandLoop();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
