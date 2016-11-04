package com.sample.vacation.entity;

import com.sample.vacation.dict.VacationStatus;

import java.util.List;

/**
 * Created by Iryna Subota on 21.10.2016.
 */
public class Vacation {
    private User user;
    private List<Day> days;
    private VacationStatus status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public VacationStatus getStatus() {
        return status;
    }

    public void setStatus(VacationStatus status) {
        this.status = status;
    }
}
