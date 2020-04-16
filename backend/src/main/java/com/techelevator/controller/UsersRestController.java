package com.techelevator.controller;

import java.util.List;

import com.techelevator.model.login.User;
import com.techelevator.model.login.UserDao;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin
public class UsersRestController {

    private UserDao userDao;
    
    @Autowired
    public UsersRestController( UserDao userDao){
        this.userDao = userDao; 
        }

        // Get all the users
        @GetMapping("/api/users/")
        public List<User> getAllUsers()  {
            return userDao.getAllUsers();
            }
       
        //Get all the users by FamilyID
        @GetMapping("/api/users/{familyid}") 
        public List<User> getAllUsersByFamilyId(@PathVariable int familyid) {
            return userDao.getAllUsersByFamilyId(familyid);
        }

        //Get a user by ID
        @GetMapping("/api/user/{id}") 
        public User getUserByID(@PathVariable int id) {
            return userDao.getUserByUserId(id);
        }

        // delete a user by ID
        @DeleteMapping("/api/user/{id}") 
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteUserByID(@PathVariable int id) {
            User existingUser = userDao.getUserByUserId(id);
            if (existingUser == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else{
                userDao.deleteUser(id);
            }
        }
    
          //Get all the children by FamilyID
          @GetMapping("/api/users/{role}/{familyid}") 
          public List<User> getFamiliesChildren(@PathVariable String role, @PathVariable int familyid) {
              return userDao.getFamiliesChildren(role, familyid);
          }
    
    }
