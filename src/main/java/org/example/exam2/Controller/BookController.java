package org.example.exam2.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.exam2.ApiResponse.ApiResponse;
import org.example.exam2.Model.Book;
import org.example.exam2.Service.BookService;
import org.example.exam2.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<ArrayList<Book>> getAllBooks(){
        return ResponseEntity.status(200).body(bookService.getAllBooks());
    }

    @PostMapping("/add")
    public ResponseEntity addNewBook(@RequestBody @Valid Book book, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isAdded=bookService.addNewBook(book);

        if (isAdded){
            return ResponseEntity.status(201).body(new ApiResponse("Book added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Book's id already exists in the system"));
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity updateBook(@PathVariable String bookId, @RequestBody @Valid Book book,Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        int result = bookService.updateBook(bookId,book);

        if (result==0)
            return ResponseEntity.status(404).body(new ApiResponse("Book not found"));
        else if (result==1)
            return ResponseEntity.status(400).body(new ApiResponse("Book's new ID must match old ID"));
        else
            return ResponseEntity.status(200).body(new ApiResponse("Book updated successfully"));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable String bookId){
        boolean isDeleted = bookService.deleteBook(bookId);

        if (isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Book deleted successfully"));

        return ResponseEntity.status(404).body(new ApiResponse("Book not found"));
    }

    @GetMapping("/get-books-by-name/{bookName}")
    public ResponseEntity getBookByName(@PathVariable String bookName){
        Book book = bookService.getBookByName(bookName);

        if (book==null)
            return ResponseEntity.status(404).body(new ApiResponse("Book not found"));

        return ResponseEntity.status(200).body(book);
    }

    @GetMapping("/get-books-by-category/{category}")
    public ResponseEntity<ArrayList<Book>> getBooksByCategory(@PathVariable String category){
        ArrayList<Book> booksByCategory = bookService.booksByCategory(category);

        return ResponseEntity.status(200).body(booksByCategory);
    }

    @GetMapping("/get-books-by-number-of-pages/{number_of_pages}")
    public ResponseEntity<ArrayList<Book>> getBooksByNumberOfPages(@PathVariable int number_of_pages){
        ArrayList<Book> booksByPages = bookService.booksByNumberOfPages(number_of_pages);
        return ResponseEntity.status(200).body(booksByPages);
    }

    @PutMapping("/change-book-status-to-unavailable/{userId}/{bookId}")
    public ResponseEntity<ApiResponse> changeBookStatusToUnAvailable(@PathVariable String userId,@PathVariable String bookId){
        int result = bookService.changeBookStatusToUnAvailable(userService.getAllUsers(),userId,bookId);

        switch (result){
            case 0: return ResponseEntity.status(404).body(new ApiResponse("User not found"));
            case 1: return ResponseEntity.status(401).body(new ApiResponse("User not authorized to do this action"));
            case 2: return ResponseEntity.status(404).body(new ApiResponse("Book not found"));
            case 3: return ResponseEntity.status(400).body(new ApiResponse("Book is already unavailable"));
            default: return ResponseEntity.status(200).body(new ApiResponse("Book's availability changed successfully"));
        }
    }

}
