package com.example.admintranis;

public class ComplainModel {
    String reason;
    String issue;
    String description;
    String name;
    String email;


    ComplainModel(){

    }

    public ComplainModel(String reason, String issue, String description,String name,String email) {
        this.name=name;
        this.email=email;
        this.reason = reason;
        this.issue = issue;
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

