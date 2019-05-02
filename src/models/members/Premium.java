/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.members;

import api.Tools;
import models.titles.Title;
import java.util.List;

/**
 * Blueprint of a customer that can rent everything (Title)
 */
public class Premium extends Member<Title>{

    /**
     * Complete constructor - takes all parameters
     * @param id
     * @param name
     * @param phone
     * @param membershipCard
     * @param rentedTitles
     */
    public Premium(Integer id, String name, String phone, MembershipCard membershipCard, List<Title> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }

    /**
     * Constructor of 2 parameters
     * @param id
     * @param name
     * @param phone
     */
    public Premium(String id, String name, String phone) {
        super(id, name, phone);
    }

    /**
     * Constructor of 2 parameters
     * @param name
     * @param phone
     */
    public Premium(String name, String phone) {
        super(name, phone);
    }

    /**
     * returns always true, cause this customer can rent everything.
     * @param title
     * @return
     */
    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return true;
    }

    /**
     * records info about this class to the file.
     */
    @Override
    public void commitInstance() {
        Tools.fileWriter(toString(), directory, id+"");
        getMembershipCard().commitInstance();
    }

}
