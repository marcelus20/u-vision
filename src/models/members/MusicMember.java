package models.members;

import api.Tools;
import models.titles.Music;
import models.titles.Title;

import java.util.List;

public class MusicMember extends Member<Music> {

    public MusicMember(Integer id, String name, String phone, MembershipCard membershipCard, List<Music> rentedTitles) {
        super(id, name, phone, membershipCard, rentedTitles);
    }

//    public MusicMember(Integer id, String name, String phone) {
//        super(id, name, phone);
//    }

    public MusicMember(String id, String name, String phone) {
        super(id, name, phone);
    }

    public MusicMember(String name, String phone) {
        super(name, phone);
    }

    @Override
    public Boolean checkIfIsEligibleToRentTitle(Title title) {
        return title instanceof Music;
    }

    @Override
    public void registerInstance() {
        Tools.fileWriter(toString(),directory, id+"");
    }

    @Override
    public void updateInstance() {
    }

}
