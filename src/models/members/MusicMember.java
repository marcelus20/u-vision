/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.members;

import static api.Tools.*;
import models.titles.Music;
import models.titles.Title;
import java.util.List;

/**
 * Blueprint of the customer that is eligible to rent only Music titles
 */
public class MusicMember extends Member<Music> {

    /**
     * Complete constructor - takes all parameters
     * @param id
     * @param name
     * @param phone
     * @param membershipCard
     * @param rentedTitles
     */
    public MusicMember(Integer id, String name, String phone, MembershipCard membershipCard, List<Music> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }


    /**
     * Constructor of 3 parameters
     * @param id
     * @param name
     * @param phone
     */
    public MusicMember(String id, String name, String phone) {
        super(id, name, phone);
    }

    /**
     * Constructor of 2 parameters
     * @param name
     * @param phone
     */
    public MusicMember(String name, String phone) {
        super(name, phone);
    }

    /**
     * Checks if title is an instance of Music object
     * @param title
     * @return
     */
    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return title instanceof Music;
    }

    /**
     * writes to file the ino about this class
     */
    @Override
    public void commitInstance() {

        fileWriter(toString(),directory, id+"");
        getMembershipCard().commitInstance();
    }

}
