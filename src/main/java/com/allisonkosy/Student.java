package com.allisonkosy;

public class Student extends LibraryUser{
    private String password;
    protected Student(String userName, UserType userType, String className,  String password) {
        super(userName, userType, className);
        this.password = password;
    }
    boolean validatePassword(String password) {
        return password == this.password;
    }
}
