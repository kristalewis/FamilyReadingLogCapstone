package com.techelevator.controller;

import javax.validation.Valid;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.JwtTokenHandler;
import com.techelevator.authentication.RegistrationResult;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.login.User;
import com.techelevator.model.login.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

/**
 * AccountController
 */
@CrossOrigin
@RestController
public class AccountController {
    @Autowired
    private AuthProvider auth;
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtTokenHandler tokenHandler;

    @PostMapping("/login")
    public String login(@RequestBody User user) throws UnauthorizedException {
        if(auth.signIn(user.getUsername(), user.getPassword())) {
            User currentUser = auth.getCurrentUser();
            return tokenHandler.createToken(currentUser);
        } else {
            throw new UnauthorizedException();
        }
    }


// Steve S. 4/13/2020 added check if username exists. if so genrate error.
    @PostMapping("/register")
    public RegistrationResult register(@Valid @RequestBody User user, BindingResult result) {
    	RegistrationResult registrationResult = new RegistrationResult();
    	if(result.hasErrors()) {
            for(ObjectError error : result.getAllErrors()) {
                registrationResult.addError(error.getDefaultMessage());
            }
        // determine if username is already taken.
        } else if (userDao.getUserByUsername(user.getUsername()) != null) {
            registrationResult.addError(user.getUsername() + " username already exists! Please choose a different username.");
        } else {
    		auth.register(user.getUsername(), user.getPassword(), user.getRole(), user.getFamilyId());
    		registrationResult.setSuccess(true);
    	}
    	return registrationResult;
    }



}