package com.allisonkosy;

public abstract class LibraryUser {

    protected LibraryUser(String userName, UserType userType, String className) {
        this.userName = userName;
        this.userType = userType;
        this.className = className;
    }

    enum UserType {
        STUDENT_JUNIOR,
        STUDENT_SENIOR,
        TEACHER,


    }
    private String userName;
    private final UserType userType;
    private String className;

    public String getClassName() {
        return className;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserType() {
        if(userType == UserType.STUDENT_JUNIOR) {
            return 3;
        }
        if(userType == UserType.STUDENT_SENIOR) {
            return 2;
        }
        else return 1;
    }


    @Override
    public String toString() {
        return userName;
    }


}
