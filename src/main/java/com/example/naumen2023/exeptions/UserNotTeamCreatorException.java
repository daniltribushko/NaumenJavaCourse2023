package com.example.naumen2023.exeptions;

public class UserNotTeamCreatorException extends GlobalAppException{
    public UserNotTeamCreatorException(String userName) {
        super(403, "User " + userName + " is not the team creator");
    }
}
