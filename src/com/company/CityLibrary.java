package com.company;

import java.nio.file.*;
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
 * @version 1.2.0
 * @since 2019.10.29
 */

public class CityLibrary{

    //Defined fields and ArrayLists
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
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";

    /**
     * CityLibrary constructor
     * Takes the library name is parameter
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


        //To add books in the array
        displayBooks();

        //Create files if they are not exist
        Path path1 = Paths.get("books.ser");
        Path path2 = Paths.get("br_books.ser");
        Path path3 = Paths.get("subscribers.ser");
        if (!Files.exists(path1) && !Files.exists(path2) && !Files.exists(path3)){
            FileUtility.saveObject("books.ser", books);
            FileUtility.saveObject("br_books.ser", borrowedBooks);
            FileUtility.saveObject("subscribers.ser", subscribers);
        }

        //Load Files & fill the defined ArrayList with the data of Files.
        ArrayList<Book> booksFile = (ArrayList<Book>)FileUtility.loadObject("books.ser");
        ArrayList<BorrowedBook> brBooksFile = (ArrayList<BorrowedBook>)FileUtility.loadObject("br_books.ser");
        ArrayList<Subscriber> subscribersFile = (ArrayList<Subscriber>)FileUtility.loadObject("subscribers.ser");
        books = booksFile;
        borrowedBooks = brBooksFile;
        subscribers = subscribersFile;

        //To display the user name who is recently logged in or has registered
        isLoggedIn();

        //To display menu items
        MainMenu.MenuItems menuItems;
        do {
            isLoggedIn();
            menuItems = MainMenu.showMenuAndGetChoice();
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
                    //All data will be saved before the program shut down
                    FileUtility.saveObject("books.ser", books);
                    FileUtility.saveObject("subscribers.ser", subscribers);
                    FileUtility.saveObject("br_books.ser", borrowedBooks);
                    System.out.println(RED_BOLD +"PROGRAM IS SHUTTING DOWN"+ ANSI_RESET);
                    break;
            }
        } while (menuItems != menuItems.EXIT);
    }

    /**
     * Function to add Books in the books array
     * This function will execute before displaying the menu-items
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
     * Function to create book object and to add in the "books" array
     * This function is called from the "addBook" function.
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
                System.out.println(ANSI_GREEN + title.toUpperCase() +  " has been successfully added." + ANSI_RESET);
            }else {
                System.out.println(ANSI_RED + "Please write a number..." + ANSI_RESET);
                isNumber = false;
                input2.next();
            }
        }while (!(isNumber));
    }

    /**
     * This function will assure that only Admin can add books, not other users.
     * To force the user to register "registerAdmin" function has been called.
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
     * Function to register subscribers
     * Boolean function 'existUsername'  has been called here to avoid the
     * creation of multiple users with the same username.
     * It will also check if the pincode is a 4 digit number.
     * If 'username' and 'pincode' are valid, these two values will
     * create a Subscriber object, will add to 'subscribers' ArrayList.
     * Besides the registration, this function also works as 'login' by
     * sending data to 'loginData' array.
     */
    public void registerSubscriber(){
        System.out.println("SUBSCRIBER REGISTRATION" +
                         "\n=======================");
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
     * Function to login subscriber
     * First it will search the username in subscribers array. If it exists,
     * it will ask again to enter the pincode. If both username and pincode match
     * with the user input values, it will add the 'username' into the 'loginData'
     * array. And finally it will print a success message in console.
     * 'Do-while' loop will give the users only two chances if they enter wrong
     * pincode or invalid number.
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
                int tryCount = 1;
                do {
                    System.out.println("PIN CODE (4 digit): ");
                    String regex = "\\d+";
                    id = scan.nextLine();
                    if (id.length() == 4 && id.matches(regex)) {
                        int id2 = Integer.parseInt(id);
                        if (existUser(name, id2)){
                            isNumber = true;
                            loginData.add(name);
                            System.out.println(ANSI_GREEN + "Hello " + name.toUpperCase() + " !" + ANSI_RESET);
                        }else {
                            isNumber = false;
                            if (tryCount < 3){
                                System.out.println(ANSI_PURPLE + "Pincode is wrong\nTry again..." + ANSI_RESET);
                                tryCount++;
                            }else {
                                System.out.println(ANSI_PURPLE + "Sorry! Try later..." + ANSI_RESET);
                                tryCount++;
                            }
                        }
                    } else {
                        isNumber = false;
                        if (tryCount < 3){
                            System.out.println(ANSI_RED + "Not a 4 digit number" + ANSI_RESET);
                            tryCount++;
                        }else {
                            System.out.println(ANSI_RED + "Sorry! Try later..." + ANSI_RESET);
                            tryCount++;
                        }
                    }
                } while (!(isNumber) && tryCount<=3);
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

    /**
     * Function to borrow books
     * First the function will check the 'loginData' array where user's loggedin data
     * are stored. If the username exist in that array, it will ask the user to enter
     * a book title. If the book exist in the array, then the 'username' and
     * 'book info' will create a new object and will add to the "borrowedBooks" array.
     * Two nested 'for-loops' is used to search the subscriber in 'subscribers' array &
     * book in the 'books' array.
     * For different conditions, it will give different message as output.
     */
    public void borrowBook(){
        if (!loginData.isEmpty()) {
            System.out.println("BORROW BOOK" +
                    "\n===========");
            Scanner input = new Scanner(System.in);
            String subscriberName = loginData.get(loginData.size() - 1);
            for (Subscriber subscriber : subscribers) {
                if ((subscriber.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
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
                                System.out.println(ANSI_GREEN + "You've just borrowed " + bookTitle.toUpperCase() + ANSI_RESET);
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
        }else {
            System.out.println(ANSI_BLUE + "TO BORROW A BOOK..." + ANSI_RESET +
                    ANSI_PURPLE +"\nPlease Login or Register." + ANSI_RESET);
        }
    }

    /**
     * Function to show the LoggedIn subscriber's borrowed books.
     * The function will check the 'loginData' array. If it is not empty, it
     * will take tale the value of the last index of the 'loginData' array, which
     * is equal to 'subscriberName'. Finally the 'for-loop' function will search
     * in the 'borrowedBooks' array by this 'subscriber' name, and will print all
     * the values.
     */
    public void showMyBorrowedBooks(){
        if (!loginData.isEmpty()) {
            System.out.println("MY BORROWED BOOKS" +
                    "\n=================");
            String subscriberName = loginData.get(loginData.size()-1);
            boolean found = false;
            for (BorrowedBook borrowedBooks : borrowedBooks) {
                if ((borrowedBooks.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                    found = true;
                    System.out.println(ANSI_BLUE + borrowedBooks + ANSI_RESET);
                }
            }
            if (!found){
                System.out.println(ANSI_RED + "You've no borrowed books" + ANSI_RESET);
            }
        }else {
            System.out.println(ANSI_BLUE + "TO SEE YOUR BORROWED BOOKS..." + ANSI_RESET +
                    ANSI_PURPLE +"\nPlease Login or Register." + ANSI_RESET);
        }
    }

    /**
     * Function to return book.
     * If the user is logged in by checking 'loginData' array, this function will proceed to
     * the next step to work. Then it will search the 'borrowedBooks' array.
     */
    public void returnBook(){
        if (!loginData.isEmpty()) {
            System.out.println("RETURN BOOK" +
                    "\n===========");
            Scanner input = new Scanner(System.in);
            String subscriberName = loginData.get(loginData.size() - 1);
            boolean existBook = false;
            try {
                for (BorrowedBook borrowedBook : borrowedBooks) {
                    if ((borrowedBook.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                        System.out.println("BOOK TITLE: ");
                        String bookTitle = input.nextLine();

                        for (BorrowedBook borrowedBook2 : borrowedBooks) {
                            if ((borrowedBook2.getName().toLowerCase()).equals(subscriberName.toLowerCase()) && bookTitle.toLowerCase().equals(borrowedBook2.getTitle().toLowerCase())) {
                                borrowedBooks.remove(borrowedBook2);
                                System.out.println(ANSI_GREEN + bookTitle.toUpperCase() + " has been returned." + ANSI_RESET);
                                existBook = true;
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
                                                    float calRate = avgRating(book.getRating(), rate);
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
                        if (!existBook) {
                            System.out.println(ANSI_PURPLE + "Yod did not borrow this book." + ANSI_RESET);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                //System.out.println(e);
            }
        }else {
            System.out.println(ANSI_BLUE + "TO RETURN BOOK..." + ANSI_RESET +
                    ANSI_PURPLE +"\nPlease Login or Register." + ANSI_RESET);
        }
    }

    //SHOW ALL BORROWED BOOKS
    public void showAllBorrowedBooks(){
        System.out.println("ALL BORROWED BOOKS" +
                         "\n==================");
        if (borrowedBooks.size() == 0){
            System.out.println(ANSI_PURPLE + "Nobody borrowed any books" + ANSI_RESET);
        }else {
            for (BorrowedBook borrowedBook : borrowedBooks) {
                System.out.println(ANSI_BLUE + borrowedBook + ANSI_RESET);
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
     * To check if the user is loggedin
     * This function will serch the username in 'loginData' array which is filled by the 'usernames' while
     * the users were logged in.
     */
    public void isLoggedIn(){
        if (!loginData.isEmpty()){
            for (int i=0; i<loginData.size(); i++){
                System.out.println(YELLOW_BACKGROUND_BRIGHT +
                        "  Login as " + loginData.get(loginData.size()-1).toUpperCase() + "  "
                        + ANSI_RESET);
                break;
            }
        }
    }

    /**
     * Function to take input as float numbers
     * One number will get from the Book's object rating number, and another
     * will assigned by the user input.
     * @param num1 float number
     * @param num2 float number
     * @return Calculated average rating
     */
    public float avgRating(float num1, float num2){
        float avgRate = (num1 + num2)/(float) 2;
        return avgRate;
    }

}