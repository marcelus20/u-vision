/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Abstract Record class. What is it?
 *
 * Everything that can be recorded in the records/ directory is a record.
 * It means that it will have an id, and a Name. I can be recorded to a file
 * and it can be printed.
 * On its declaration there are Registrable and Printable interfaces implementation.
 *
 * That is the most abstract model class. It means that all classes within the
 * model package, will extend this class.
 */
public abstract class AbstractRecord implements Registrable, PrintableRow {

    /**
     * This attribute will control the ID of the all of the records in the system, regardless of their type.
     */
    public static Integer instanceAmount = 0;

    /**
     * The directory is located in records/
     */
    public static final String directory = "records";

    /**
     * List of attributes
     */
    protected Integer id;
    protected String name;

    /**
     * Constructor
     * @param id
     * @param name
     */
    public AbstractRecord(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This is the factory method
     * Possible outcomes: FilmLover | LiveConcertMenber | Member | MusicMember | Premium | TVMember
     * BoxSet | Film | LiveConcert | Music | Title | Rent
     *
     * It will get a list of Strings which represents the lines of a specific file EG:
     *
     * models.Rent
     * 4
     * 1
     * 2
     * 21-04-2019
     * true
     *
     * The parameter would be something like:
     * {"models.Rent", "4", "1", "2", "21-04-2019", "true"}
     *
     * The factory will use REFLECTION to get the class by its name (fileLines.get(0));
     *
     * It will guess the constructor to be used to create the object and will output a new instance of that class casted in T
     * @param fileLines
     * @param <T> T should extend this class
     * @return
     */
    public static <T extends AbstractRecord> T abstractRecord(List<String> fileLines){
        try {
            Class<?> className = Class.forName(fileLines.get(0));
            fileLines.remove(0);

            Constructor[] constructors = className.getConstructors();
            Constructor correctConstructor = null;
            for(Constructor constructor : constructors){
                if(constructor.getParameterCount() == fileLines.size()){
                    correctConstructor = constructor;
                }
            }
            Integer numOfParams = fileLines.size();
            Object[] paramsArray = new Object[numOfParams];
            IntStream.range(0,fileLines.size()).forEach(i->paramsArray[i] = fileLines.get(i));

            return (T)correctConstructor.newInstance(paramsArray);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getters and setters
     * @return
     */
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if object from outside is the same as this one by comparing their attributes
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractRecord that = (AbstractRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /**
     * ToString method for this class
     * @return
     */
    @Override
    public String toString() {
        return  this.getClass().getName()+"\n"+
                id +"\n"+
                name;
    }
}
