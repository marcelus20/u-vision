package controller;

import models.Rent;
import models.members.Member;
import models.titles.Title;

import java.util.List;

public interface RecordsSearchable {

    List<Member> initializeListOfMembers();
    List<Rent> initializeListOfRents();
    List<Title> initializeListOfTitles();
}
