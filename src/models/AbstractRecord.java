package models;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public abstract class AbstractRecord implements Registrable {

    public static Integer instanceAmount = 0;

    public static final String directory = "records";

    protected Integer id;
    protected String name;

    public AbstractRecord(Integer id, String name) {
        this.id = id;
        this.name = name;
        incrementInstanceAmount();
    }

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

    @Override
    public void incrementInstanceAmount() {
        instanceAmount += 1;
    }

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

    @Override
    public String toString() {
        return  this.getClass().getName()+"\n"+
                id +"\n"+
                name;
    }
}
