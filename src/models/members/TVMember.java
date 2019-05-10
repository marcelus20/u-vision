/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.members;

import static api.Tools.*;
import models.titles.BoxSet;
import models.titles.Title;
import java.util.List;

/**
 * Blueprint model of the customer that can rent only Boxset titles
 */
public class TVMember extends Member<BoxSet> {

    /**
     * Complete constructor - takes all parameters
     * @param id
     * @param name
     * @param phone
     * @param membershipCard
     * @param rentedTitles
     */
    public TVMember(Integer id, String name, String phone, MembershipCard membershipCard, List<BoxSet> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }

    /**
     * Constructor of 3 paramebers
     * @param id
     * @param name
     * @param phone
     */
    public TVMember(String id, String name, String phone) {
        super(id, name, phone);
    }

    /**
     * Construcotr of 2 parameters
     * @param name
     * @param phone
     */
    public TVMember(String name, String phone) {
        super(name, phone);
    }

    /**
     * checks if title is an instance of Boxset
     * @param title
     * @return
     */
    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return title instanceof BoxSet;
    }

    /**
     * Writes info about this class to a file
     */
    @Override
    public void commitInstance() {
        fileWriter(toString(), directory, id+"");
        getMembershipCard().commitInstance();
    }

}
