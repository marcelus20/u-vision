/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models;

/**
 * This interface is meant to allow subclasses to be printed in the consoled in a not predefined way.
 *
 */
public interface PrintableRow {

    /**
     * This method is concerned with the array of Strings that stores VALUES of fields of the implemented classes
     * EG:
     * {
     *     name: "Felipe";
     *     surename "Mantovani";
     * }
     * @return {"Felipe", "Mantovani"}
     */
    String[] getRowValuesFromFields();


    /**
     * this method is concerned with the array of Strings that stores the NAME of the fields of the implemented classes
     * EG:
     * {
     *      name: "Felipe";
     *      surename "Mantovani";
     * }
     * @return {"name", "Mantovani"}
     */
    String[] getHeadersNameFromFields();
}
