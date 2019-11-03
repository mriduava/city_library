package com.company;

import java.nio.file.*;
import java.util.*;

/**
 * <h1>Library Program.</h1>
 * <p>Admin and Subscribers as Users, where only Admin can add Books
 * and Subscriber can Borrow or Return Books.
 * All users can see available books and sort books by 'title' or 'author'.
 * Log in function has been added with this version. So, the subscriber must
 * have to register or login to borrow books.
 * If a subscriber borrows a book, the book's quantity will be subtracted,
 * and by returning the book, the quantity will be added again.
 * While returning the book, subscribes can also add 'rating' which is a float
 * number (0.1 to 5.0).  Rating book is optional, that means the user cna skip
 * that option just by pressing enter.</p>
 *
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
    private ArrayList<String> loginData = new ArrayList<>();


    //Just to color the console text
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";

    /**
     * CityLibrary constructor.
     * It takes the library name is parameter.
     */
    private String libraryName = "";
    public CityLibrary(String libraryName){
        this.libraryName = libraryName;
    }

    /**
     * This function will display the Menu Items.
     * Each menu-item will call its specific function to execute by "switch" method.
     * Before displaying menu-items this function calls other functions, for example,
     * 'addBooksToArray' function will fill the 'books' array with pre-defined Book objects.
     *
     * To avoid getting "NoSuchFileException", files are created before 'switch' (menu-items)
     * function executes.  All files will be loaded at the starting of the program, and will
     * be saved before the program is closed. So, users can retrieve data after restart the program.
     */
    public void promptMenu() {
        System.out.println("====================================\n" +
                "* WELCOME TO " + libraryName + "  *");

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

        //To display menu items.
        MainMenu.MenuItems menuItems;
        do {
            //To display the user name who is recently logged in or has registered.
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
                    //All data will be saved before the program shut down.
                    FileUtility.saveObject("books.ser", books);
                    FileUtility.saveObject("subscribers.ser", subscribers);
                    FileUtility.saveObject("br_books.ser", borrowedBooks);
                    System.out.println("PROGRAM IS SHUTTING DOWN");
                    break;
            }
        } while (menuItems != menuItems.EXIT);
    }

    /**
     * Function to add Books in the books array.
     * This function will execute before displaying the menu-items.
     */
    public void displayBooks(){
        Book emil = new Book("Emil", "Astrid Lindgren", 2, 0.0f);
        Book matilda = new Book("Matilda", "Roald Dahl", 3, 0.0f);
        Book ladiesCope = new Book("Ladies Coupe", "Anita Nair", 1, 0.0f);
        Book hunden = new Book("Hunden", "Kerstin Ekman", 3, 0.0f);

        books.add(matilda);
        books.add(emil);
        books.add(ladiesCope);
        books.add(hunden);
    }

    /**
     * Function to register Admin.
     * Maximum 2 Admins can be added, as it is predeclared as MAX_ADMINS=2.
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
                    System.out.println("Admin's position is full");
                }else {
                    admins.add(adminInfo);
                    System.out.println(adminInfo.welcomeMessage());
                }
            } else {
                isNumber = false;
                System.out.println("Please enter a 4 digit number...");
            }
        }while (!(isNumber));
    }

    /**
     * NOT USED
     * Just to check if "registerAdmin" function works.
     * Function will print all registered Admins.
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
                System.out.println(title.toUpperCase() +  " has been successfully added.");
            }else {
                System.out.println("Please write a number...");
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
            System.out.println("To add books please register first..." +
                    "\n----------------------------");
            registerAdmin();
            generateBookInfo();
        }
    }

    /**
     * To Print all the books from Array.
     * To print all the books, "toString" method is used.
     */
    public void showBooks(){
        System.out.println("AVAILABLE BOOKS" +
                "\n===============");
        /*for (Book book: books){
            System.out.println(book);
        }*/
        printArrayList(books);
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

                System.out.println("BOOKS SORTED BY TITLE" +
                        "\n---------------------");
                for(Book books: books){
                    System.out.println(books);
                }
                return;
            case "author":
                Book.setSortBy(Book.SortBy.AUTHOR);
                Collections.sort(books);

                System.out.println("BOOKS SORTED BY AUTHOR" +
                        "\n----------------------");
                for(Book books: books){
                    System.out.println(books);
                }
                return;
        }
    }

    /**
     * Function to register subscribers.
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
                        System.out.println(subscriberInfo.welcomeMessage());
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

    /**
     * Function to login subscriber.
     * First it will search the username in 'subscribers' array. If it exists,
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
                        System.out.println("Hello " + name.toUpperCase() + " !");
                    }else {
                        isNumber = false;
                        if (tryCount < 3){
                            System.out.println("Pincode is wrong\nTry again...");
                            tryCount++;
                        }else {
                            System.out.println("Sorry! Try later...");
                            tryCount++;
                        }
                    }
                } else {
                    isNumber = false;
                    if (tryCount < 3){
                        System.out.println("Not a 4 digit number");
                        tryCount++;
                    }else {
                        System.out.println("Sorry! Try later...");
                        tryCount++;
                    }
                }
            } while (!(isNumber) && tryCount<=3);
        }else {
            System.out.println("You're not a Subscriber\nPlease register");
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
            //Call this function here to display all the books again.
            //Just to make the program user-friendly. (Thanks to Johan, as he suggested me.)
            showBooks();

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
                                System.out.println(bookTitle.toUpperCase() + " is now borrowed.");
                                book.setQuantity(book.getQuantity() - 1);
                                break;
                            } else {
                                System.out.println("You have already borrowed this book.");
                            }
                        } else if ((book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) && (book.getQuantity() == 0)) {
                            foundBook = true;
                            System.out.println("Sorry, this book is not available to borrow.");
                        }
                    }
                    if (!foundBook) {
                        System.out.println("Sorry, We do not have this book.");
                    }
                }
            }
        }else {
            System.out.println("TO BORROW A BOOK..." +
                    "\nPlease Login or Register.");
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
            /*String subscriberName = loginData.get(loginData.size()-1);
            boolean found = false;
            for (BorrowedBook borrowedBooks : borrowedBooks) {
                if ((borrowedBooks.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                    found = true;
                    System.out.println(borrowedBooks);
                }
            }
            if (!found){
                System.out.println("You've no borrowed books");
            }*/
            printArray(borrowedBooks);
        }else {
            System.out.println("TO SEE YOUR BORROWED BOOKS..." +
                    "\nPlease Login or Register.");
        }
    }

    /**
     * Function to return book.
     * This function also works if the subscriber is log in. The whole process done
     * by several steps. First, it will search only the book's title in the 'borrowedBooks'
     * array. Secondly, it will compare the book's title and name with the 'logged in' subscriber's name
     * and given book tile. If it validates, first the subscriber's book will be removed
     * from 'borrowedBooks' array. Finally, it will search that book in the 'books' array &
     * will add 1 in the book's quantity.
     * Same arraylist has been looped thorough two times, as it was not working. The
     * reason is unknown, needs to identify. May be it can be solved in different way.
     */
    public void returnBook(){

        if (!loginData.isEmpty()) {
            String subscriberName = loginData.get(loginData.size()-1);
            boolean found = false;
            for (BorrowedBook borrowedBooks : borrowedBooks) {
                if ((borrowedBooks.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                    found = true;
                    System.out.println(borrowedBooks);

                }
            }
            /*if (found){
                System.out.println("YOUR BORROWED BOOKS" +
                                 "\n===================");
            }*/
            System.out.println("RETURN BOOK" +
                    "\n===========");
            Scanner input = new Scanner(System.in);
           // String subscriberName = loginData.get(loginData.size() - 1);
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
                                System.out.println(bookTitle.toUpperCase() + " has been returned.");
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
                                        } else if (str.contains(",")) {
                                            System.out.println("Sorry! No rating added\nYou've used 'comma' instead of 'dot'!");
                                        } else {
                                            float rate = Float.parseFloat(str);
                                            if (rate > 0 && rate <= 5) {
                                                if (book.getRating() == 0) {
                                                    book.setRating(rate);
                                                    System.out.println("Thanks for rating.");
                                                } else {
                                                    float calRate = avgRating(book.getRating(), rate);
                                                    book.setRating(calRate);
                                                    System.out.println("Thanks for rating.");
                                                }
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
                        if (!foundBook) {
                            System.out.println("Yod did not borrow this book.");
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                //System.out.println(e);
            }
            if (!foundSubscriber){
                System.out.println("You have no books to return.");
            }
        }else {
            System.out.println("TO RETURN BOOK..." +
                    "\nPlease Login or Register.");
        }
    }

    /**
     * This function will print all the borrowed books by
     * searching 'borrowedBooks' array.
     */
    public void showAllBorrowedBooks(){
        System.out.println("ALL BORROWED BOOKS" +
                "\n==================");
        if (borrowedBooks.size() == 0){
            System.out.println("Nobody borrowed any books");
        }else {
            /*for (BorrowedBook borrowedBook : borrowedBooks) {
                System.out.println(borrowedBook);
            }*/
            printArrayList(borrowedBooks);
        }
    }

    /**
     * Function to search the username in the Array.
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
     * Check the book's existence in the array.
     * The function will take an Object as input.
     * It will compare with input object's 'title' and 'name' with
     * 'borrowedBooks' object's 'title' and 'name'.
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
     * To check if the user is logged in.
     * When a subscriber login, the 'username' saved in the 'loginData' ArrayList.
     * This function will loop through the array & take out the value of the last index of the Array.
     */
    public void isLoggedIn(){
        if (!loginData.isEmpty()){
            for (int i=0; i<loginData.size(); i++){
                System.out.println(YELLOW_BACKGROUND_BRIGHT + ">> Login as " + loginData.get(loginData.size()-1).toUpperCase() + "  " + ANSI_RESET);
                break;
            }
        }
    }

    /**
     * Function to take two inputs as float numbers and
     * then calculate the average.
     * @param num1 float number
     * @param num2 float number
     * @return Calculated average rating
     */
    public float avgRating(float num1, float num2){
        float avgRate = (num1 + num2)/(float) 2;
        return avgRate;
    }

    public <E extends HasGetName> void printArray(ArrayList<E> arrayList){
        if (!loginData.isEmpty()) {
            String subscriberName = loginData.get(loginData.size() - 1);
            boolean found = false;
            for (E element : arrayList) {
                if ((element.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                    found = true;
                    System.out.println(element);
                }
            }
            if (!found) {
                System.out.println("You've no borrowed books");
            }
        }
    }

    public <E> void printArrayList(ArrayList<E> arrayList){
        for (E element : arrayList) {
            System.out.println(element);
        }
    }

}