package com.jean.mongodb.entity;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 3617931430808763429L;

    private String id;
    private String name;
    private int age;

    public Person() {
    }

    public Person(String id, String name, int age) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person[id=" + id + ",name=" + name + ",age=" + age + "]";
    }

}
