package models;

public interface Registrable {

    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String name);

    void registerInstance();

    void updateInstance();

    void incrementInstanceAmount();
}
