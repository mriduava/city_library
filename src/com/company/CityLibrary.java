package com.company;

import java.util.*;

/**
 * <h1>Library Program</h1>
 * <p>Admin and Subscribers as User
 * where Admin can add Books and Subscriber
 * can Borrow or Return Books from Library</p>
 * @author Maruf Ahmed
 * @version 1.0.0
 * @since 2019.10.16
 */

public class CityLibrary{

    private static final int MAX_ADMINS = 2;
    private ArrayList<Admin> admins = new ArrayList<>(MAX_ADMINS);

    private ArrayList<Subscriber> subscribers = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<BorrowedBook> borrowedBooks = new ArrayList<>();
    private ArrayList<Float> rate = new ArrayList<>();

    private String libraryName = "";
    public CityLibrary(String libraryName){
        this.libraryName = libraryName;
    }

    /*public void promptMenu() {
        System.out.println("====================================\n" +
                           "   WELCOME TO " + libraryName);

        displayBooks();
        Scanner scan = new Scanner(System.in);
        String input;
        do {
            System.out.println("====================================\n" +
                               "PLEASE SELECT ONE ITEM FROM THE MENU\n" +
                               "====================================\n" +
                    "1. LIBRARIAN'S LOGIN\n" +
                    "2. ADD BOOKS\n" +
                    "3. SHOW BOOKS\n" +
                    "4. SORT BOOKS\n" +
                    "5. BECOME A SUBSCRIBER\n" +
                    "6. RESERVE BOOKS\n" +
                    "7. MY RESERVED BOOKS\n" +
                    "8. CANCEL RESERVATION\n" +
                    "9. ALL RESERVED BOOKS\n" +
                    "10. EXIT PROGRAM\n");

            input = scan.nextLine();
            switch (input) {
                case "1":
                    registerAdmin();
                    break;
                case "2":
                    addBook();
                    break;
                case "3":
                    showBooks();
                    break;
                case "4":
                    sortBooks();
                    break;
                case "5":
                    addSubscriber();
                    break;
                case "6":
                    borrowBook();
                    break;
                case "7":
                    showMyBorrowedBooks();
                    break;
                case "8":
                    returnBook();
                    break;
                case "9":
                    showAllBorrowedBooks();
                    break;
                case "10":
                case "exit":
                    System.out.println("PROGRAM IS SHUTTING DOWN");
                    break;
            }
        } while (!((input.equals("10")) || (input.equalsIgnoreCase("exit"))));
    }*/

    
    public void promptMenu() {
        System.out.println("====================================\n" +
                "* WELCOME TO " + libraryName + "  *");
        //To add books in array & to display
        displayBooks();

        MainMenu.MenuItems menuItems;
        do {
            menuItems = MainMenu.showMenuAndGetChoice();
            switch (menuItems) {
                case ADMIN_LOGIN_REG:
                    registerAdmin();
                    break;
                case ADD_BOOKS:
                    addBook();
                    break;
                case SHOW_BOOKS:
                    showBooks();
                    break;
                case SORT_BOOKS:
                    sortBooks();
                    break;
                case SUBSCRIBER_REGISTRATION:
                    addSubscriber();
                    break;
                case BORROW_BOOK:
                    borrowBook();
                    break;
                case MY_BORROWED_BOOKS:
                    showMyBorrowedBooks();
                    break;
                case RETURN_BOOK:
                    returnBook();
                    break;
                case ALL_BORROWED_BOOKS:
                    showAllBorrowedBooks();
                    break;
                case EXIT:
                    System.out.println("PROGRAM IS SHUTTING DOWN");
                    break;
            }
        } while (menuItems != menuItems.EXIT);
    }


