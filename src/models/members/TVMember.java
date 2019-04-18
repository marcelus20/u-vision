package models.members;

import api.Tools;
import models.titles.BoxSet;
import models.titles.Title;

import java.util.List;

public class TVMember extends Member<BoxSet> {

    public TVMember(Integer id, String name, String phone, MembershipCard membershipCard, List<BoxSet> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }

    public TVMember(String id, String name, String phone) {
        super(id, name, phone);
    }

    public TVMember(String name, String phone) {
        super(name, phone);
    }

    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return title instanceof BoxSet;
    }

    @Override
    public void registerInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

    @Override
    public void updateInstance() {

    }
}
