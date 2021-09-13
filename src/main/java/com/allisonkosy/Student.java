package com.allisonkosy;

public class Student extends LibraryUser{
    private String password;
    protected Student(String userName, UserType userType, String className,  String password, Library library) {
        super(userName, userType, className, library);
        this.password = password;
    }

    @Override
    public String getRole() {
        int userType = getUserType();
        return userType == 3 ? "Junior Student" : "Senior Student";
    }
}
