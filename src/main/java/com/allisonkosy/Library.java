package com.allisonkosy;

import java.util.*;

public class Library {

    public static class Librarian {
        final private String name;
        final private Library library;

        public Librarian(String name, Library library) {
            this.name = name;
            this.library = library;
        }

        // Assuming only a librarian can fulfill requests
        public BookRequest fulfillRequest() {
            return library.fulfillRequest();
        }
    }
    // Keep the book in a map for faster searching
    private final HashMap<String, Book> books =  new HashMap<String, Book>();

    private final Librarian librarian;



    /**
     * Using a priority queue to store requests ensures that requests
     * made by Teachers are always at the front of the queue when polling
     * followed by requests made by senior students and then requests made by
     * junior students
     */
    private final PriorityQueue<BookRequest> requests = new PriorityQueue<>();

    /**
     * Assuming that there is supposed to be some sort of database of users
     */
    private final ArrayList<LibraryUser> usersDatabase = new ArrayList<>();

    // Assuming that we keep a list of borrowed books
    private final ArrayList<BookRequest> borrowed = new ArrayList<>();

    public Library() {
        librarian = new Librarian("Monica", this);
    }
    public static int randomQuantity(int max) {
        Random random = new Random();
        return  random.nextInt(max);
    }

    /**
     * Add a user from a csv row
     * @param line csv row
     * @param lineNumber csv line number
     * @return The new user created or null if an invalid role was provided
     * @throws RuntimeException if the row is invalid and cannot create a usee
     */
    public LibraryUser addUser(String line, int lineNumber) throws RuntimeException {

        String[] strings = line.split(",");

        if(strings.length < 4) {
           throw new RuntimeException("Invalid csv row on line " + lineNumber);

        }

       LibraryUser user = null;
        switch (strings[2]) {
            case "Senior Student":
                user = new Student(strings[0], LibraryUser.UserType.STUDENT_SENIOR, strings[3], strings[1], this);
                break;
            case "Junior Student":
                user = new Student(strings[0], LibraryUser.UserType.STUDENT_JUNIOR, strings[3], strings[1], this);
                break;
            case "Teacher":
                user = new Teacher(strings[0], strings[3], strings[1], this);
                break;
            default:
                App.logger.error("Invalid user role "+ strings[2] + " on line " + lineNumber);
                break;

        }
        if(user != null) {
            usersDatabase.add(user);
            App.logger.info(user.toString() + " added");
        }
        return  user;

    }

    /**
     * Used to query how many users are in the database
     * @return number of users in the database
     */
    public int userSize() {
        return usersDatabase.size();
    }

    private LibraryUser findUser(String userName) {
        // Simple search for a user by username
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
            // book is not in books hashmap
            App.logger.error("No book found with title: " + bookName);
            return "No such book "+ bookName;
        }

        if (!book.isAvailable()) {
            // book has is not currently in stock
            App.logger.info(bookName + " is unavailable");
            return "book taken";
        }
        // create a new request to be fulfilled later
        BookRequest request = new BookRequest(user,bookName);
        request.setBook(book);

        // add the request to the requests queue
        requests.add(request);

        App.logger.info(request);
        return "request added";
    }

    public String borrowBook(String userName, String bookName) {
        LibraryUser user = findUser(userName);
        if(user == null ) {
            // User is not in database
            App.logger.error("No user found with username: " + userName);
            return "invalid request";
        }

        return borrowBook(user, bookName);
    }

    /**
     * Prints all the users in the database
     */
    public void printAll() {
        int size = userSize();
        for (int i = 0; i < size; i++) {
            LibraryUser user = usersDatabase.get(i);
            System.out.println(i + " " + user + " " + user.getRole());
        }
    }

    public ArrayList<LibraryUser> getUsersDatabase() {
        return usersDatabase;
    }

    private BookRequest fulfillRequest() {
        if (requests.isEmpty()) {
            App.logger.info("No requests in queue");
            return null;
        }
        BookRequest request = requests.poll();
        request.fulfill();
        borrowed.add(request);
        return request;

    }

    /**
     * Add a book to the library using just a title
     * @param name title of the book
     * @return true if a new book was added
     *         false if book with same title already exists
     */
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

    /**
     * Add a book to the library
     * @param book Book to add
     * @return true if the book is a book not in library
     *         or false if library already has the book
     */
  public boolean addBook(Book book) {
        String name = book.getName();
        if(books.containsKey(name) ) {
            // if this is not a new book title then
            // add it to the books hashmap
            Book b = books.get(name);
            b.add();
            App.logger.info("Added more inventory of " + name + " quantity is now: " + b.getStock());
            return false;

        }

        book.setStock(1); // set the stock of new book to 1
        books.put(name, book);
        App.logger.info("Added book " + name + " to library");
        return true;


    }

    public HashMap<String, Book> getBooks() {
        return books;
    }

    /**
     * Get a user from the user database
     * @param index id of the user
     * @return LibraryUser if the id is in the database
     */
    public LibraryUser getUser(int index) {
        return usersDatabase.get(index);
    }

    public void printAllBooks() {
        Collection<Book> bookCollection = books.values();
        int index = 0;
        for (Book book : bookCollection) {
            System.out.println(index++ + " " + book.getName());
        }
    }


    private boolean checkIndex(int index) {
        int length = bookLength();
        return (index > -1 && length > index);
    }

    public int bookLength() {
        String[] strings = books.keySet().toArray(new String[0]);
        return strings.length;
    }
    public String getBookName(int index) {
        String[] strings = books.keySet().toArray(new String[0]);

        if(checkIndex(index))return strings[index];
        else throw new ArrayIndexOutOfBoundsException("No book found for selection");

    }

    public Librarian getLibrarian() {
        return librarian;
    }

    private BookRequest findBorrowed(String bookName) {
        int size  = borrowed.size();

        for (int i = 0; i < size; i++) {
            BookRequest request = borrowed.get(i);
            if(request.getBook().getName().equals(bookName)) {
                return request;
            }
        }
        return null;
    }

    public boolean returnBook(String userName, String bookName) {
        BookRequest request = findBorrowed(bookName);
        if(request == null) {
            App.logger.info(userName + " returned invalid book " + bookName);
            return false;
        }
        int len = borrowed.size();
       if(request.getUser().getUserName().equals(userName)) {
           request.getBook().add();
           App.logger.info(userName + " returned " + bookName);
           borrowed.remove(request);

       }
       else {
           App.logger.info(userName + " returned invalid book " + bookName);
           return false;
       }

        int len2 = borrowed.size();

        return len > len2;
    }
}
