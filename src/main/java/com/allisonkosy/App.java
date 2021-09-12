package com.allisonkosy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class App {
    public static final Logger logger = LogManager.getLogger(App.class);
    public static LibraryUser currentUser = null;
    public static final String csv = "Ruth14@yahoo.com,0000,Senior Student,london\n" +
            "Juana.Swaniawski59@hotmail.com,0000,Teacher,london\n" +
            "Joany_Wiegand48@yahoo.com,0000,Senior Student,london\n" +
            "Georgette.Botsford24@hotmail.com,0000,Senior Student,london\n" +
            "Ernestine.Wyman@hotmail.com,0000,Senior Student,london\n" +
            "Crystel_Rodriguez98@hotmail.com,0000,Junior Student,london\n" +
            "Kamren.Zboncak@yahoo.com,0000,Junior Student,london\n" +
            "Caesar9@gmail.com,0000,Junior Student,london\n" +
            "Cody.Windler@hotmail.com,0000,Junior Student,london\n" +
            "Rhea76@yahoo.com,0000,Junior Student,london\n" +
            "Keyshawn67@yahoo.com,0000,Teacher,london\n" +
            "Peyton.Hamill@yahoo.com,0000,Teacher,london\n" +
            "Alexandra.Johns@yahoo.com,0000,Junior Student,london\n" +
            "Thora.Rempel@hotmail.com,0000,Junior Student,london\n" +
            "Elvis_Legros95@hotmail.com,0000,Teacher,london";

    public static final String[] titles = {"Fifty shades of grey", "Cinderella", "Robin Hood", "Shapes for babies",
            "Pride and Prejudice", "The God's are to blame", "Martha", "Fifty shades of grey", "Cinderella", "Robin Hood", "Shapes for babies",
            "Pride and Prejudice", "The God's are to blame", "Martha","Fifty shades of grey", "Cinderella", "Robin Hood", "Shapes for babies",
            "Pride and Prejudice", "The God's are to blame", "Martha"};



    public static void main( String[] args ) {
        logger.info("creating library");

        String[] users = {
                "Juana.Swaniawski59@hotmail.com", // teacher,
                "Ruth14@yahoo.com", // senior student,
                "Crystel_Rodriguez98@hotmail.com" // junior student
        };
        Library library= new Library();
        App.addBooks(library);
        App.addUsers(library);
//        library.borrowBook("Ruth14@yahoo.com", "Cinderella");
//        library.fulfillRequest();
//        BookRequest request = library.fulfillRequest();
//        assertNull(request);

        for (int i = 2; i > -1; i--) {
            library.borrowBook(users[i], "Cinderella");

        }

        BookRequest request = library.fulfillRequest();
        BookRequest request1 = library.fulfillRequest();
        BookRequest request2 = library.fulfillRequest();

        StringBuilder s = new StringBuilder();
        s.append(request.getPriority() + request.toString())
                .append("\n")
                .append(request1.getPriority() + request1.toString())
                .append("\n")
                .append(request2.getPriority() + request2.toString());

        System.out.println(s);



    }

    public static void addUsers(Library library) {
        String[] lines = csv.split("\n");
        int len = lines.length;
        for (int i = 0; i < len; i++) {
            library.addUser(lines[i],i);
        }
    }

    public static void createRandomRequests(Library library, int number) {
        for (int i = 0; i < number; i++) {

        }
    }


    public static void addBooks(Library library) {
        for (String book: titles) {
            library.addBook(book);
        }
    }
    public static void addUser(Library library) {
        String[] questions = {
                "What is your username: ",
                "What is your password: ",
                "What is your role\n 1-> Teacher, 2-> Senior Student, 3-> Junior Student: ",
                "What is your class: "
        };

        int index = 0;
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();

        while (index < 4) {
            System.out.print(questions[index]);
            String s = "";
            if(index == 2) {
                try {
                    String[] choices = {
                            "Teacher",
                            "Senior Student",
                            "Junior Student",

                    };

                   Integer choice = Integer.parseInt(scanner.nextLine());
                   s = (choices[choice - 1]);

                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid option");
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid option");
                    continue;
                }
            }
           else s = scanner.nextLine();
           if(index == 1 && s.length() < 4) {
               System.out.println("Invalid password length");
               continue;
           }
            index++;
            stringBuilder.append(s);
            if(index < 4) stringBuilder.append(',');
        }

       currentUser =  library.addUser(stringBuilder.toString(), 0);
    }


}