    /**
     * Function to register Admin
     * Max 2 Admin can be added as MAX_ADMINS=2
     */
    public void registerAdmin(){
        System.out.println("ADMIN LOGIN/REGISTRATION" +
                         "\n========================");
        Scanner scan = new Scanner(System.in);
        String name, id;
        System.out.println("ADMIN USERNAME: ");
        name = scan.nextLine();
        boolean isNumber;
        do{
            System.out.println("PIN CODE (4 digit): ");
            String regex = "\\d+";
            id = scan.nextLine();
            if (id.length() == 4 && id.matches(regex)) {
                isNumber = true;
                int id2 = Integer.valueOf(id);
                Admin adminInfo = new Admin(name, id2);
                if (admins.size()== MAX_ADMINS){
                    System.out.println("Admin's position is full");
                }else {
                    admins.add(adminInfo);
                    adminInfo.welcomeMessage();
                }
            } else {
                isNumber = false;
                System.out.println("Please enter a 4 digit number...");
            }
        }while (!(isNumber));
    }

    /**
     * Not using this function
     * Just to check if "registerAdmin works or not
     */
    public void showAdmins(){
        for (int i = 0; i< admins.size(); i++){
            System.out.println("Name: " + admins.get(i).getName().toUpperCase() +
                    "\nID: " + admins.get(i).getId() +
                    "\n--------------------------");
        }
    }

    /**
     * It will add Books to the books array
     * This function will run before the display of Menuitems
     */
    public void displayBooks(){
        Book emil = new Book("Emil", "Astrid Lindgren", 2, 0.0f);
        Book matilda = new Book("Matilda", "Roald Dahl", 3, 0.0f);
        Book whiteTiger = new Book("Ladies Coupe", "Anita Nair", 0, 0.0f);
        Book skuld = new Book("Skuld", "Karin Alvtegen", 1, 0.0f);

        books.add(emil);
        books.add(matilda);
        books.add(whiteTiger);
        books.add(skuld);
    }

    /**
     * To create book object and to add in the books array
     */
    private void generateBookInfo(){
        Scanner input = new Scanner(System.in);
        String title, author;
        int quantity;
        float rating = 0.0f;
        System.out.println("Book's Title: ");
        title = input.nextLine();

        System.out.println("Book's Author: ");
        author = input.nextLine();

        Scanner input2 = new Scanner(System.in);
        boolean isNumber;
        do{
            System.out.println("Number of Items: ");
            if (input2.hasNextInt()){
                isNumber=true;
                quantity = input2.nextInt();
                Book bookInfo = new Book(title, author, quantity, rating);
                books.add(bookInfo);
                System.out.println(title.toUpperCase() + " has been successfully added.");

                //FileUtility.writeBooksList("booksList.ser", books);
               // FileUtility.saveObject("books.ser", books);

            }else {
                System.out.println("Please write a number...");
                isNumber = false;
                input2.next();
            }
        }while (!(isNumber));
    }

    /**
     * This function is created to call registerAdmin function
     * only Admin can add books, not other users
     */
    public void addBook(){
        System.out.println("ADD BOOK TO LIBRARY" +
                         "\n===================");
        if (admins.size() != 0){
            generateBookInfo();
        }else {
            System.out.println("TO ADD BOOKS, YOU'VE TO LOGIN" +
                    "\n----------------------------");
            registerAdmin();
            generateBookInfo();
        }
    }

    /**
     * To Show all the books
     */
    public void showBooks(){
        System.out.println("AVAILABLE BOOKS" +
                "\n===============");
        //SHOW BOOKS FROM ARRAY LIST
        for (Book book: books){
            System.out.printf("Title   : %s \nAuthor  : %s\nAvg Rate: %.1f\nQuantity: %s \n------------------\n",
                    book.getTitle().toUpperCase(),
                    book.getAuthor().toUpperCase(),
                    book.getRating(),
                    book.getQuantity());
        }
        //SHOW BOOKS FROM FILE
        /*ArrayList<Book> booksFile = (ArrayList<Book>)FileUtility.readBooksList("booksList.ser");
        System.out.println(booksFile.toString().replace("[", "").replace("]", ""));*/

        //List<Book> students = (List<Book>)FileUtility.loadObject("books.ser");
    }

    /**
     * To sort all books in the array
     * It can sort books by "title" or "author"
     */

