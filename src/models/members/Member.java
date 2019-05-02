/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.members;

import api.Tools;
import models.AbstractRecord;
import models.Rent;
import models.titles.Title;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Blueprint of a customer. It abstracts all attributes of MusicMembers, LiveConcertMember, Premium, TVMembers.
 * It extends the AbstractRecord class, therefore it inherit the id and name attribute.
 * Every member has a Membership card, this class lies only within the Member class (Inner class)
 * @param <T> T is restricted to be just a Title
 */
public abstract class Member<T extends Title> extends AbstractRecord{

    /**
     * directory of the Member class is located in records/members
     */
    public static final String directory = AbstractRecord.directory+"/members";


    /**
     * List of attributes
     */
    protected String phone;
    private MembershipCard membershipCard;
    private List<T> rentedTitles;


    /**
     * Complete constructor - takes all parameters
     * @param id
     * @param name
     * @param phone
     * @param membershipCard
     * @param rentedTitles
     */
    public Member(Integer id, String name, String phone, MembershipCard membershipCard, List<T> rentedTitles) {
        super(id, name);
        this.phone = phone;
        this.membershipCard = membershipCard;
        this.rentedTitles = rentedTitles;
    }


    /**
     * Constructor of 2 parameters
     * @param id
     * @param name
     * @param phone
     */
    public Member(String id, String name, String phone) {
        super(Integer.parseInt(id), name);
        this.phone = phone;
        this.membershipCard = assignMembershipCard();
        this.rentedTitles = new ArrayList<T>();
    }

    /**
     * Constructor of 2 parameters
     * @param name
     * @param phone
     */
    public Member(String name, String phone) {
        super(AbstractRecord.instanceAmount, name);
        this.phone = phone;
        this.membershipCard = new MembershipCard(id);
        this.rentedTitles = new ArrayList<T>();
    }

    /**
     * Reads all Files that store information of CM and returns the one that matches the ID
     * @param id
     * @return
     */
    public static Member searchMember(Integer id) {
        if(Tools.checkIfFileExistsWithinDirectory(Member.directory, id+"")){
            return AbstractRecord.abstractRecord(Tools.linesReader(Member.directory+"/"+id+".txt"));
        }else {
            return null;
        }
    }

    /**
     * Reads every Files that store information about rent, maps to the Rent Object, filtrates the rents that belong
     * to this member and add all rents to the field rentedTitles
     */
    public void fillRentedTitles() {
        Tools.listAllFilesOfDirectory(Rent.directory)
                .stream()
                .map(file -> (Rent) AbstractRecord.abstractRecord(Tools.linesReader(file.getPath())))
                .filter(rent -> rent.getMember().getId() == this.id)
                .map(rent -> Title.searchTitle(rent.getTitle().getId()))
                .forEach(title -> rentedTitles.add((T)title));
    }

    /**
     * Returns an existing membership card if it finds in the files, if not, returns an entire new Membership cards.
     * @return membershipCard
     */
    private MembershipCard assignMembershipCard() {
        if(Tools.checkIfFileExistsWithinDirectory(MembershipCard.directory, id+"")){
            List<String> params = Tools.linesReader(MembershipCard.directory + "/" + id + ".txt");
            return new MembershipCard(params.get(1), params.get(2), params.get(3));
        }else{
            return new MembershipCard(id);
        }

    }

    /**
     * checks points balance in the card and returns how many points left for the user to become eligible.
     * If the user happens to be eligigle, it will return 0 points.
     * @return
     */
    public Integer getRemainingPointsToBeEligible(){
        if(membershipCard.isEligible()){
            return 0;
        }else{
            return 100 - membershipCard.getPointBalance();
        }
    }

    /**
     * Makes the card attribute to update its balance
     */
    public void updatePoints(){
        getMembershipCard().updateBalance();
    }

    /**
     * Penalises customer by checking the days that exceeds 3 and takes 5 points from its card balance.
     * EG:
     * If the member membershipcard balance was 50, after the method run twice, the balance will be 40.
     * @param timeOfRent
     */
    public void penalizeMember(Double timeOfRent){
        if(timeOfRent > 3){
            membershipCard.setPointBalance((int) (membershipCard.getPointBalance()-(5*(timeOfRent-3))));
        }
    }

    /**
     * Method to be implemented
     * @param title
     * @return
     */
    public abstract Boolean checkIfIsEligibleToRentTitle(Title title);

