/**
 * author: Felipe Mantovani 2017192
 * @date: 2/5/2019
 */
package models.members;

import api.Tools;
import models.titles.Film;
import models.titles.Title;
import java.util.List;

/**
 * This class represents the Customer eligible just to rent Films
 */
public class FilmMember extends Member<Film> {

    /**
     * complete constructor - Takes all parameters
     * @param id
     * @param name
     * @param phone
     * @param membershipCard
     * @param rentedTitles
     */
    public FilmMember(Integer id, String name, String phone, MembershipCard membershipCard, List<Film> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }

    /**
     * Constructor of 3 parameters
     * @param id
     * @param name
     * @param phone
     */
    public FilmMember(String id, String name, String phone) {
        super(id, name, phone);
    }

    /**
     * Constructors of 2 parameters
     * @param name
     * @param phone
     */
    public FilmMember(String name, String phone) {
        super(name, phone);
    }

    /**
     * Checks if the title passed as parameter is instance of the Film title class
     * @param title
     * @return
     */
    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return title instanceof Film;
    }

    /**
     * This method comes from the Abstract method extension. It saves the object to the a file.
     */
    @Override
    public void commitInstance() {
        Tools.fileWriter(toString(), Member.directory, id+"");
    }

}
