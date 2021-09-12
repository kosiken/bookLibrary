package com.allisonkosy;

import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{



    @Test
    public void shouldAddUser() {
        Library library= new Library();
        String s = "Ruth14@yahoo.com,0000,Senior Student,london";
       LibraryUser user = library.addUser(s, 0);
       assertNotNull(user);
    }

    @Test
    public void shouldNotAddUser() {
        Library library= new Library();
        String s = "Ruth14@yahoo.com,0000,Senior St,london";
        LibraryUser user = library.addUser(s, 0);
        assertNull(user);
    }


    @Test
    public void shouldAddNewBook() {
        Library library= new Library();
       boolean bool = library.addBook("Lauren");
       assertTrue(bool);


    }

    @Test
    public void shouldAddBook() {
        Library library= new Library();
        boolean bool = library.addBook("Lauren");
        bool = library.addBook("Lauren");
        Collection<Book> collection = library.getBooks().values();
        assertFalse(bool);
       Book book = (Book) collection.toArray()[0];
        assertTrue((!bool && collection.size() == 1));
        assertEquals(2,book.getStock());


    }

    @Test
    public void shouldBorrowBook() {
        Library library= new Library();
        boolean bool = library.addBook("Lauren");
        library.addUser("Ruth14@yahoo.com,0000,Senior Student,london", 0);
       String ans=  library.borrowBook("Ruth14@yahoo.com", "Lauren");
       assertEquals("request added", ans);
    }


    @Test
    public void shouldNotBorrowBookInvalidUser() {
        Library library= new Library();
        boolean bool = library.addBook("Lauren");
        library.addUser("Ruth14@yahoo.com,0000,Senior Student,london", 0);
        String ans=  library.borrowBook("Ruth14@yaho.com", "Lauren");
        assertEquals("invalid request", ans);
    }

    @Test
    public void shouldNotBorrowBookInvalidBookName() {
        Library library= new Library();
        boolean bool = library.addBook("Lauren");
        library.addUser("Ruth14@yahoo.com,0000,Senior Student,london", 0);
        String ans=  library.borrowBook("Ruth14@yahoo.com", "Lorraine");
        assertEquals("No such book Lorraine", ans);
    }

    @Test
    public void shouldNotBorrowBookNoStock() {
        Library library= new Library();
        boolean bool = library.addBook("Lauren");
        Book book = library.getBooks().get("Lauren");
        book.setStock(0);
        library.addUser("Ruth14@yahoo.com,0000,Senior Student,london", 0);
        String ans=  library.borrowBook("Ruth14@yahoo.com", "Lauren");
        assertEquals("book taken", ans);
    }

    @Test
    public void shouldFufillRequest() {
        Library library= new Library();
       App.addBooks(library);
       App.addUsers(library);
        library.borrowBook("Ruth14@yahoo.com", "Cinderella");

        BookRequest request = library.fulfillRequest();
        assertNotNull(request);



    }

    @Test
    public void shouldNotFufillRequest() {
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);
        library.borrowBook("Ruth14@yahoo.com", "Cinderella");
        library.fulfillRequest();
        BookRequest request = library.fulfillRequest();
        assertNull(request);



    }
 
    @Test
    public void shouldOrderByPriority() {
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

        BookRequest request = library.fulfillRequest();
        BookRequest request1 = library.fulfillRequest();
        BookRequest request2 = library.fulfillRequest();

        assertEquals(1, request.getPriority());
        assertEquals(2, request1.getPriority());
        assertEquals(3, request2.getPriority());





    }




}
