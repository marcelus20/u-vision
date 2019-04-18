package models.titles;

import api.Tools;
import models.media.Media;

public class LiveConcert extends Video {

    private String location;

    public LiveConcert(String id, String name, String year, String media, String location) {
        super(id, name, year, media);
        this.location = location;
    }

    public LiveConcert(String id, String name, String year, String location) {
        super(id, name, year);
        this.location = location;
    }

    public LiveConcert(String name, String year, Media media, String location) {
        super(name, year, media);
        this.location = location;
    }

    @Override
    public void registerInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

    @Override
    public void updateInstance() {

    }
}
