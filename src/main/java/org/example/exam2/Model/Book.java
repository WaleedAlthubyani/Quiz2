package org.example.exam2.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    @NotEmpty(message = "Please enter the book's ID")
    @Pattern(regexp = "^[0-9]{10}$",message = "ID must be 10 digits long")
    private String id;

    @NotEmpty(message = "Please enter the book's name")
    @Size(min = 2, message = "A name can't be less than two characters long")
    private String name;

    @Positive(message = "Number of pages must be a positive number")
    private int number_of_pages;

    @Positive(message = "The price of the book can only be a positive number")
    private double price;

    @NotEmpty(message = "Please enter the book's category")
    @Pattern(regexp = "^(novel|academic)$",message = "Book's category can only be novel or academic")
    private String category;

    @NotNull(message = "if the book is available type true if not type false")
    private Boolean isAvailable;//choose Boolean instead of boolean so that NotNull can work on it

}
