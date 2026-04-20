package service;

import dao.*;
import model.Book;
import java.util.Scanner;

public class LibraryService {

    Scanner sc = new Scanner(System.in);
    BookDAO bookDAO = new BookDAO();
    UserDAO userDAO = new UserDAO();
    TransactionDAO transactionDAO = new TransactionDAO();

    public void start() {
        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Add User");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.println();

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    bookDAO.addBook(new Book(title, author));
                    break;

                case 2:
                    bookDAO.viewBooks();
                    break;

                case 3:
                    sc.nextLine();
                    System.out.print("User Name: ");
                    String userName = sc.nextLine();

                    int userId = userDAO.addUser(userName);
                    System.out.println("User added! User ID: " + userId);
                    break;

                case 4:
                    System.out.print("Book ID: ");
                    int bId = sc.nextInt();

                    System.out.print("User ID: ");
                    int uId = sc.nextInt();

                    int transactionId = transactionDAO.issueBook(bId, uId);
                    System.out.println("Book issued! Transaction ID: " + transactionId);
                    break;

                case 5:
                    System.out.print("Transaction ID: ");
                    int tId = sc.nextInt();
                    transactionDAO.returnBook(tId);
                    break;

                case 6:
                    return;
            }
        }
    }
}