    public void sortBooks(){
        System.out.println("SORT BOOKS" +
                "\n==========");
        Scanner scan = new Scanner(System.in);
        System.out.println("Please write a sorting method: " +
                "\n-----------------------------" +
                "\n  'TITLE' or 'AUTHOR'");
        String sortingMethod = scan.nextLine();
        switch (sortingMethod.toLowerCase()){
            case "title":
                Book.setSortBy(Book.SortBy.TITLE);
                Collections.sort(books);

                System.out.println("BOOKS SORTED BY TITLE\n" +
                        "---------------------");
                for(Book books: books){
                    System.out.println(books);
                }
                return;
            case "author":
                Book.setSortBy(Book.SortBy.AUTHOR);
                Collections.sort(books);

                System.out.println("BOOKS SORTED BY AUTHOR\n" +
                        "----------------------");
                for(Book books: books){
                    System.out.println(books);
                }
                return;
        }
    }

    /**
     * Boolean function existUsername  has been called here to avoid same username
     * It will also chek tf the pin is 4 digit
     */
    public void addSubscriber(){
        System.out.println("BECOME A SUBSCRIBER" +
                         "\n===================");
        Scanner scan = new Scanner(System.in);
        String name, id;

        boolean userName;
        do {
            System.out.println("SUBSCRIBER'S USERNAME: ");
            name = scan.nextLine();
            if (!existUsername(name)){
                boolean isNumber;
                do {
                    System.out.println("PIN CODE (4 digit): ");
                    String regex = "\\d+";
                    id = scan.nextLine();
                    if (id.length() == 4 && id.matches(regex)) {
                        int id2 = Integer.valueOf(id);
                        Subscriber subscriberInfo = new Subscriber(name, id2);
                        subscribers.add(subscriberInfo);
                        subscriberInfo.welcomeMessage();
                        break;
                    } else {
                        isNumber = false;
                        System.out.println("Please enter a 4 digit number...");
                    }
                } while (!(isNumber));
               break;
            }else {
                userName = false;
                System.out.println("This username is used.");
            }
        }while (!userName);

    }

    //SHOW SUBSCRIBERS
    public void showSubscribers(){
        for (Subscriber subscriber: subscribers){
            System.out.println("Name: " + subscriber.getName().toUpperCase() +
                    "\nID: " + subscriber.getId() +
                    "\n--------------------------");
        }
    }

    //BORROW BOOK
    public void borrowBook(){
        System.out.println("BORROW BOOK" +
                         "\n===========");
        Scanner input = new Scanner(System.in);
        System.out.println("SUBSCRIBER NAME: ");
        String subscriberName = input.nextLine();

        boolean foundSubscriber = false;
        for (Subscriber subscriber: subscribers){
            if ((subscriber.getName().toLowerCase()).equals(subscriberName.toLowerCase())){
                foundSubscriber = true;
                String name = subscriber.getName();
                System.out.println("BOOK TITLE TO BORROW: ");
                String bookTitle = input.nextLine();
                boolean foundBook = false;
                for (Book book: books){
                    if ((book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) && (book.getQuantity() > 0)){
                        foundBook = true;
                        BorrowedBook borrowedBook = new BorrowedBook(name, book.getTitle(), book.getAuthor(), book.getQuantity(), book.getRating());
                        if (!existBook(borrowedBook)){
                            borrowedBooks.add(borrowedBook);
                            System.out.println(bookTitle.toUpperCase() + " is now borrowed!\n");
                            book.setQuantity(book.getQuantity()-1);
                            break;
                        }else{
                            System.out.println("You have already borrowed this book.");
                        }
                    } else if ((book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) && (book.getQuantity() == 0)){
                        foundBook = true;
                        System.out.println("Sorry, this book is not available to borrow.");
                    }
                }
                if (!foundBook){
                    System.out.println("Sorry, We do not have this book.");
                }
            }
        }
        if (!foundSubscriber){
            System.out.println("To borrow a book...\n" +
                    "you've to be a subscriber.");
        }
    }

