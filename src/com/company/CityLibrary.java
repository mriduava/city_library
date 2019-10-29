package com.company;

import java.nio.file.*;
import java.text.NumberFormat;
import java.util.*;

/**
 * <h1>Library Program</h1>
 * <p>Admin and Subscribers as Users, where only Admin can add Books
 * and Subscriber can Borrow or Return Books.</p>
 * Users can see all available books and sort books by 'title' or 'author'.
 * If a subscriber borrow a book, the book's quantity will be subtracted,
 * and by returning back the quantity will be added again.
 * While returning back the book, subscribes can also add 'rating' which is a float
 * number (0.1 to 5.0).  Rating book is optional, that means the user cna skip
 * that option just by pressing enter.
 * @author Maruf Ahmed
 * @version 1.0.1
 * @since 2019.10.16
 */

public class CityLibrary{

    private static final int MAX_ADMINS = 2;
    private ArrayList<Admin> admins = new ArrayList<>(MAX_ADMINS);

    private ArrayList<Subscriber> subscribers = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<BorrowedBook> borrowedBooks = new ArrayList<>();
    private ArrayList<Float> rate = new ArrayList<>();
    private ArrayList<String> loginData = new ArrayList<>();

    //Just to color the console texts
    public static final String RED_BOLD = "\033[1;31m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    /**
     * CityLibrary constructor
     */
    private String libraryName = "";
    public CityLibrary(String libraryName){
        this.libraryName = libraryName;
    }

    /**
     * This function will display the Menu Items.
     * Each menu-item will call its specific function to execute by "switch" method.
     * Before displaying menu-items this function calls other functions, for example
     * 'addBooksToArray' function will fill the 'books' array with pre-defined Book objects.
     *
     * To avoid "NoSuchFileException", files are created before Menu-items' function executes.
     * All files will be loaded at the beginning and will be saved before the Program shut down.
     * So users can retrieve data after restart the program.
     */
    public void promptMenu() {
        System.out.println("====================================\n" +
                ANSI_BLUE+ "* WELCOME TO " + libraryName + "  *" + ANSI_RESET);


        //To add books in array & to display
        displayBooks();

        Path path1 = Paths.get("books.ser");
        Path path2 = Paths.get("br_books.ser");
        Path path3 = Paths.get("subscribers.ser");
        if (!Files.exists(path1) && !Files.exists(path2) && !Files.exists(path3)){
            FileUtility.saveObject("books.ser", books);
            FileUtility.saveObject("br_books.ser", borrowedBooks);
            FileUtility.saveObject("subscribers.ser", subscribers);
        }
        ArrayList<Book> booksFile = (ArrayList<Book>)FileUtility.loadObject("books.ser");
        ArrayList<BorrowedBook> brBooksFile = (ArrayList<BorrowedBook>)FileUtility.loadObject("br_books.ser");
        ArrayList<Subscriber> subscribersFile = (ArrayList<Subscriber>)FileUtility.loadObject("subscribers.ser");
        books = booksFile;
        borrowedBooks = brBooksFile;
        subscribers = subscribersFile;

        //To display the user name who is recently logged in or has registered
        loggedinUser();
        MainMenu.MenuItems menuItems;
        do {
            loggedinUser();
            menuItems = MainMenu.showMenuAndGetChoice();
            while (admins.size()>0){
                for (MainMenu.MenuItems item: MainMenu.MenuItems.values()){
                    if (item == MainMenu.MenuItems.ADD_BOOKS){
                    }
                }
                    addBook();
            }

            switch (menuItems) {
                case ADMIN_REGISTRATION:
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
                case SUBSCRIBER_LOGIN:
                    loginSubscriber();
                    break;
                case SUBSCRIBER_REGISTRATION:
                    registerSubscriber();
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
                    FileUtility.saveObject("books.ser", books);
                    FileUtility.saveObject("subscribers.ser", subscribers);
                    FileUtility.saveObject("br_books.ser", borrowedBooks);
                    System.out.println(RED_BOLD +"PROGRAM IS SHUTTING DOWN"+ ANSI_RESET);
                    break;
            }
        } while (menuItems != menuItems.EXIT);
    }


