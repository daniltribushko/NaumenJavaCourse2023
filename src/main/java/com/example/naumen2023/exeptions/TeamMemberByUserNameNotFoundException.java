package com.example.naumen2023.exeptions;

public class TeamMemberByUserNameNotFoundException extends GlobalAppException{
    public TeamMemberByUserNameNotFoundException(String userName) {
        super(404, "Team member " + userName + " not found");
    }
}
