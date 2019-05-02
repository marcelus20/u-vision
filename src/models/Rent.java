/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models;

import api.Tools;
import models.members.Member;
import models.titles.Title;
import java.sql.Timestamp;


/**
 * Rent record blueprint
 */
public class Rent extends AbstractRecord {

    /**
     * directory is located at records/rents
     */
    public static final String directory = AbstractRecord.directory+"/rents";

    /**
     * List of attributes
     */
    private Member member;
    private Title title;
    private String dateOfRent;
    private Boolean returned;


    /**
     * Complete constructor
     * @param rentId
     * @param member
     * @param title
     * @param dateOfRent
     * @param returned
     */
    public Rent(String rentId, String member, String title, String dateOfRent, String returned) {
        super(Integer.parseInt(rentId), Member.searchMember(Integer.parseInt(member)).getName());
        this.member = Member.searchMember(Integer.parseInt(member));
        this.title = Title.searchTitle(Integer.parseInt(title));
        this.dateOfRent = dateOfRent;
        this.returned = Boolean.valueOf(returned);
    }

    /**
     * Constructor of 4 parameters
     * @param id
     * @param member
     * @param title
     * @param dateOfRent
     */
    public Rent(String id, String member, String title, String dateOfRent) {
        super(Integer.parseInt(id), Member.searchMember(Integer.parseInt(member)).getName());
        this.member = Member.searchMember(Integer.parseInt(member));
        this.title = Title.searchTitle(Integer.parseInt(title));
        this.dateOfRent = dateOfRent;
    }

    /**
     * Constructor of 3 parameters
     * @param member
     * @param title
     * @param dateOfRent
     */
    public Rent(Integer member, Integer title, String dateOfRent) {
        super(AbstractRecord.instanceAmount, Member.searchMember(member).getName());
        this.member = Member.searchMember(member);
        this.title = Title.searchTitle(title);
        this.dateOfRent = dateOfRent;
    }


    /**
     * Looks for files that stores information about Rents and and output a Rent object that matches the id
     * @param id
     * @return
     */
    public static Rent searchRent(Integer id) {
        if(Tools.checkIfFileExistsWithinDirectory(Rent.directory, id+"")){
            return AbstractRecord.abstractRecord(Tools.linesReader(Rent.directory+"/"+id+".txt"));
        }else {
            return null;
        }
    }

    /**
     * Records this object rent to the file
     */
    public void performRent(){
        title.setAvailable(false);
        member.commitInstance();
        title.commitInstance();
    }

    /**
     * This method makes this Rent class to be returned in the by changing its returned field to true and available
     * title field also to true
     */
    public void returnTitle(){
        if(!returned){
            calculateRentTime();
            title.setAvailable(true);
            Tools.breakLinesPrinting("Rent Return has been recorded successfully");
            title.commitInstance();
            returned = true;
            commitInstance();
        }else{
            System.out.println("No actions taken - title selected to return is in stock already.");
        }


    }

    /**
     * it calculates how long the this rent is late and applies penalty to the Customer by reducing it pointBalance field
     * member.penalizeMember()
     */
    private void calculateRentTime() {
        long initialDate = Timestamp.valueOf(Tools.convertToAmericanStringFormat(dateOfRent)+" 00:00:00").getTime();
        long returnDate = System.currentTimeMillis();
        Double daysRented = Tools.calculateTime(initialDate, returnDate);
        if(daysRented>3){
            member.penalizeMember(daysRented);
            Tools.breakLinesPrinting("YOU HAVE BEEN PENALIZED FOR RETURNING THE RENT LATE");
        }
        member.commitInstance();
    }

    /**
     * Increase the point balance of the Member by calling the member.updatePoints() method
     */
    private void benefitMember() {
        member.updatePoints();
    }

    /**
     * Checks if this rent is valid by checking if user is eligible to rent the title and also check if the file is available
     * @return
     */
    private Boolean rentIsValid() {
        return member.checkIfIsEligibleToRentTitle(title) && title.getAvailable();
    }

    /**
     * Getters and Setters
     * @return
     */
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getDateOfRent() {
        return dateOfRent;
    }

    public void setDateOfRent(String dateOfRent) {
        this.dateOfRent = dateOfRent;
    }


    /**
     * Registrable interface method - saves this method to a file
     */
    @Override
    public void commitInstance() {
        if(rentIsValid()){
            Tools.fileWriter(toString(), directory, id+"");
            benefitMember();
            Tools.breakLinesPrinting("Rent has been recorded successfully");
        }else {
            Tools.breakLinesPrinting("Rent has not been proceed due to the following reason:");
            if(!title.getAvailable()){
                System.out.println("TITLE IS RENTED, IT IS NOT AVAILABLE");
            }else if(!member.checkIfIsEligibleToRentTitle(title)){
                System.out.println("USER IS NOT ELIGIBLE TO RENT THIS TITLE\nUser:");
                Tools.breakLinesPrinting(member);
                System.out.println("Title: ");
                Tools.breakLinesPrinting(title);
            }

        }

    }

    /**
     * Printable interface methods
     * @return array of fields values
     */
    @Override
    public String[] getRowValuesFromFields() {
        return new String[]{id+"", member.getName(), title.getName(), dateOfRent, returned?"returned":"rented"};
    }

    /**
     * Printable interface method
     * @return array of field name
     */
    @Override
    public String[] getHeadersNameFromFields() {
        return new String[]{"id", "member", "title", "date of rent", "status"};
    }

    /**
     * The string format of this object to be recorded in the file
     * @return
     */
    @Override
    public String toString() {
        return this.getClass().getName()+"\n"+
                id +"\n"+
                member.getId() +"\n"+
                title.getId() +"\n"+
                dateOfRent+"\n"+
                returned;
    }
}
