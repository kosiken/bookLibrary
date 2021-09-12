package com.allisonkosy;

public class Teacher extends LibraryUser{
    private String password;
    protected Teacher(String userName,  String className, String password) {
        super(userName, UserType.TEACHER, className);
        this.password = password;
    }

    boolean validatePassword(String password) {
      return password == this.password;
    }

}
