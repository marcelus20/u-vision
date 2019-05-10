/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.titles;

import static api.Tools.*;
import models.AbstractRecord;
import models.media.Media;
import java.util.Objects;

/**
 * Title blueprint class, it extends AbstractRecord, therefore it(or its children)
 * implements Registrable and Printable interfaces.
 * All titles are to be recorded on records/titles
 */
public abstract class Title extends AbstractRecord{

    /**
     * directory is located at records/titles
     */
    public static final String directory = AbstractRecord.directory+"/titles";

    /**
     * List of Attributes
     */
    protected String year;
    protected Media media;
    protected Boolean available;

    /**
     * Complete Constructor - takes all parameters
     * @param id
     * @param name
     * @param year
     * @param media
     * @param available
     */
    public Title(Integer id, String name, String year, Media media, Boolean available) {
        super(id, name);
        this.year = year;
        this.media = media;
        this.available = available;
    }


    /**
     * Constructor of 4 parameters - default Media is DVD
     * @param id
     * @param name
     * @param year
     * @param available
     */
    public Title(String id, String name, String year, String available) {
        super(Integer.parseInt(id), name);
        this.year = year;
        this.media = Media.DVD;
        this.available = Boolean.valueOf(available);
    }

    /**
     * Constructor of 4 parameters - ID is taken from AbstractRecord.instanceAmount static attribute
     * @param name
     * @param year
     * @param media
     * @param available
     */
    public Title(String name, String year, Media media, Boolean available) {
        super(AbstractRecord.instanceAmount,name);
        this.year = year;
        this.media = media;
        this.available = available;
    }

    /**
     * searchs for any files that stores Title information and outputs an instance of Title using the factory
     * method AbstractRecord.abstractRecord()
     * @param id
     * @return
     */
    public static Title searchTitle(Integer id) {
        if(checkIfFileExistsWithinDirectory(Title.directory, id+"")){
            return AbstractRecord.abstractRecord(linesReader(Title.directory+"/"+id+".txt"));
        }else {
            return null;
        }
    }

    /**
     * geters and setters
     * @return
     */
    public Boolean getAvailable() {
        return available;
    }


    public void setAvailable(Boolean available){
        this.available = available;
    };

    /**
     * Printable interface method
     * Returns an array of strings of this object fields
     * @return
     */
    @Override
    public String[] getRowValuesFromFields() {
        return new String[]{id+"", name, year, media+"", available?"available":"Not available"};
    }

    /**
     * Printable interface method
     * Returns an array of strings of name of fields
     * @return
     */
    @Override
    public String[] getHeadersNameFromFields() {
        return new String[]{"id", "name", "year", "media", "Availability"};
    }

    /**
     * Checks if object from outside is the same as this object by comparing the attributes
     * @param o
     * @return
     */
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

    /**
     * String format of this instance to record in the file.
     * @return
     */
    @Override
    public String toString() {
        return this.getClass().getName() + "\n"+
                id + "\n"+
                name + "\n"+
                year+ "\n"+
                available;
    }
}
