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
            "lion,0000,Junior Student,london\n" +
            "Thora.Rempel@hotmail.com,0000,Junior Student,london\n" +
            "Elvis_Legros95@hotmail.com,0000,Teacher,london";

    public static final String[] titles = {"Fifty shades of grey", "Cinderella", "Robin Hood", "Shapes for babies",
            "Pride and Prejudice", "The God's are to blame", "Martha", "Fifty shades of grey", "Cinderella", "Robin Hood", "Shapes for babies",
            "Pride and Prejudice", "The God's are to blame", "Martha","Fifty shades of grey", "Cinderella", "Robin Hood", "Shapes for babies",
            "Pride and Prejudice", "The God's are to blame", "Martha"};



    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        logger.info("creating library");


        Library library= new Library();

        initializeLibrary(library);


        login(library, scanner);

        logger.info("Logged in as " + currentUser + " role -> " + currentUser.getRole());

        createRequest(library, scanner);




    }

    public static void initializeLibrary(Library library) {
        logger.info("Initializing library");
        addBooks(library);
        addUsers(library);
        createRandomRequests(library, 5);
        logger.info("Library initialized with 5 random requests in queue");

    }

    public static void login(Library library, Scanner scanner) {
        int size = library.userSize();
        library.printAll();
         int choice = -1;

        System.out.println("Enter a number between 0 and " + (size - 1) + " to select a user from the database" );
        System.out.println("Enter " + size + " to create a new user ");
      while (choice == -1) {
          try {

              choice = scanner.nextInt();
              logger.info("user entered " + choice);
          }
          catch (InputMismatchException e) {
              logger.error(e);
              scanner.nextLine();
          }
      }
      if(choice >= size) {
          scanner.nextLine();
          addUser(library, scanner);
      }
      else {
          currentUser = library.getUser(choice);

      }
    }

    public static void createRequest(Library library, Scanner scanner) {
        System.out.println("Hello " + currentUser);
        System.out.println("here is a look at the current selection of books");
        library.printAllBooks();
        int size = library.bookLength();
        String bookName = "Invalid book";
        System.out.println("Enter a number between 0 and " +  (size - 1) + " to select a book");
        int choice = -1;
        while (choice == -1) {
            try {

                choice = scanner.nextInt();
                logger.info("user entered " + choice);
                bookName = library.getBookName(choice);
            }
            catch (InputMismatchException e) {
                logger.error(e);

                scanner.nextLine();
            }
            catch (ArrayIndexOutOfBoundsException e) {
                logger.error(e);
                System.out.println("Invalid option");
                choice = -1;

                scanner.nextLine();
            }

        }
        logger.info("User selected " + bookName );

        library.borrowBook(currentUser.getUserName(), bookName);
    }




    public static void addUsers(Library library) {

        String[] lines = csv.split("\n");
        int len = lines.length;
        for (int i = 0; i < len; i++) {
            library.addUser(lines[i],i);
        }
    }

    public static void createRandomRequests(Library library, int number) {
        ArrayList<LibraryUser> users = library.getUsersDatabase();
        int max = users.size();
        int titleLength = titles.length;
        for (int i = 0; i < number; i++) {
           int index = Library.randomQuantity(max);
            LibraryUser user = users.get(index);
            library.borrowBook(user.getUserName(), titles[i % titleLength]);


        }
    }


    public static void addBooks(Library library) {
        for (String book: titles) {
            library.addBook(book);
        }
    }
    public static void addUser(Library library, Scanner scanner) {
        String[] questions = {
                "What is your username: ",
                "What is your password: ",
                "What is your role\n 1-> Teacher, 2-> Senior Student, 3-> Junior Student: ",
                "What is your class: "
        };

        int index = 0;

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