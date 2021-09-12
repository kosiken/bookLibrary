package com.allisonkosy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

public class Library {
    private final HashMap<String, Book> books =  new HashMap<String, Book>();;

    private final PriorityQueue<BookRequest> requests = new PriorityQueue<>();
    private ArrayList<LibraryUser> usersDatabase = new ArrayList<>();

    public Library() {
//        this.books =



    }
    public static int randomQuantity(int max) {
        Random random = new Random();
        return  random.nextInt(max);
    }
    public LibraryUser addUser(String line, int lineNumber) throws RuntimeException {

        String[] strings = line.split(",");

        if(strings.length < 4) {
           App.logger.error("Invalid csv row on line " + lineNumber);
           return null;
        }

       LibraryUser user = null;
        switch (strings[2]) {
            case "Senior Student":
                user = new Student(strings[0], LibraryUser.UserType.STUDENT_SENIOR, strings[3], strings[1]);
                break;
            case "Junior Student":
                user = new Student(strings[0], LibraryUser.UserType.STUDENT_JUNIOR, strings[3], strings[1]);
                break;
            case "Teacher":
                user = new Teacher(strings[0], strings[3], strings[1]);
                break;
            default:
                App.logger.error("Invalid user role "+ strings[2] + " on line " + lineNumber);
                break;

        }
        if(user != null) usersDatabase.add(user);
        App.logger.info(user.toString() + " added");
        return  user;

    }
    public int userSize() {
        return usersDatabase.size();
    }

    private LibraryUser findUser(String userName) {
        for (LibraryUser user:
             usersDatabase) {
            if(user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
    private String  borrowBook(LibraryUser user, String bookName) {


        Book book = books.get(bookName);

        if(book == null){
            App.logger.error("No book found with title: " + bookName);
            return "No such book "+ bookName;
        }

        if (!book.isAvailable()) {
            App.logger.info(bookName + " is unavailable");
            return "book taken";
        }

        BookRequest request = new BookRequest(user,bookName);
        request.setBook(book);
        requests.add(request);

        App.logger.info(request);
        return "request added";
    }

    public String borrowBook(String userName, String bookName) {
        LibraryUser user = findUser(userName);
        if(user == null ) {
            App.logger.error("No user found with username: " + userName);
            return "invalid request";
        }

        return borrowBook(user, bookName);
    }

    public void printAll() {
        for (LibraryUser user:
            usersDatabase ) {
            System.out.println(user);
        }
    }

    public ArrayList<LibraryUser> getUsersDatabase() {
        return usersDatabase;
    }

    public BookRequest fulfillRequest() {
        if (requests.isEmpty()) {
            App.logger.info("No requests in queue");
            return null;
        }
        BookRequest request = requests.poll();
        request.fulfill();
        return request;

    }

    // add book
public boolean addBook(String name) {
        if(books.containsKey(name) ) {
            Book b = books.get(name);
            b.add();
            App.logger.info("Added more inventory of " + name + " quantity is now: " + b.getStock());
            return false;

        }
        Book book = new Book(name, "Lorem ipsum");
        return addBook(book);


}

  public boolean addBook(Book book) {
        String name = book.getName();
        if(books.containsKey(name) ) {
            Book b = books.get(name);
            b.add();
            App.logger.info("Added more inventory of " + name + " quantity is now: " + b.getStock());
            return false;

        }

        book.setStock(1);
        books.put(name, book);
        App.logger.info("Added book " + name + " to library");
        return true;


    }

    public HashMap<String, Book> getBooks() {
        return books;
    }



    //borrow book

    // return book



}