    /**
     * Getters and Setters
     * @return
     */
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }

    public List<T> getRentedTitles() {
        return rentedTitles;
    }

    public void setRentedTitles(List<T> rentedTitles) {
        this.rentedTitles = rentedTitles;
    }


    /**
     * Places each fields of this class in an array of strings.
     * @return
     */

    @Override
    public String[] getRowValuesFromFields() {
        return new String[]{id+"",
                name,
                phone,
                getClass().getSimpleName(),
                getMembershipCard().cardType+"",
                getMembershipCard().pointBalance+""
        };
    }

    /**
     * returns an array of Strings that represents the name of the fields
     * @return
     */
    @Override
    public String[] getHeadersNameFromFields() {
        return new String[]{"id", "name", "phone", "membership", "cardType", "balance"};
    }

    /**
     * Checks if object comming from parameter is the same as this by comparing the fields
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Member member = (Member) o;
        return Objects.equals(phone, member.phone) &&
                Objects.equals(membershipCard, member.membershipCard) &&
                Objects.equals(rentedTitles, member.rentedTitles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone, membershipCard, rentedTitles);
    }

    /**
     * Returns the text formatted to be recorded in a file
     * @return
     */
    @Override
    public String toString() {
        return  this.getClass().getName()+"\n"+
                id +"\n"+
                name +"\n"+
                phone;
    }

    /**
     * CardType ENUM
     */
    public enum CardType{
        /**
         * CREDIT card has an acronym of CC whereas Debit card has an acronym of DC
         */
        CREDIT("CC"), DEBIT("DC");

        private final String acronym;

        CardType(String acronym) {
            this.acronym = acronym;
        }

        public String getAcronym() {
            return acronym;
        }
    }

    /**
     * Class declaration of MembershipCard. Because a membership card is Registrable, and has an ID and name, it will also
     * extend Abstract Record
     */
    public class MembershipCard extends AbstractRecord{


        /**
         * Directory is records/members/card
         */
        public static final String directory = Member.directory+"/cards";

        /**
         * List of attributes
         */
        private CardType cardType;
        private Integer pointBalance;

        /**
         * Complete Constructor - takes all parameter
         * @param cardId
         * @param cardType
         * @param pointBalance
         */
        public MembershipCard(String cardId, String cardType, String pointBalance) {
            super(Integer.parseInt(cardId), Member.this.name);
            this.cardType = CardType.valueOf(cardType);
            this.pointBalance = Integer.valueOf(pointBalance);
        }

        /**
         * Constructor of 2 parameters - point balance starts with 0.
         * @param cardId
         * @param cardType
         */
        public MembershipCard(Integer cardId, CardType cardType) {
            super(cardId, Member.this.name);
            this.cardType = cardType;
            this.pointBalance = 0;
        }

        /**
         * Constructor of 1 parameter
         * cartype default value is CREDIT and point balance is 0.
         * @param cardId
         */
        public MembershipCard(Integer cardId) {
            super(cardId, Member.this.name);
            this.cardType = CardType.CREDIT;
            this.pointBalance = 0;
        }

        /**
         * Default constructor
         */
        public MembershipCard() {
            super(null, "");
        }

        /**
         * updates the pointBalance field by incrementing in 10 or taking 10 if user is eligible
         */
        public void updateBalance(){
            if(isEligible()){
                pointBalance -= 100;
            }else{
                pointBalance += 10;
            }
        }

        /**
         * Checks if user is eligible, if point balance is grater equals 100, then true.
         * @return
         */
        public Boolean isEligible(){
            return pointBalance >= 100;
        }

        /**
         * Getters and seters
         * @return
         */
        public CardType getCardType() {
            return cardType;
        }

        public void setCardType(CardType cardType) {
            this.cardType = cardType;
        }

        public Integer getPointBalance() {
            return pointBalance;
        }

        public void setPointBalance(Integer pointBalance) {
            this.pointBalance = pointBalance;
        }

        /**
         * returns an array of strings the field values
         * @return
         */
        @Override
        public String[] getRowValuesFromFields() {
            return new String[]{cardType+"", pointBalance+""};
        }

        /**
         * returns an array of fields names
         * @return
         */
        @Override
        public String[] getHeadersNameFromFields() {
            return new String[]{"card type", "balance"};
        }

        /**
         * saves toString contet to a file
         */
        @Override
        public void commitInstance() {
            Tools.fileWriter(toString(), MembershipCard.directory, id+"");
        }

        /**
         * Formats string of this membershipCard to be saved in a file
         * @return
         */
        @Override
        public String toString() {
            return  getClass().getName()+"\n"+
                    id +"\n"+
                    cardType +"\n"+
                    pointBalance;
        }

    }


}
