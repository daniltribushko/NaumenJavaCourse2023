package com.example.naumen2023.exeptions;

public class TeamNotFoundException extends GlobalAppException{
    public TeamNotFoundException(Long id) {
        super(404, "team with " + id + " id not found");
    }
}
