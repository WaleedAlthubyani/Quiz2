package org.example.exam2.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.exam2.ApiResponse.ApiResponse;
import org.example.exam2.Model.User;
import org.example.exam2.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<ArrayList<User>> getAllUsers(){
        ArrayList<User> users=userService.getAllUsers();
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity addNewUser(@RequestBody @Valid User user,Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean isCreated = userService.addNewUser(user);

        if (isCreated){
            return ResponseEntity.status(201).body(new ApiResponse("User created successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("User's ID already exists in the system"));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        int result = userService.updateUser(userId,user);

        if (result==0){
            return ResponseEntity.status(404).body(new ApiResponse("User not found"));
        }
        if (result==1){
            return ResponseEntity.status(400).body(new ApiResponse("the old ID must match the new ID"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        boolean isDeleted = userService.deleteUser(userId);

        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("User not found"));
    }

    @GetMapping("/get-users-by-balance/{balance}")
    public ResponseEntity<ArrayList<User>> getUsersByBalance(@PathVariable double balance){
        ArrayList<User> usersByBalance = userService.getUsersByBalance(balance);
        return ResponseEntity.status(200).body(usersByBalance);
    }

    @GetMapping("/get-users-by-age/{age}")
    public ResponseEntity<ArrayList<User>> getUsersByAge(@PathVariable int age){
        ArrayList<User> usersByAge= userService.getUsersByAge(age);

        return ResponseEntity.status(200).body(usersByAge);
    }

}
