/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.titles;

import api.Tools;
import models.media.Media;

/**
 * Class Music has no extra atributes
 */
public class Music extends Title{

    /**
     * Complete constructor - takes all parameters
     * @param id
     * @param name
     * @param year
     * @param media
     * @param available
     */
    public Music(String id, String name, String year, String media, String available) {
        super(Integer.parseInt(id), name, year, Media.valueOf(media), Boolean.valueOf(available));
    }

    /**
     * Construcotr of 4 parameters - All strings
     * @param id
     * @param name
     * @param year
     * @param available
     */
    public Music(String id, String name, String year, String available) {
        super(id, name, year, available);
    }

    /**
     * Constructor of 4 parameters - takes Strings, Media Types and Booleans
     * @param name
     * @param year
     * @param media
     * @param available
     */
    public Music(String name, String year, Media media, Boolean available) {
        super(name, year, media, available);
    }

    /**
     * writes down the toString method to the the file - toString comes from from Ttitle Class
     */
    @Override
    public void commitInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

}
