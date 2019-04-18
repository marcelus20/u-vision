package models.members;

import api.Tools;
import models.titles.LiveConcert;
import models.titles.Title;

import java.util.List;

public class LiveConcertMember extends Member<LiveConcert> {

    public LiveConcertMember(Integer id, String name, String phone, MembershipCard membershipCard, List<LiveConcert> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }


    public LiveConcertMember(String id, String name, String phone) {
        super(id, name, phone);
    }

    public LiveConcertMember(String name, String phone) {
        super(name, phone);
    }

    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return title instanceof LiveConcert;
    }

    @Override
    public void registerInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

    @Override
    public void updateInstance() {

    }
}