    /**
     * Function to register Admin
     * Maximum 2 Admins can be added, as it is predeclared as MAX_ADMINS=2
     * "Do-while" loop is used to force the user to enter a 4 digit number.
     * If admin registration is successful, it will print a welcome message.
     */
    public void registerAdmin(){
        System.out.println("ADMIN REGISTRATION" +
                         "\n==================");
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
                    System.out.println(ANSI_PURPLE + "Admin's position is full" + ANSI_RESET);
                }else {
                    admins.add(adminInfo);
                    System.out.println(ANSI_GREEN + adminInfo.welcomeMessage() + ANSI_RESET);
                }
            } else {
                isNumber = false;
                System.out.println(ANSI_RED + "Please enter a 4 digit number..." + ANSI_RESET);
            }
        }while (!(isNumber));
    }

    /**
     * NOT USED
     * Just to check if "registerAdmin" function works
     * Function will print all registered Admins
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
        Book matilda = new Book("Matilda", "Roald Dahl", 0, 0.0f);
        Book ladiesCope = new Book("Ladies Coupe", "Anita Nair", 2, 0.0f);
        Book hunden = new Book("Hunden", "Kerstin Ekman", 3, 0.0f);

        books.add(matilda);
        books.add(emil);
        books.add(ladiesCope);
        books.add(hunden);
    }

    /**
     * To create book object and to add in the book's array
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
                FileUtility.saveObject("books.ser", books);
                System.out.println(title.toUpperCase() + ANSI_GREEN + " has been successfully added." + ANSI_RESET);
            }else {
                System.out.println(ANSI_RED + "Please write a number..." + ANSI_RESET);
                isNumber = false;
                input2.next();
            }
        }while (!(isNumber));
    }

    /**
     * Only Admin can add books, not other users
     * To force the user to register "registerAdmin" function has been called
     */
    public void addBook(){
        System.out.println("ADD BOOK TO LIBRARY" +
                         "\n===================");
        if (admins.size() != 0){
            generateBookInfo();
        }else {
            System.out.println(ANSI_PURPLE + "To add books please register first..." + ANSI_RESET +
                    "\n----------------------------");
            registerAdmin();
            generateBookInfo();
        }
    }

    /**
     * To Print all the books from Array.
     * "toString" method is used to print all the books
     */
    public void showBooks(){
        System.out.println("AVAILABLE BOOKS" +
                "\n===============");
        for (Book book: books){
            System.out.println(ANSI_BLUE + book + ANSI_RESET);
        }
    }

    /**
     * To sort all books in the array
     * It can sort books by "title" or "author"
     */
    public void sortBooks(){
        System.out.println("SORT BOOKS" +
                "\n==========");
        Scanner scan = new Scanner(System.in);
        System.out.println(ANSI_BLUE + "Please write a sorting method: " + ANSI_RESET +
                "\n-----------------------------" +
                "\n  'TITLE' or 'AUTHOR'");
        String sortingMethod = scan.nextLine();
        switch (sortingMethod.toLowerCase()){
            case "title":
                Book.setSortBy(Book.SortBy.TITLE);
                Collections.sort(books);

                System.out.println(ANSI_GREEN + "BOOKS SORTED BY TITLE" + ANSI_RESET +
                        "\n---------------------");
                for(Book books: books){
                    System.out.println(ANSI_BLUE + books + ANSI_RESET);
                }
                return;
            case "author":
                Book.setSortBy(Book.SortBy.AUTHOR);
                Collections.sort(books);

                System.out.println(ANSI_GREEN + "BOOKS SORTED BY AUTHOR" + ANSI_RESET +
                        "\n----------------------");
                for(Book books: books){
                    System.out.println(ANSI_BLUE + books + ANSI_RESET);
                }
                return;
        }
    }

    /**
     * Boolean function 'existUsername'  has been called here to avoid the
     * creation of the same username.
     * It will also check tf the pincode is a 4 digit number.
     */
    public void registerSubscriber(){
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
                        loginData.add(name);
                        System.out.println(ANSI_GREEN + subscriberInfo.welcomeMessage() + ANSI_RESET);
                        break;
                    } else {
                        isNumber = false;
                        System.out.println(ANSI_RED + "Please enter a 4 digit number..." + ANSI_RESET);
                    }
                } while (!(isNumber));
               break;
            }else {
                userName = false;
                System.out.println(ANSI_RED + "This username is used." + ANSI_RESET);
            }
        }while (!userName);
    }

    /**
     * Subscriber login
     * TODO
     */
    public void loginSubscriber(){
        System.out.println("SUBSCRIBER LOGIN" +
                         "\n================");
        Scanner scan = new Scanner(System.in);
        String name, id;
            System.out.println("ENTER THE USERNAME: ");
            name = scan.nextLine();
            if (existUsername(name)){
                boolean isNumber;
                int tryCount = 0;
                do {
                    System.out.println("PIN CODE (4 digit): ");
                    String regex = "\\d+";
                    id = scan.nextLine();
                    if (id.length() == 4 && id.matches(regex)) {
                        int id2 = Integer.parseInt(id);
                        if (existUser(name, id2)){
                            loginData.add(name);
                            System.out.println(ANSI_GREEN + "Hello " + name.toUpperCase() + " !" + ANSI_RESET);
                        }else {
                            System.out.println(ANSI_PURPLE + "Pincode didn't match." + ANSI_RESET);
                        }
                        break;
                    } else {
                        isNumber = false;
                        tryCount++;
                        System.out.println(ANSI_RED + "Please enter a 4 digit number..." + ANSI_RESET);
                        if (tryCount == 2){
                            System.out.println("Try again.");
                        }
                    }
                } while (!(isNumber) && tryCount<=2);
            }else {
                System.out.println(ANSI_RED + "You're not a Subscriber\nPlease register" + ANSI_RESET);
            }
    }

    /**
     * NOT USED
     * (Just to check "addSubscriber" functions)
     * This function will loop subscribers array
     * and print oll the subscribers info to the console.
     */
    public void showSubscribers(){
        for (Subscriber subscriber: subscribers){
            System.out.println("Name: " + subscriber.getName().toUpperCase() +
                    "\nID: " + subscriber.getId() +
                    "\n--------------------------");
        }
    }

    //BORROW BOOK
    public void borrowBook(){
        if (!loginData.isEmpty()) {
            System.out.println("BORROW BOOK" +
                    "\n===========");
            Scanner input = new Scanner(System.in);
        /*System.out.println("SUBSCRIBER NAME: ");
        String subscriberName = input.nextLine();*/
            String subscriberName = loginData.get(loginData.size() - 1);
            //boolean foundSubscriber = false;

            for (Subscriber subscriber : subscribers) {
                if ((subscriber.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                    //foundSubscriber = true;
                    String name = subscriber.getName();
                    System.out.println("BOOK TITLE TO BORROW: ");
                    String bookTitle = input.nextLine();
                    boolean foundBook = false;
                    for (Book book : books) {
                        if ((book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) && (book.getQuantity() > 0)) {
                            foundBook = true;
                            BorrowedBook borrowedBook = new BorrowedBook(name, book.getTitle(), book.getAuthor(), book.getQuantity(), book.getRating());
                            if (!existBook(borrowedBook)) {
                                borrowedBooks.add(borrowedBook);
                                System.out.println(ANSI_GREEN + bookTitle.toUpperCase() + " is now borrowed!\n" + ANSI_RESET);
                                book.setQuantity(book.getQuantity() - 1);
                                break;
                            } else {
                                System.out.println(ANSI_PURPLE + "You have already borrowed this book." + ANSI_RESET);
                            }
                        } else if ((book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) && (book.getQuantity() == 0)) {
                            foundBook = true;
                            System.out.println(ANSI_PURPLE + "Sorry, this book is not available to borrow." + ANSI_RESET);
                        }
                    }
                    if (!foundBook) {
                        System.out.println(ANSI_RED + "Sorry, We do not have this book." + ANSI_RESET);
                    }
                }
            }
            /*if (!foundSubscriber) {
                System.out.println(ANSI_PURPLE + "To borrow a book...\n" +
                        "you've to be a subscriber." + ANSI_RESET);
            }*/
        }else {
            System.out.println("Please Login or Register.");
        }
    }

    //MY BORROWED BOOKS
    public void showMyBorrowedBooks(){
        if (!loginData.isEmpty()) {
            System.out.println("MY BORROWED BOOKS" +
                    "\n=================");
            Scanner input = new Scanner(System.in);
            /*System.out.println("YOUR NAME: ");
            String subscriberName = input.nextLine();*/
            String subscriberName = loginData.get(loginData.size()-1);
            String bookTitle, bookAuthor;
            boolean found = false;
            for (BorrowedBook borrowedBooks : borrowedBooks) {
                if ((borrowedBooks.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                    found = true;
                    bookTitle = borrowedBooks.getTitle();
                    bookAuthor = borrowedBooks.getAuthor();
                    System.out.println(ANSI_BLUE + "Borrower: " + borrowedBooks.getName().toUpperCase() +
                            "\nTitle   : " + bookTitle.toUpperCase() +
                            "\nAuthor  : " + bookAuthor.toUpperCase() +
                            ANSI_RESET +
                            "\n-----------------------");
                }
            }
            /*if (!found) {
                System.out.println(ANSI_RED + "You've no borrowed books" + ANSI_RESET);
            }*/
        }else {
            System.out.println("Please Login or Register.");
        }
    }

    //RETURN BOOK
    public void returnBook(){
        if (!loginData.isEmpty()) {
            System.out.println("RETURN BOOK" +
                    "\n===========");
            Scanner input = new Scanner(System.in);
        /*System.out.println("YOUR NAME: ");
        String subscriberName = input.nextLine();*/
            String subscriberName = loginData.get(loginData.size() - 1);
            boolean foundSubscriber = false;
            boolean foundBook = false;
            try {
                for (BorrowedBook borrowedBook : borrowedBooks) {
                    if ((borrowedBook.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                        foundSubscriber = true;
                        System.out.println("BOOK TITLE: ");
                        String bookTitle = input.nextLine();

                        for (BorrowedBook borrowedBook2 : borrowedBooks) {
                            if ((borrowedBook2.getName().toLowerCase()).equals(subscriberName.toLowerCase()) && bookTitle.toLowerCase().equals(borrowedBook2.getTitle().toLowerCase())) {
                                borrowedBooks.remove(borrowedBook2);
                                System.out.println(ANSI_GREEN + bookTitle.toUpperCase() + " has been returned." + ANSI_RESET);
                                foundBook = true;
                                for (Book book : books) {
                                    if (book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) {
                                        book.setQuantity(book.getQuantity() + 1);
                                        Scanner input2 = new Scanner(System.in).useLocale(Locale.US);
                                        System.out.println(ANSI_BLUE + "Rate the book (Optional)" +
                                                "\nEnter a number 0.1 - 5.0" + ANSI_RESET);
                                        String str = input2.nextLine();
                                        if (str.isEmpty()) {
                                            System.out.println(ANSI_PURPLE + "You've skipped rating." + ANSI_RESET);
                                            break;
                                        } else if (str.contains(",")) {
                                            System.out.println(ANSI_RED + "Sorry! No rating added\nYou've used 'comma' instead of 'dot'!" + ANSI_RESET);
                                        } else {
                                            float rate = Float.parseFloat(str);
                                            if (rate > 0 && rate <= 5) {
                                                if (book.getRating() == 0) {
                                                    book.setRating(rate);
                                                    System.out.println(ANSI_GREEN + "Thanks for rating." + ANSI_RESET);
                                                } else {
                                                    float calRate = Float.parseFloat(avgRating(book.getRating(), rate));
                                                    book.setRating(calRate);
                                                    System.out.println(ANSI_GREEN + "Thanks for rating." + ANSI_RESET);
                                                }
                                                break;
                                            } else {
                                                System.out.println(ANSI_RED + "No rating added for out of limit." + ANSI_RESET);
                                                break;
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        if (!foundBook) {
                            System.out.println(ANSI_PURPLE + "Yod did not borrow this book." + ANSI_RESET);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                //System.out.println(e);
            }
            if (!foundSubscriber) {
                System.out.println(ANSI_RED + "You didn't borrow any book" + ANSI_RESET);
            }
        }else {
            System.out.println("Please Login or Register.");
        }
    }

    //SHOW ALL BORROWED BOOKS
    public void showAllBorrowedBooks(){
        System.out.println("ALL BORROWED BOOKS" +
                         "\n==================");

        //Show books from arraylist
        if (borrowedBooks.size() == 0){
            System.out.println(ANSI_PURPLE + "Nobody borrowed any books" + ANSI_RESET);
        }else {
            for (BorrowedBook borrowedBook : borrowedBooks) {
                System.out.printf("%sBorrower: %s \nTitle   : %s\nAuthor  : %s %s\n------------------\n",
                        ANSI_BLUE,
                        borrowedBook.getName().toUpperCase(),
                        borrowedBook.getTitle().toUpperCase(),
                        borrowedBook.getAuthor().toUpperCase(),
                        ANSI_RESET);
            }
        }
    }

    /**
     * Search the username in the Array.
     * This function was created to avoid the creation of
     * multiple users with the same username.
     * @param name String name
     * @return true or false
     */
    public boolean existUsername(String name){
        for (Subscriber subscriber: subscribers){
            if (subscriber.getName().equals(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    /**
     * Search the Pin Code in the Array.
     * This function was created to avoid the creation of
     * multiple users with the same username.
     * @param pinCode String name
     * @return true or false
     */
    public boolean existUser(String username, int pinCode){
        for (Subscriber subscriber: subscribers){
            if (subscriber.getName().toLowerCase().equals(username.toLowerCase()) && subscriber.getId() == pinCode ){
                return true;
            }
        }
        return false;
    }

    /**
     * Check the book's existence in the array
     * The function will take an Object as input
     * It will compare with input object's 'title' and 'name' with
     * 'borrowedBooks' object's 'title' and 'name'
     * @param borrowedBook Borrowed book object
     * @return true or false
     */
    public boolean existBook(BorrowedBook borrowedBook){
        for (BorrowedBook resBook: borrowedBooks){
            if (resBook.getTitle().equals(borrowedBook.getTitle()) && resBook.getName().equals(borrowedBook.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * To chek user login
     */
    public void loggedinUser(){
        if (!loginData.isEmpty()){
            for (int i=0; i<loginData.size(); i++){
                System.out.println(ANSI_BLUE + "Log in as " + loginData.get(loginData.size()-1).toUpperCase() + ANSI_RESET);
                break;
            }
        }
    }

    public boolean isLoggedin(){
        if (!loginData.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * Function to take input as float number
     * This function wil fist add the number in the Array, and
     * then calculate the total.
     * Finally the total will be divided by the size of the Array
     * To fix a bug I've also counted number of zeros and subtracted it from the Array size,
     * because I didn't want any zeros in the Array to calculate average rate.
     * @param num1
     * @param num2
     * @return Calculated average rating
     */
    public String avgRating(float num1, float num2){
        float avgRate = (num1 + num2)/(float) 2;
        return NumberFormat.getInstance(Locale.US).format(avgRate);
    }

}