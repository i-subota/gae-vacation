package com.sample.vacation.entity;

import com.sample.vacation.dict.DayType;

import java.util.Date;

/**
 * Created by Iryna Subota on 21.10.2016.
 */
public class Day {

    private Date date;
    private DayType type;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DayType getType() {
        return type;
    }

    public void setType(DayType type) {
        this.type = type;
    }
}
