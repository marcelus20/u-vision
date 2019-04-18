package main;

import api.Tools;
import models.AbstractRecord;
import models.media.Media;
import models.members.*;
import models.titles.BoxSet;
import models.titles.Title;


public class Main {



    public static void main(String[] args) {

        LiveConcertMember member = new LiveConcertMember("Felipe", "0899595657");
        MusicMember musicMember = new MusicMember("Sara", "0854411254");
        Premium kleber = new Premium("kleber", "123456789");
        TVMember ariane = new TVMember("Ariane", "555444111");

        member.registerInstance();
        musicMember.registerInstance();
        kleber.registerInstance();
        ariane.registerInstance();
//
        BoxSet boxSet = new BoxSet("Cobra Kai", "2005", Media.DVD);
        boxSet.registerInstance();

        System.out.println(ariane.checkIfIsEligibleToRentTitle(boxSet));
//        System.out.println(member);
//        System.out.println(musicMember);
//        System.out.println(kleber);
//        System.out.println(boxSet);

//        System.out.println(AbstractRecord.abstractRecord(Tools.linesReader(Member.directory+"/0.txt")));
//        System.out.println(AbstractRecord.abstractRecord(Tools.linesReader(Title.directory+"/1.txt")));
    }
}
