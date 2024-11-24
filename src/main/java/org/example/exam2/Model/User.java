package org.example.exam2.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "Please enter the user's ID")
    @Pattern(regexp = "^[0-9]{10}$",message = "ID must be 10 digits long")
    private String id;

    @NotEmpty(message = "Please enter the user's name")
    @Size(min = 2,message = "User's name can't be less than two characters")
    private String name;

    @Positive(message = "The user's age can only be a positive number")
    @Min(value = 5,message = "User's age can't be less than 5")
    private int age;

    @Positive(message = "User's balance must be a positive number")
    private double balance;

    @NotEmpty(message = "Please enter the user's role")
    @Pattern(regexp = "^(customer|libraian)$",message = "User's role can only be customer or librarian")
    private String role;
}
