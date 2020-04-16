package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import com.techelevator.model.family.Family;
import com.techelevator.model.family.FamilyDao;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/api/families")
public class FamilyRestController {

    private FamilyDao familyDao;
    
    @Autowired
    public FamilyRestController(FamilyDao familyDao){
        this.familyDao = familyDao; 
        }

    // Get all the families
    @GetMapping("/")
    public List<Family> getAllfamilies()  {
        return familyDao.getAllfamilies();
        }
    // create a new family
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Family CreateFamily(@Valid @RequestBody Family newFamily, BindingResult result){
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Family name is required.");
        }
        return familyDao.saveFamily(newFamily.getFamilyname());
    }

    //get a family by ID
    @GetMapping("/{id}") 
    public Family getfamilybyID(@PathVariable int id) {
        return familyDao.getFamilyById(id);
    }
    
    // deletes the family by ID
    @DeleteMapping("/{id}") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletefamilybyID(@PathVariable int id) {
        Family existingFamily = familyDao.getFamilyById(id);
        if (existingFamily == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else{
            familyDao.deleteFamilyById(id);
        }
    }


}