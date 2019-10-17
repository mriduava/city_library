package com.company;

import java.util.*;

public class CityLibrary {

    private static final int MAX_LIBRARIANS = 2;
    private ArrayList<Librarian> librarians = new ArrayList<>(MAX_LIBRARIANS);
    private ArrayList<Subscriber> subscribers = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<ReservedBook> reservedBooks  = new ArrayList<>();

    private String libraryName = "";
    public CityLibrary(String libraryName){
        this.libraryName = libraryName;
    }

    /*Scanner scan = new Scanner(System.in);
    public void promptMenu() {
        System.out.println("====================================\n" +
                           "   WELCOME TO " + libraryName);

        displayBooks();
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
                case "login":
                    System.out.println("LIBRARIAN'S LOGIN/REGISTRATION" +
                                     "\n==============================");
                    addLibrarian();
                    break;
                case "2":
                case "add book":
                    System.out.println("ADD BOOK'S TITLE & AUTHOR" +
                                      "\n=========================");
                    addBook();
                    break;
                case "3":
                case "show books":
                    System.out.println("AVAILABLE BOOKS" +
                                     "\n===============");
                    showBooks();
                    break;
                case "4":
                case "sort books":
                    System.out.println("SORT BOOKS" +
                                     "\n==========");
                    sortBooks();
                    break;
                case "5":
                case "become a subscriber":
                    System.out.println("BECOME A SUBSCRIBER" +
                                     "\n===================");
                    addSubscriber();
                    break;
                case "6":
                case "reserve book":
                    System.out.println("RESERVE BOOK" +
                                     "\n============");
                    reserveBook();
                    break;
                case "7":
                case "my reservation":
                    System.out.println("MY RESERVED BOOKS" +
                                     "\n=================");
                    showMyReservation();
                    break;
                case "8":
                case "cancel reservation":
                    System.out.println("CANCEL RESERVATION" +
                                     "\n=================");
                    cancelReservation();
                    break;
                case "9":
                case "reserved books":
                    System.out.println("ALL RESERVED BOOKS" +
                            "\n=================");
                    showAllReservedBooks();
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
                "   WELCOME TO " + libraryName);
        //To add books in array & to display
        displayBooks();

        MainMenu.MenuItems menuItems;
        do {
            menuItems = MainMenu.showMenuAndGetChoice();
            switch (menuItems) {
                case ADMIN_LOGIN_REG:
                    addLibrarian();
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
                    reserveBook();
                    break;
                case MY_BORROWED_BOOKS:
                    break;
                case RETURN_BOOK:
                    cancelReservation();
                    break;
                case ALL_BORROWED_BOOKS:
                    showAllReservedBooks();
                    break;
                case EXIT:
                    System.out.println("PROGRAM IS SHUTTING DOWN");
                    break;
            }
        } while (menuItems != menuItems.EXIT);
    }

     
    //ADD LIBRARIAN
    public void addLibrarian(){
        Scanner scan = new Scanner(System.in);
        String name, id;
        System.out.println("LIBRARIAN'S NAME: ");
        name = scan.nextLine();
        boolean isNumber;
        do{
            System.out.println("LIBRARIANS'S ID (4 digit): ");
            String regex = "\\d+";
            id = scan.nextLine();
            if (id.length() == 4 && id.matches(regex)) {
                isNumber = true;
                int id2 = Integer.valueOf(id);
                Librarian librariansInfo = new Librarian(name, id2);
                if (librarians.size()== MAX_LIBRARIANS){
                    System.out.println("Librarian's position is full");
                }else {
                    librarians.add(librariansInfo);
                    librariansInfo.welcomeMessage();
                }
            } else {
                isNumber = false;
                System.out.println("Please enter a 4 digit number...");
            }
        }while (!(isNumber));
    }

    //SHOW LIBRARIANS
    public void showLibrarians(){
        for (int i=0; i<librarians.size(); i++){
            System.out.println("Name: " + librarians.get(i).getName().toUpperCase() +
                    "\nID: " + librarians.get(i).getId() +
                    "\n--------------------------");
        }
    }

    //TO ADD & DISPLAY BOOKS
    public void displayBooks(){
        Book emil = new Book("Emil", "Astrid Lindgren", 2, 0.0f);
        Book matilda = new Book("Matilda", "Roald Dahl", 3, 0.0f);
        Book whiteTiger = new Book("White Tiger", "Aravind Adiga", 0, 0.0f);
        Book skuld = new Book("Skuld", "Karin Alvtegen", 1, 0.0f);

        books.add(emil);
        books.add(matilda);
        books.add(whiteTiger);
        books.add(skuld);
    }

    //ADD BOOK PART 1
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

    //ADD BOOK PART 2
    public void addBook(){
        if (librarians.size() != 0){
            generateBookInfo();
        }else {
            System.out.println("TO ADD BOOKS, YOU'VE TO LOGIN" +
                    "\n----------------------------");
            addLibrarian();
            generateBookInfo();
        }
    }

    //SHOW BOOKS
    public void showBooks(){
        //SHOW BOOKS FROM ARRAY LIST
        for (Book books: books){
            System.out.println("Title   : " + books.getTitle().toUpperCase() +
                    "\nAuthor  : " + books.getAuthor().toUpperCase() +
                    "\nAvg Rate: " + books.getRating() +
                    "\nQuantity: " + books.getQuantity() +
                    "\n-----------------------");
        }
        //SHOW BOOKS FROM FILE
        /*ArrayList<Book> booksFile = (ArrayList<Book>)FileUtility.readBooksList("booksList.ser");
        System.out.println(booksFile.toString().replace("[", "").replace("]", ""));*/

