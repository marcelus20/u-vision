package models.titles;

import models.AbstractRecord;
import models.Registrable;
import models.media.Media;

import java.util.Objects;

public abstract class Title extends AbstractRecord implements Registrable{

    public static final String directory = AbstractRecord.directory+"/titles";

    protected String year;
    protected Media media;

    public Title(Integer id, String name, String year, Media media) {
        super(id, name);
        this.year = year;
        this.media = media;
    }


    public Title(String id, String name, String year) {
        super(Integer.parseInt(id), name);
        this.year = year;
        this.media = Media.DVD;
    }

    public Title(String name, String year, Media media) {
        super(AbstractRecord.instanceAmount,name);
        this.year = year;
        this.media = media;
    }

    public static Title searchTitle(Integer title) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Title title = (Title) o;
        return Objects.equals(year, title.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), year);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "\n"+
                id + "\n"+
                name + "\n"+
                year;
    }
}
