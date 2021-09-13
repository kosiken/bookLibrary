package com.allisonkosy;

public abstract class LibraryUser {

    protected LibraryUser(String userName, UserType userType, String className, Library library) {
        this.userName = userName;
        this.userType = userType;
        this.className = className;
        this.library = library;
    }

    enum UserType {
        STUDENT_JUNIOR,
        STUDENT_SENIOR,
        TEACHER,


    }
    private String userName;
    private final UserType userType;
    private String className;
    private final Library library;

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

    public boolean borrowBook(String bookName){
      String response =  library.borrowBook(userName, bookName);
      return response.equals("request added");
    }


    @Override
    public String toString() {
        return userName;
    }

    public abstract String getRole();


}