        //List<Book> students = (List<Book>)FileUtility.loadObject("books.ser");
    }

    //SORT BOOKS PART 3
    public void sortBooks(){
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

    //ADD SUBSCRIBERS
    public void addSubscriber(){
        Scanner scan = new Scanner(System.in);
        String name, id;
        System.out.println("SUBSCRIBER'S NAME: ");
        name = scan.nextLine();
        boolean isNumber;
        do{
            System.out.println("SUBSCRIBER'S ID (4 digit): ");
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

        }while (!(isNumber));
    }

    //SHOW SUBSCRIBERS
    public void showSubscribers(){
        for (Subscriber subscriber: subscribers){
            System.out.println("Name: " + subscriber.getName().toUpperCase() +
                    "\nID: " + subscriber.getId() +
                    "\n--------------------------");
        }
    }

    //RESERVE BOOK
    public void reserveBook(){
        Scanner input = new Scanner(System.in);
        System.out.println("SUBSCRIBER NAME: ");
        String subscriberName = input.nextLine();

        boolean foundSubscriber = false;
        for (Subscriber subscriber: subscribers){
            if ((subscriber.getName().toLowerCase()).equals(subscriberName.toLowerCase())){
                foundSubscriber = true;
                String name = subscriber.getName();
                System.out.println("BOOK TITLE TO RESERVE: ");
                String bookTitle = input.nextLine();
                boolean foundBook = false;
                for (Book book: books){
                    if ((book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) && (book.getQuantity() > 0)){
                        foundBook = true;
                        ReservedBook reservedBook = new ReservedBook(name, book.getTitle(), book.getAuthor(), book.getQuantity(), book.getRating());
                        if (!existBook(reservedBook)){
                            reservedBooks.add(reservedBook);
                            System.out.println(bookTitle.toUpperCase() + " is now reserved!\n");
                            book.setQuantity(book.getQuantity()-1);
                            break;
                        }else{
                            System.out.println("You have already reserved this book.");
                        }
                    } else if ((book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())) && (book.getQuantity() == 0)){
                        foundBook = true;
                        System.out.println("Sorry, this book is not available to reserve.");
                    }
                }
                if (!foundBook){
                    System.out.println("Sorry, We do not have this book.");
                }
            }
        }
        if (!foundSubscriber){
            System.out.println("To reserve a book...\n" +
                    "you've to be a subscriber.");
        }
    }

    //MY RESERVED BOOKS
    public void showMyReservation(){
        Scanner input = new Scanner(System.in);
        System.out.println("YOUR NAME: ");
        String subscriberName = input.nextLine();
        String bookTitle, bookAuthor;

        boolean found = false;
        for (ReservedBook reservedBooks: reservedBooks){
            if ((reservedBooks.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                found = true;
                bookTitle = reservedBooks.getTitle();
                bookAuthor = reservedBooks.getAuthor();
                System.out.println("Subscriber : " + reservedBooks.getName().toUpperCase() +
                        "\nBook Title : " + bookTitle.toUpperCase() +
                        "\nBook Author: " + bookAuthor.toUpperCase() +
                        "\n-----------------------");
            }
        }
        if (!found){
            System.out.println("You've no reserved books");
        }
    }

    //CANCEL RESERVATION
    public void cancelReservation(){
        Scanner input = new Scanner(System.in);
        System.out.println("YOUR NAME: ");
        String subscriberName = input.nextLine();

        boolean foundSubscriber = false;
        boolean foundBook = false;
        try {
            for (ReservedBook reservedBook: reservedBooks){
                if ((reservedBook.getName().toLowerCase()).equals(subscriberName.toLowerCase())) {
                    foundSubscriber = true;
                    System.out.println("BOOK TITLE: ");
                    String bookTitle = input.nextLine();
                    if ((reservedBook.getTitle().toLowerCase()).equals(bookTitle.toLowerCase())) {
                        foundBook = true;
                        reservedBooks.remove(reservedBook);
                        for (Book book: books) {
                            if (book.getTitle().toLowerCase().equals(bookTitle.toLowerCase())){
                                book.setQuantity(book.getQuantity() + 1);

                                Scanner input2 = new Scanner(System.in).useLocale(Locale.US);
                                System.out.println("Rate the book (Optional)" +
                                                   "\nEnter a number 0.1 - 5.0");
                                    String str = input2.nextLine();
                                    if(str.isEmpty()){
                                        System.out.println("You've skipped rating.");
                                        break;
                                    } else {
                                        float rate = Float.parseFloat(str);
                                        if (rate > 0 && rate <=5){
                                            book.setRating(avgRating(rate));
                                            System.out.println("Thanks for rating.");
                                            break;
                                        }else {
                                            System.out.println("No rating added for out of limit.");
                                            break;
                                        }
                                    }
                            }
                        }
                        System.out.println(reservedBook.getTitle().toUpperCase() + " has been canceled.");
                    } else if(!foundBook) {
                        System.out.println("Yod did not reserve this book.");
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

    //SHOW ALL RESERVED BOOKS
    public void showAllReservedBooks(){
        for (ReservedBook reservedBook: reservedBooks){
            System.out.println("Name  : " + reservedBook.getName().toUpperCase() +
                    "\nTitle : " + reservedBook.getTitle().toUpperCase() +
                    "\nAuthor: " + reservedBook.getAuthor().toUpperCase() +
                    "\n--------------------------");
        }
    }

    //CHECK IF THE BOOK EXIST IN THE ARRAY LIST
    public boolean existBook(ReservedBook reservedBook){
        for (ReservedBook resBook: reservedBooks){
            if (resBook.getTitle().equals(reservedBook.getTitle()) && resBook.getName().equals(reservedBook.getName())){
                return true;
            }
        }
        return false;
    }

    //CALCULATE AVERAGE RATING
    private ArrayList<Float> rate = new ArrayList<>();
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