    //MY BORROWED BOOKS
    public void showMyBorrowedBooks(){
        System.out.println("MY BORROWED BOOKS" +
                         "\n=================");
        Scanner input = new Scanner(System.in);
        System.out.println("YOUR NAME: ");
        String subscriberName = input.nextLine();
        String bookTitle, bookAuthor;

        boolean found = false;
        for (BorrowedBook borrowedBooks : this.borrowedBooks){
            if ((borrowedBooks.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                found = true;
                bookTitle = borrowedBooks.getTitle();
                bookAuthor = borrowedBooks.getAuthor();
                System.out.println("Subscriber : " + borrowedBooks.getName().toUpperCase() +
                        "\nBook Title : " + bookTitle.toUpperCase() +
                        "\nBook Author: " + bookAuthor.toUpperCase() +
                        "\n-----------------------");
            }
        }
        if (!found){
            System.out.println("You've no borrowed books");
        }
    }

    //RETURN BOOK
    public void returnBook(){
        System.out.println("RETURN BOOK" +
                         "\n===========");
        Scanner input = new Scanner(System.in);
        System.out.println("YOUR NAME: ");
        String subscriberName = input.nextLine();

        boolean foundSubscriber = false;
        boolean foundBook = false;
        try {
            for (BorrowedBook borrowedBook : borrowedBooks){
                if ((borrowedBook.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                    foundSubscriber = true;
                    System.out.println("BOOK TITLE: ");
                    String bookTitle = input.nextLine();

                    for (BorrowedBook borrowedBook2 : borrowedBooks) {
                        if ((borrowedBook2.getName().toLowerCase()).equals(subscriberName.toLowerCase()) && bookTitle.toLowerCase().equals(borrowedBook2.getTitle().toLowerCase())) {
                            borrowedBooks.remove(borrowedBook2);
                            System.out.println(bookTitle.toUpperCase() + " has been canceled.");
                            foundBook = true;
                            for (Book book : books) {
                                if (book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) {
                                    book.setQuantity(book.getQuantity() + 1);

                                    Scanner input2 = new Scanner(System.in).useLocale(Locale.US);
                                    System.out.println("Rate the book (Optional)" +
                                            "\nEnter a number 0.1 - 5.0");
                                    String str = input2.nextLine();
                                    if (str.isEmpty()) {
                                        System.out.println("You've skipped rating.");
                                        break;
                                    } else {
                                        float rate = Float.parseFloat(str);
                                        if (rate > 0 && rate <= 5) {
                                            book.setRating(avgRating(rate));
                                            System.out.println("Thanks for rating.");
                                            break;
                                        } else {
                                            System.out.println("No rating added for out of limit.");
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }

                    }
                    if (!foundBook){
                        System.out.println("Yod did not borrow this book.");
                        break;
                    }
                }
            }
        }catch (Exception e){
            //System.out.println(e);
        }

        if (!foundSubscriber){
            System.out.println("Subscriber not found.");
        }
    }

    //SHOW ALL BORROWED BOOKS
    public void showAllBorrowedBooks(){
        System.out.println("ALL BORROWED BOOKS" +
                         "\n==================");
        for (BorrowedBook borrowedBook : borrowedBooks){
            System.out.println("Name  : " + borrowedBook.getName().toUpperCase() +
                    "\nTitle : " + borrowedBook.getTitle().toUpperCase() +
                    "\nAuthor: " + borrowedBook.getAuthor().toUpperCase() +
                    "\n--------------------------");
        }
    }

    //CHECK IF THE USERNAME EXIST IN THE ARRAY LIST
    public boolean existUsername(String name){
        for (Subscriber subscriber: subscribers){
            if (subscriber.getName().equals(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    //CHECK IF THE BOOK EXIST IN THE ARRAY LIST
    public boolean existBook(BorrowedBook borrowedBook){
        for (BorrowedBook resBook: borrowedBooks){
            if (resBook.getTitle().equals(borrowedBook.getTitle()) && resBook.getName().equals(borrowedBook.getName())){
                return true;
            }
        }
        return false;
    }

    //CALCULATE AVERAGE RATING
    public float avgRating(float num){
        rate.add(num);
        float total = 0.0f;
        for (float r: rate){
            total += r;
        }
        float avgRate = total/(float) rate.size();
        return avgRate;
    }

}