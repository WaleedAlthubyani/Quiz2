package org.example.exam2.Service;

import org.example.exam2.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class UserService {

    HashMap<String, User> users = new HashMap<>();

    public ArrayList<User> getAllUsers(){
        return new ArrayList<>(users.values());
    }

    public boolean addNewUser(User user){
        if (users.containsKey(user.getId())){
            return false;//fail duplicate ID
        }

        users.put(user.getId(),user);
        return true;//success
    }

    public int updateUser(String userID, User user){
        if (!users.containsKey(userID)){
            return 0;//fail user not found
        }
        if (!user.getId().equals(userID)){
            return 1;//fail id not matching
        }

        users.put(userID,user);
        return 2;//success
    }

    public boolean deleteUser(String userID){
        if (!users.containsKey(userID)){
            return false;//fail user not found
        }

        users.remove(userID);
        return true;//success
    }

    public ArrayList<User> getUsersByBalance(double balance){
        ArrayList<User> usersByBalance= new ArrayList<>();

        for (User user:users.values()){
            if (user.getBalance()>=balance){
                usersByBalance.add(user);
            }
        }

        return usersByBalance;
    }

    public ArrayList<User> getUsersByAge(int age){
        ArrayList<User> usersByAge=new ArrayList<>();

        for (User user : users.values()){
            if (user.getAge()>=age){
                usersByAge.add(user);
            }
        }

        return usersByAge;
    }

}
