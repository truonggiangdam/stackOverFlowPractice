package com.example.giangdam.stackoverflowpractice.model;

import java.io.Serializable;

/**
 * Created by cpu11326-local on 12/01/2018.
 */

public class Student implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private String emailId;

    public Student() {

    }

    public Student(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
