package com.allisonkosy;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{



    @Test
    public void shouldAddUser() {
        // test method to add a new user
        Library library= new Library();
        String s = "Ruth14@yahoo.com,0000,Senior Student,london";
       LibraryUser user = library.addUser(s, 0);
       assertNotNull(user);
    }

    @Test
    public void shouldNotAddUser() {
        // should not add a new user with invalid credentials
        Library library= new Library();
        String s = "Ruth14@yahoo.com,0000,Senior St,london";
        LibraryUser user = library.addUser(s, 0);
        assertNull(user);
    }


    @Test
    public void shouldAddNewBook() {
        // test add book method
        Library library= new Library();
       boolean bool = library.addBook("Lauren");
       assertTrue(bool);
       assertEquals(1, library.bookLength());


    }

    @Test
    public void shouldAddBook() {
        // test adding multiple copies of the same book
        Library library= new Library();
        boolean bool;
        library.addBook("Lauren");
        bool = library.addBook("Lauren");
        Collection<Book> collection = library.getBooks().values();
        assertFalse(bool);
       Book book = (Book) collection.toArray()[0];
        assertTrue((collection.size() == 1));
        assertEquals(2,book.getStock());


    }

    @Test
    public void shouldBorrowBook() {
        // test borrow book method
        Library library= new Library();
       library.addBook("Lauren");
        library.addUser("Ruth14@yahoo.com,0000,Senior Student,london", 0);
       String ans=  library.borrowBook("Ruth14@yahoo.com", "Lauren");
       assertEquals("request added", ans);
    }


    @Test
    public void shouldNotBorrowBookInvalidUser() {
        // should not borrow book to a user not in database
        Library library= new Library();
        library.addBook("Lauren");
        library.addUser("Ruth14@yahoo.com,0000,Senior Student,london", 0);
        String ans=  library.borrowBook("Ruth14@yaho.com", "Lauren");
        assertEquals("invalid request", ans);
    }

    @Test
    public void shouldNotBorrowBookInvalidBookName() {
        // should not create request for a book not in library
        Library library= new Library();
        library.addBook("Lauren");
        library.addUser("Ruth14@yahoo.com,0000,Senior Student,london", 0);
        String ans=  library.borrowBook("Ruth14@yahoo.com", "Lorraine");
        assertEquals("No such book Lorraine", ans);
    }

    @Test
    public void shouldNotBorrowBookNoStock() {
        // should not borrow book that is out of stock
        Library library= new Library();
        library.addBook("Lauren");
        Book book = library.getBooks().get("Lauren");
        book.setStock(0); // set the number of books in the library to 0
        library.addUser("Ruth14@yahoo.com,0000,Senior Student,london", 0);
        String ans=  library.borrowBook("Ruth14@yahoo.com", "Lauren");
        assertEquals("book taken", ans);
    }

    @Test
    public void shouldFulfillRequest() {
        // test fulfill request method
        Library library= new Library();
       App.addBooks(library);
       App.addUsers(library);
        library.borrowBook("Ruth14@yahoo.com", "Cinderella");
        Library.Librarian librarian = library.getLibrarian();
        BookRequest request = librarian.fulfillRequest();
        assertNotNull(request);
    }

    @Test
    public void shouldNotFulfillRequest() {
        // should not fulfill the same request twice
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);
        library.borrowBook("Ruth14@yahoo.com", "Cinderella");
        Library.Librarian librarian = library.getLibrarian();
        librarian.fulfillRequest();
        BookRequest request = librarian.fulfillRequest();
        assertNull(request);



    }

    @Test
    public void shouldOrderByPriority() {
        // test priority ordering of requests
        String[] users = {
               "Juana.Swaniawski59@hotmail.com", // teacher,
               "Ruth14@yahoo.com", // senior student,
                "Crystel_Rodriguez98@hotmail.com" // junior student
        };
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);
        for (int i = 2; i > -1; i--) {
            library.borrowBook(users[i], "Cinderella");

        }
        Library.Librarian librarian = library.getLibrarian();
        BookRequest request = librarian.fulfillRequest();
        BookRequest request1 = librarian.fulfillRequest();
        BookRequest request2 = librarian.fulfillRequest();
        assertEquals(1, request.getPriority());
        assertEquals(2, request1.getPriority());
        assertEquals(3, request2.getPriority());
    }



    @Test
    public void shouldGetBookName() {
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);

       String book = library.getBookName(6);
       assertEquals("Cinderella", book);

    }

    @Test
    public void shouldThrow(){
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);

        String book = null;
        try {
            book = library.getBookName(9);

        }
        catch (ArrayIndexOutOfBoundsException e) {
            // should not assign book
        }
        assertNull(book);
    }

    @Test
    public void shouldReturnBook() {
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);

        library.borrowBook("lion", "Cinderella");
        library.getLibrarian().fulfillRequest();

        boolean bool = library.returnBook("lion", "Cinderella");

        assertTrue(bool);

    }


    @Test
    public void shouldNotReturnBookInvalidUser() {
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);

        library.borrowBook("lion", "Cinderella");
        library.getLibrarian().fulfillRequest();

        boolean bool = library.returnBook("lio", "Cinderella");

        assertFalse(bool);

    }

    @Test
    public void shouldNotReturnBookInvalidRequest() {
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);

        library.borrowBook("lion", "Cinderella");
        library.getLibrarian().fulfillRequest();

        boolean bool = library.returnBook("lion", "Cinderella2");

        assertFalse(bool);

    }

    @Test
    public void shouldNotReturnBookUnfulfilledRequest() {
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);

        library.borrowBook("lion", "Cinderella");


        boolean bool = library.returnBook("lion", "Cinderella");

        assertFalse(bool);

    }



}
