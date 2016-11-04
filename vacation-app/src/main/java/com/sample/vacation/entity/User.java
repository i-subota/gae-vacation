package com.sample.vacation.entity;

import java.util.Date;

/**
 * Created by Iryna Subota on 21.10.2016.
 */
public class User {

    private long id = -1;
    private String firstName;
    private String secondName;
    private Date birthDay;
    private String position;

    public User() {};

    private User(long id, String firstName, String secondName, Date birthDay, String position) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDay = birthDay;
        this.position = position;
    }

    public static User create(long id, String firstName, String secondName, Date birthDay, String position) {
        return new User(id, firstName, secondName, birthDay, position);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
