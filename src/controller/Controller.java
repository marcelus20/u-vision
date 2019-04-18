package controller;

import models.Rent;
import models.members.Member;
import models.titles.Title;

import java.util.List;

public class Controller implements Application, RecordsSearchable{

    public static Controller controller;

    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }

        return controller;
    }



    List<Member> memberList;
    List<Title> titleList;
    List<Rent> rentList;




    public Controller() {

        memberList = initializeListOfMembers();
        titleList = initializeListOfTitles();
        rentList = initializeListOfRents();

    }

    @Override
    public void searchForTitles() {

    }

    @Override
    public void searchForCustomers() {

    }

    @Override
    public void addNewTitles() {

    }

    @Override
    public void addNewCustomers() {

    }

    @Override
    public void updateCustomers() {

    }

    @Override
    public void performARent() {

    }

    @Override
    public void performARentReturn() {

    }

    @Override
    public List<Member> initializeListOfMembers() {
        return null;
    }

    @Override
    public List<Rent> initializeListOfRents() {
        return null;
    }

    @Override
    public List<Title> initializeListOfTitles() {
        return null;
    }
}
