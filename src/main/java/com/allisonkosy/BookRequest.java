package com.allisonkosy;

public class BookRequest implements Comparable<BookRequest> {
    private final LibraryUser user;
    private final Integer priority;
    private final String bookName;
    private Book book;

    public BookRequest(LibraryUser user, String bookName) {
        this.user = user;
        this.priority = user.getUserType();
        this.bookName = bookName;
    }

    public void setBook(Book book) {


        this.book = book;
    }

    public Book getBook() {

        return book;
    }

    public void fulfill() {
        book.borrow();
        App.logger.info(user.getUserName() + " has borrowed " + bookName);

    }

    public int getPriority() {
        return priority;
    }

    public int compareTo(BookRequest request) {
        Integer other = request.getPriority();

        return priority.compareTo(other);

    }

    @Override
    public String toString() {
        return "Book " + bookName +
                " is requested by " + user.getUserName();
    }
}
