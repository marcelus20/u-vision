package models.members;

import api.Tools;
import models.titles.Title;

import java.util.List;

public class Premium extends Member<Title>{

    public Premium(Integer id, String name, String phone, MembershipCard membershipCard, List<Title> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }

    public Premium(String id, String name, String phone) {
        super(id, name, phone);
    }

    public Premium(String name, String phone) {
        super(name, phone);
    }

    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return true;
    }

    @Override
    public void registerInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

    @Override
    public void updateInstance() {

    }
}
