/**
 * @author: Felipe Mantovani, 2017192
 * @date: 22/4/2019
 */
package controller;

import api.Tools;
import asg.cliche.Command;
import com.jakewharton.fliptables.FlipTable;
import models.AbstractRecord;
import models.PrintableRow;
import models.Registrable;
import models.Rent;
import models.members.*;
import models.titles.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static api.Tools.*;

/**
 * This class is the heart of the application. It is a singleton that manages and loops through the menu options.
 * It will be the client of all the models classes and allow user to interact to the recorded data by making use of the
 * data modeling and methods override by the Application, RecordsSearchable and MapInitializer interface.
 */
public class Controller implements Application, RecordsSearchable, MapInitializer{

    /**
     * singleton static instance
     */
    private static Controller controller;


    /**
     * getter for the static singleton instance.
     * @return
     */
    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }


    /**
     * Attributes: List of the Objects modelling.
     */
    private List<Member> memberList;
    private List<Title> titleList;
    private List<Rent> rentList;
    /**
     * Mappers attributes
     */
    private Map<String, String> titleMap;
    private Map<String, String> memberMap;


    /**
     * Private controller for singleton - initializing all attributes.
     */
    private Controller() {
        try{
            AbstractRecord.instanceAmount = calculateNextID();
            memberList = initializeListOfMembers();
            titleList = initializeListOfTitles();
            rentList = initializeListOfRents();
            titleMap = initTitleMap();
            memberMap = initMemberMap();
        }catch (Exception e){
            breakLinesPrinting("Could not initialize the controller");
        }

    }

    /**
     * Calculates the ID of the added records by counting everything (every file) recorded so far in the records folder.
     * Every model extends AbstractRecord in models.AbstractRecord, which has an static attribute called: instanceAmount.
     * Whatever returned value from this function will be assigned to models.AbstractRecord.instanceAmount.
     * @return
     */
    private Integer calculateNextID() {
        return (listAllFilesOfDirectoryRecursively("records").size()
                -listAllFilesOfDirectoryRecursively(Member.directory+"/cards").size())+1;
    }


    /**
     * The command annotation is to indicate this method is a menu option - Library used: Cliche.
     */
    @Command(description = "This method shows all available titles in the system")
    @Override
    public void lookForTitles() {
        try{
            breakLinesPrinting("Searching Titles...");

            /**
             * preparing headers and data before printing in a table format
             */
            String[] headers = titleList.get(0).getHeadersNameFromFields();
            String[][] data = convertListOfObjectTo2DStringArray(titleList);

            /**
             * printing FlipTables.of headers and data
             */
            breakLinesPrinting(FlipTable.of(headers, data));

            /**
             * prompting user to type either ID or name of title
             */
            String idOrName = input("Type the ID or Name of Title");
            /**
             * filtering list of titles by titles that matches name of id typed by user
             */
            List<Title> filtereedList = titleList.stream()
                    .filter(title -> title.getId().toString().equalsIgnoreCase(idOrName)
                            || title.getName().toLowerCase().contains(idOrName.toLowerCase()))
                    .collect(Collectors.toList());
            /**
             * print list.
             */
            filtereedList.forEach(title -> breakLinesPrinting(title));
            pause(2);
        }catch (Exception e){
            breakLinesPrinting("No titles were found, list may be empty or directory is corrupted or may not exist");
        }
    }

    /**
     * The command annotation is to indicate this method is a menu option - Library used: Cliche.
     */
    @Command(description = "This method shows all available customers and allows to select a customer by ID")
    @Override
    public void lookForMembers() {
        try{
            breakLinesPrinting("searching Customers");
            /**
             * preparing headers and data before printing in a table format
             */
            String[] headers = memberList.get(0).getHeadersNameFromFields();
            String[][] data = convertListOfObjectTo2DStringArray(memberList);

            /**
             * printing FlipTables.of headers and data
             */
            breakLinesPrinting(FlipTable.of(headers, data));

            /**
             * prompting user to type either ID or name of customer
             */
            String idOrName = input("Type the ID or Name of Customer");

            /**
             * filtering list of members/customer by that matches name of id typed by user
             */
            List<Member> filtereedList = memberList.stream()
                    .filter(member -> member.getId().toString().equalsIgnoreCase(idOrName)
                            || member.getName().toLowerCase().contains(idOrName.toLowerCase()))
                    .collect(Collectors.toList());

            /**
             * print list.
             */
            filtereedList.forEach(member -> breakLinesPrinting(member));
            pause(2);

            breakLinesPrinting("Display rented titles of members listed in the table above...");

            /**
             * Iterated over the filtered list and prints, retrieve from system the titles that each member of the list
             * have rented (fillRentedTitles() : List<T extend Titles> attribute initializer for member object)
             */
            filtereedList.forEach(member -> {
                member.fillRentedTitles();
                breakLinesPrinting("Displaying rents of "+member.getName()+"...");
                /**
                 * preparing headers and data
                 */
                String [] h = ((PrintableRow)member.getRentedTitles().get(0)).getHeadersNameFromFields();
                String [][] d = convertListOfObjectTo2DStringArray(member.getRentedTitles());
                /**
                 * Printing table format of headers and data
                 */
                breakLinesPrinting(FlipTable.of(h, d));
            });
        }catch (Exception e){
            breakLinesPrinting("No member were found, list may be empty or directory is corrupted or may not exist");
        }

    }

    /**
     * The command annotation is to indicate this method is a menu option - Library used: Cliche.
     */
    @Command(description = "This method shows all rents recorded so far")
    @Override
    public void lookForRents() {
        try{
            breakLinesPrinting("Displaying list of Rents...");
            /**
             * preparing headers and data before printing in a table format
             */
            String[] headers = rentList.get(0).getHeadersNameFromFields();
            String[][] data = convertListOfObjectTo2DStringArray(rentList);
            /**
             * printing table from list of rents
             */
            breakLinesPrinting(FlipTable.of(headers, data));

            /**
             * prompting user to type either ID of Rent
             */
            String id = input("Type the id of Rent");

            /**
             * Printing filtered list of rents that matches the id
             */
            List<Rent> filtereedList = rentList.stream()
                    .filter(rent -> rent.getId().toString().equalsIgnoreCase(id))
                    .collect(Collectors.toList());

            filtereedList.forEach(member -> breakLinesPrinting(member));
            pause(2);
        }catch (Exception e){
            breakLinesPrinting("No rents were found, list may be empty or directory is corrupted or may not exist");
        }

    }

    /**
     * The command annotation is to indicate this method is a menu option - Library used: Cliche.
     */
    @Command(description = "This method allows user to add new Titles to the system")
    @Override
    public void addTitles() {
        try {

            breakLinesPrinting("Adding new Title...");

            /**
             * Prompting user to enter name, year and media format of title: default attributes for every title object
             */
            String name = input("Type name of title");
            String year = input("Type year of title");
            String media = input("Type media this title will be recorded: BLUE_RAY, CD or CD", "CD", "DVD", "BLUE_RAY");

            /**
             * display all possible Title Options by iterating over titles HASHMAP object
             */
            breakLinesPrinting("Type one of the options bellow");
            titleMap.forEach((k,v)-> System.out.println(k));
            System.out.println();
            String chosenOption = input("Choose one of the options above: ", titleMap);

            /**
             * switch case to route to the proper Title instantiation
             */
            Registrable title = null;
            switch (chosenOption.toLowerCase()){
                case "boxset":// if boxset
                    String season = input("which Season?");
                    title = new BoxSet(AbstractRecord.instanceAmount+"", name, year, media, "true",season);break;
                case "film": //if film
                    String director = input("name of director");
                    title = new Film(AbstractRecord.instanceAmount+"", name, year, media, "true",director);break;
                case "music": // if Music
                    title = new Music(AbstractRecord.instanceAmount+"", name, year,media, "true");break;
                case "liveconcert": //liveconcert
                    String location = input("Location of Live Concert");
                    title = new LiveConcert(AbstractRecord.instanceAmount+"", name, year, media, "true",location);break;
                default:
                    breakLinesPrinting("Not a valid option, returning to menu");break;

            }
            title.commitInstance();//save changes
            updateFields();//update global variables/attributes
            breakLinesPrinting("Titles added successfully");
            pause(2);
        } catch (Exception e) {
            breakLinesPrinting("Could not add Title. An error occured");
        }
    }

    /**
     * The command annotation is to indicate this method is a menu option - Library used: Cliche.
     */
    @Command(description = "This method allows user to add new Customer to the system")
    @Override
    public void addMembers() {
        try {
            breakLinesPrinting("Adding new Customer...");


            /**
             * Prompting user to add name and phone for member
             */
            String name = input("Type name of member");
            String phone = inputPhone();

            /**
             * Displaying different types of user option
             */
            breakLinesPrinting("Type one of the options bellow");
            memberMap.forEach((key, value)-> System.out.println(key));
            System.out.println();

            String chosenOption = input("chosenOption: ", memberMap);

            /**
             * switch statement to route to the proper Member instantiation
             */
            Registrable member = null;
            switch (chosenOption.toLowerCase()){
                case "musicmember":// if music member
                    member = new MusicMember(AbstractRecord.instanceAmount+"", name, phone);break;
                case "filmmember": // if film member
                    member = new FilmMember(AbstractRecord.instanceAmount+"", name, phone);break;
                case "premium": //if premium
                    member = new Premium(AbstractRecord.instanceAmount+"", name, phone);break;
                case "tvmember": // if tvmember
                    member = new TVMember(AbstractRecord.instanceAmount+"", name, phone);break;
                case "liveconcertmember": // if live concert member
                    member = new LiveConcertMember(AbstractRecord.instanceAmount+"", name, phone);break;
                default:
                    breakLinesPrinting("Not a valid option, returning to menu");break;
            }

            String creditCardType = input("Type payment type: CREDIT or DEBIT",
                    "CREDIT", "DEBIT");
            ((Member)member).getMembershipCard().setCardType(Member.CardType.valueOf(creditCardType));
            member.commitInstance();
            updateFields();
            breakLinesPrinting("Member added successfully");
            pause(2);
        } catch (Exception e) {
            e.printStackTrace();
            breakLinesPrinting("Bad input, returning to menu");
        }
    }


    /**
     * This method is implemented with an algorithm that allows user to select a member by its id and changes information
     * about this user and record it to the file
     */
    @Command(description = "This method allows user to update customers")
    @Override
    public void updateMembers() {
        try{
            breakLinesPrinting("Updating a Member");
            /**
             * preparing headers and and data to be displayed in a table format
             */
            String[] headers = memberList.get(0).getHeadersNameFromFields();
            String[][] data = convertListOfObjectTo2DStringArray(memberList);
            breakLinesPrinting(FlipTable.of(headers, data));

            /**
             * prompting user to select the ID from the results displayed above
             */
            breakLinesPrinting("SELECT THE EXACT ID OF MEMBER YOU WISH TO UPDATE - MUST BE THE ONE FROM THE TABLE");
            Integer id = integerInput("Type the ID of Customer");

            /**
             * saving the current information about member in two variables. THe variables to be manipulated is
             * member, the variable that will remain untouched is oldMember.
             */
            Member member = Member.searchMember(id);
            Member oldMember = Member.searchMember(id);

            breakLinesPrinting(member);

            /**
             * prompting user to enter new values of the member
             */
            String newName = skipableInput("Edit Name or press enter to skip");
            String newPhone = (skipableInputPhone("Edit Phone or press enter to skip"));
            String newCardType = skipableInput("CREDIT or DEBIT card?", "CREDIT", "DEBIT", "");
            memberMap.forEach((key, value)-> System.out.println(key));
            System.out.println();
            String chosenOption = skipableInput("Choose one of these memberships or press enter to skip");

            /**
             * Modifying member object by its setters
             */
            member.setName(newName.equalsIgnoreCase("")?member.getName():newName);
            member.setPhone(newPhone.equalsIgnoreCase("")?member.getPhone():newPhone);
            member.getMembershipCard().setCardType(Member.CardType.valueOf(newCardType.equals("")?member.getMembershipCard().getCardType().toString():newCardType));

            if(!chosenOption.equalsIgnoreCase("")){//chosen option is not blank
                switch (chosenOption.toLowerCase()){
                    case "musicmember":
                        //assign member a new MusicMember object with the same attribute values
                        member = new MusicMember(member.getId()+"", member.getName(), member.getPhone());break;
                    case "filmmember":
                        //assign member a new FilmMember object with the same attribute values
                        member = new FilmMember(member.getId()+"", member.getName(), member.getPhone());break;
                    case "premium":
                        //assign member a new Premium object with the same attribute values
                        member = new Premium(member.getId()+"", member.getName(), member.getPhone());break;
                    case "tvmember":
                        //assign member a new TVMember object with the same attribute values
                        member = new TVMember(member.getId()+"", member.getName(), member.getPhone());break;
                    case "liveconcertmember":
                        //assign member a new LiveConcert object with the same attribute values
                        member = new LiveConcertMember(member.getId()+"", member.getName(), member.getPhone());break;
                    default:
                        breakLinesPrinting("Not a valid option, returning to menu");break;
                }
            }
            //record the modified member to the files
            member.commitInstance();
            updateFields();
            if(member.equals(oldMember)){
                breakLinesPrinting("No changes have been made to user "+ member.getName());
            }else{
                breakLinesPrinting("Member "+member.getName()+" has been successfully edited/updated");
            }

            pause(2);
        }catch(Exception e){
            breakLinesPrinting("Could not update member, member list may be empty or member selected not found");
        }
    }


    /**
     * Algorithm to create a rent and record it to the file
     */
    @Command(description = "This method allows user to record a rent")
    @Override
    public void insertARent() {
        try{
            breakLinesPrinting("Performing a Rent");

            /**
             * Displayling the list of Members
             */
            String[] headers = memberList.get(0).getHeadersNameFromFields();
            String[][] data = convertListOfObjectTo2DStringArray(memberList);
            breakLinesPrinting(FlipTable.of(headers, data));

            /**
             * Prompting user to select user by id
             */
            Integer id = integerInput("SELECT THE EXACT ID OF USER - MUST BE ONE OF THE TABLE ABOVE");
            Member member = Member.searchMember(id);

            headers = titleList.get(0).getHeadersNameFromFields();
            //converting the whole list of titles to a 2D array to display in a table format
            data = convertListOfObjectTo2DStringArray(titleList.
                    //filtering the list to titles that user is elligible to rent
                            stream().filter(title -> member.checkIfIsEligibleToRentTitle(title))
                    .collect(Collectors.toList()));
            breakLinesPrinting(FlipTable.of(headers, data));

            //prompting user to enter the desired id of title
            Integer titleId = integerInput("SELECT THE EXACT ID OF TITLE TO RENT - MUST BE ONE OF THE TABLE ABOVE");

            //retrieving from records the id of title.
            Title title = Title.searchTitle(titleId);

            //instanciating rent object
            Rent rent = new Rent(AbstractRecord.instanceAmount+"", member.getId()+"", title.getId()+"", getTodayDate());

            //recording rent to the file
            rent.commitInstance();
            //modifying title availability
            rent.performRent();
            if(member.getMembershipCard().isEligible()){
                breakLinesPrinting("CONGRATULATIONS, YOUR NEXT RENT YOU ARE ELIGIBLE TO RENT FOR FREE");
            }else{
                breakLinesPrinting("REMAINING POINTS: "+rent.getMember().getRemainingPointsToBeEligible());
            }
            updateFields();
            pause(2);
        }catch (Exception e){
            breakLinesPrinting("Could not initialize algorithm. Member list or Title list may be empty");
        }
    }


    /**
     * Algorithm to change the a rent record to returned and the title back to available
     */
    @Command(description = "This method allows user to record a rent return")
    @Override
    public void returnARent() {
        try{
            /**
             * printing all rented stuff
             */
            String[] headers = rentList.get(0).getHeadersNameFromFields();
            String[][] data = convertListOfObjectTo2DStringArray(rentList);
            breakLinesPrinting(FlipTable.of(headers, data));


            /**
             * selecting id of rent he wants to return
             */
            Integer id = integerInput("SELECT THE EXACT ID OF RENT TO RETURN - MUST BE ONE OF THE TABLE ABOVE");

            //retrieving from records the rent with that ID
            Rent rent = Rent.searchRent(id);

            //return the rent
            rent.returnTitle();
            updateFields();
            pause(2);
        }catch (Exception e){
            breakLinesPrinting("Could not initialize algorithm. No rents may have been recorded yet");
        }
        breakLinesPrinting("Returning a Rent...");
    }

    /**
     * Everytime a change is made in the records, the system will refresh the information by pulling data from files and
     * re-populating global lists with the up-to-date information
     */
    @Override
    public void updateFields() {
        try{
            memberList = initializeListOfMembers();
            rentList = initializeListOfRents();
            titleList = initializeListOfTitles();
            AbstractRecord.instanceAmount = calculateNextID();
        }catch (Exception e){
            breakLinesPrinting("Could not get update lists of Controller");
        }
    }

    /**
     * it will read all files in the member directory and the factory method AbstractRecord.abstractRecord() map files
     * to Member object.
     * @return
     */
    @Override
    public List<Member> initializeListOfMembers() {
        try {
            return listAllFilesOfDirectory(Member.directory)
                    .stream()
                    .map(file -> (Member)AbstractRecord.abstractRecord(linesReader(file.getPath())))
                    .collect(Collectors.toList());
        }catch (Exception e){
            breakLinesPrinting("Could not get members. Directory may be corrupted or not exist");
            return null;
        }

    }


    /**
     * It maps every file in the rent directory to the Rent object outputted by the factory method AbstractRecord.abstractRecord()
     * @return
     */
    @Override
    public List<Rent> initializeListOfRents() {
        try{
            return listAllFilesOfDirectory(Rent.directory)
                    .stream()
                    .map(file -> (Rent)AbstractRecord.abstractRecord(linesReader(file.getPath())))
                    .collect(Collectors.toList());
        }catch (Exception e){
            breakLinesPrinting("Could not get Rents, directory may not exist");
            return null;
        }
    }

    /**
     * It maps every file in the title directory and to the Title object outputted by the factory method AbstractRecord.abstractRecord()
     * @return
     */
    @Override
    public List<Title> initializeListOfTitles() {
        try {
            return listAllFilesOfDirectory(Title.directory)
                    .stream()
                    .map(file -> (Title)AbstractRecord.abstractRecord(linesReader(file.getPath())))
                    .collect(Collectors.toList());
        }catch (Exception e){
            breakLinesPrinting("Could not get Titles, directory may not exist");
            return null;
        }

    }


    /**
     * This method is the responsible to initialise the field titleMap
     * @return the dictionary.
     */
    @Override
    public Map<String, String> initTitleMap() {
        try{
            Map<String, String> map = new HashMap<>();
            map.put("Film", Film.class.getName());
            map.put("Music", Music.class.getName());
            map.put("LiveConcert", LiveConcert.class.getName());
            map.put("BoxSet", BoxSet.class.getName());
            return map;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Responsible to initialise the memberMap field
     * @return the dictionary
     */
    @Override
    public Map<String, String> initMemberMap() {
        try{
            Map<String, String> map = new HashMap<>();
            map.put("Premium", Premium.class.getName());
            map.put("MusicMember", MusicMember.class.getName());
            map.put("LiveConcertMember", LiveConcertMember.class.getName());
            map.put("TVMember", TVMember.class.getName());
            map.put("FilmMember", FilmMember.class.getName());
            return map;
        }catch (Exception e){
            breakLinesPrinting("Could not init Member MAP");
            return null;
        }
    }
}
