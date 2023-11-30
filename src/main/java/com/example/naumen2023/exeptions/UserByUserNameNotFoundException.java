package com.example.naumen2023.exeptions;

public class UserByUserNameNotFoundException extends GlobalAppException{
    public UserByUserNameNotFoundException(String userName) {
        super(404, "User " + userName + " not found");
    }
}
