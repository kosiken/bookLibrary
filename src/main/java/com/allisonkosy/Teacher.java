package com.allisonkosy;

public class Teacher extends LibraryUser{
    private String password;
    protected Teacher(String userName,  String className, String password, Library library) {
        super(userName, UserType.TEACHER, className, library);
        this.password = password;
    }

    boolean validatePassword(String password) {
      return password == this.password;
    }

    @Override
    public String getRole() {
        return "Teacher";
    }
}
