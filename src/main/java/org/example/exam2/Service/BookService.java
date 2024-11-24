package org.example.exam2.Service;

import org.example.exam2.Model.Book;
import org.example.exam2.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class BookService {

    HashMap<String, Book> books = new HashMap<>();

    public ArrayList<Book> getAllBooks(){
        return new ArrayList<>(books.values());
    }

    public boolean addNewBook(Book book){
        if (books.containsKey(book.getId())){
            return false;//duplicate id fail
        }

        books.put(book.getId(),book);
        return true;//success
    }

    public int updateBook(String bookID,Book book){
        if (!books.containsKey(bookID)){
            return 0;//fail book not found
        }
        if (!book.getId().equals(bookID)){
            return 1;//fail book's new ID doesn't match original ID
        }

        books.put(bookID,book);
        return 2;//success
    }

    public boolean deleteBook(String bookID){
        if (!books.containsKey(bookID)){
            return false;//fail book not found
        }

        books.remove(bookID);
        return true;//success
    }

    public Book getBookByName(String bookName){
        for (Book book : books.values()){
            if (book.getName().equalsIgnoreCase(bookName))
                return book;
        }

        return null;//fail book not found
    }

    public ArrayList<Book> booksByCategory(String category){
        ArrayList<Book> booksByCategory = new ArrayList<>();

        for (Book book : books.values()){
            if (book.getCategory().equalsIgnoreCase(category)){
                booksByCategory.add(book);
            }
        }

        return booksByCategory;//if user category is not academic or novel list will be empty
    }

    public ArrayList<Book> booksByNumberOfPages(@PathVariable int number_of_pages){
        ArrayList<Book> booksByPages= new ArrayList<>();

        for (Book book:books.values()){
            if (book.getNumber_of_pages()==number_of_pages){
                booksByPages.add(book);
            }
        }

        return booksByPages;
    }

    public int changeBookStatusToUnAvailable(ArrayList<User> users,String userID, String bookId){

        User currentUser=null;
        for (User user:users){
            if (user.getId().equals(userID)){
                currentUser=user;
            }

        }

        if (currentUser==null)
            return 0;//user not found
        if (!currentUser.getRole().equals("libraian"))
            return 1;//user not authorized
        if (!books.containsKey(bookId))
            return 2;//Book not found
        if (!books.get(bookId).getIsAvailable())
            return 3;//Book already is unAvailable

        books.get(bookId).setIsAvailable(false);
        return 4;//success

    }
}
