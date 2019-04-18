package models;

import api.Tools;
import models.members.Member;
import models.titles.Title;

public class Rent extends AbstractRecord {

    public static Integer rentAmount = 0;
    public static final String directory = AbstractRecord.directory+"/rents";

    private Member member;
    private Title title;
    private String dateOfRent;

    public Rent(Integer rentId, Integer member, Integer title, String dateOfRent) {
        super(rentId, Member.searchMember(member).getName());
        this.member = Member.searchMember(member);
        this.title = Title.searchTitle(title);
        this.dateOfRent = dateOfRent;
    }

    public Rent(String rentId, Integer member, Integer title, String dateOfRent) {
        super(Integer.parseInt(rentId), Member.searchMember(member).getName());
        this.member = Member.searchMember(member);
        this.title = Title.searchTitle(title);
        this.dateOfRent = dateOfRent;
    }

    public Rent(Integer member, Integer title, String dateOfRent) {
        super(rentAmount, Member.searchMember(member).getName());
        this.member = Member.searchMember(member);
        this.title = Title.searchTitle(title);
        this.dateOfRent = dateOfRent;
    }


    @Override
    public void registerInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

    @Override
    public void updateInstance() {

    }

    @Override
    public String toString() {
        return this.getClass().getName()+"\n"+
                id +"\n"+
                name+"\n"+
                member.getId() +"\n"+
                title.getId() +"\n"+
                dateOfRent +"\n";
    }
}
