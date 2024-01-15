package com.example.collegefeesmanagement;

public class Student {
    int id,fees;
    String name,number,email,year,branch,caste,status;

    public Student(int id, String name, String number, String email, String year, String branch, String caste, int fees,String status) {
        this.id = id;
        this.fees = fees;
        this.name = name;
        this.number = number;
        this.email = email;
        this.year = year;
        this.branch = branch;
        this.caste = caste;
        this.status = status;
    }

    public Student() {
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

    public int getFees() {
        return fees;
    }

    public String getStatus() {
        return status;
    }

}
