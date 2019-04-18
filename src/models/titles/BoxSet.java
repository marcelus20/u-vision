package models.titles;

import api.Tools;
import models.media.Media;

public class BoxSet extends Video{

    private Integer season;

    public BoxSet(String id, String name, String year, String media, String season) {
        super(id, name, year, media);
        this.season = Integer.parseInt(season);
    }


    public BoxSet(String id, String name, String year, String season) {
        super(id, name, year);
        this.season = Integer.parseInt(season);
    }


    public BoxSet(String id, String name, String year) {
        super(id, name, year);
        season = getDefaultSeason();
    }

    public BoxSet(String name, String year, Media media) {
        super(name, year, media);
        season = getDefaultSeason();
    }

    private Integer getDefaultSeason() {
        return 1;
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
        return  this.getClass().getName()+"\n"+
                id +"\n"+
                name +"\n"+
                year +"\n"+
                media +"\n"+
                season;
    }
}
