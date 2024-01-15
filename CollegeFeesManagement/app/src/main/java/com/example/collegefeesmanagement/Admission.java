package com.example.collegefeesmanagement;

public class Admission {
    int id;
    String name,number,email,year,branch,caste,paid;

    public Admission() {
    }

    public Admission(int id, String name, String number, String email, String year, String branch, String caste, String paid) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.year = year;
        this.branch = branch;
        this.caste = caste;
        this.paid = paid;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getYear() {
        return year;
    }

    public String getBranch() {
        return branch;
    }

    public String getCaste() {
        return caste;
    }

    public String getPaid() {
        return paid;
    }
}
