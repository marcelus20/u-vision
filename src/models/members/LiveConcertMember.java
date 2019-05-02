/**
 * @author: Felipe Mantovani 2017192
 * @date: 2/5/2019
 */
package models.members;

import api.Tools;
import models.titles.LiveConcert;
import models.titles.Title;
import java.util.List;

/**
 * This class is the blueprint of a customer that is eligible to rent only live concert titles. It extends the Member class
 */
public class LiveConcertMember extends Member<LiveConcert> {

    /**
     * Complete constructor - takes all parameters
     * @param id
     * @param name
     * @param phone
     * @param membershipCard
     * @param rentedTitles
     */
    public LiveConcertMember(Integer id, String name, String phone, MembershipCard membershipCard, List<LiveConcert> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }


    /**
     * Constructor of 3 parameters
     * @param id
     * @param name
     * @param phone
     */
    public LiveConcertMember(String id, String name, String phone) {
        super(id, name, phone);
    }

    /**
     * Constructor of 2 parameters
     * @param name
     * @param phone
     */
    public LiveConcertMember(String name, String phone) {
        super(name, phone);
    }

    /**
     * Checks if title is instance of the live concert object.
     * @param title
     * @return
     */
    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return title instanceof LiveConcert;
    }

    /**
     * saves object info to file.
     */
    @Override
    public void commitInstance() {
        Tools.fileWriter(toString(), directory, id+"");
        getMembershipCard().commitInstance();
    }

}
