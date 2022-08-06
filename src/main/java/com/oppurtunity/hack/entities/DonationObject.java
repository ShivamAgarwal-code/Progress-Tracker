package com.oppurtunity.hack.entities;

import java.io.Serializable;

public class DonationObject implements Serializable {

    private String from;

    private String to;

    private String month;

    private String year;

    private String amount;

    public DonationObject(String from, String to, String month, String year, String amount) {
        this.from = from;
        this.to = to;
        this.month = month;
        this.year = year;
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
