package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.book.Book;
import com.techelevator.model.book.BookDao;
import com.techelevator.model.prize.Award;
import com.techelevator.model.prize.Prize;
import com.techelevator.model.prize.PrizeDao;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/api/")
public class PrizeRestController {

    private PrizeDao prizeDao;
    
    @Autowired
    public PrizeRestController(PrizeDao prizeDao){
        this.prizeDao = prizeDao; 
        }

    // create a new prize
    @PostMapping("prize/")
    @ResponseStatus(HttpStatus.CREATED)
    public Prize CreatePrize(@Valid @RequestBody Prize newPrize, BindingResult result){
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error creating prize-needs more work.");
        }
        return prizeDao.createPrize(newPrize);
    }

   // updates a prize
   @PutMapping("prize/{id}")
   public void UpdatePrize(@PathVariable int id, @Valid @RequestBody Prize prizeToUpdate, BindingResult result){
        Prize existingPrize = prizeDao.getPrize(id);

        if (existingPrize == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else{
            prizeDao.updatePrize(prizeToUpdate);
        }
       
   }

    //get all the prizes by family ID
    @GetMapping("prizes/{id}") 
    public List<Prize>  getAllPrizesByFamilyID(@PathVariable int id) {
        return prizeDao.getAllPrizesByFamilyId(id);
    }
    
    
    //get a prize by ID
    @GetMapping("prize/{id}") 
    public Prize getPrizeByID(@PathVariable int id) {
        return prizeDao.getPrize(id);
    }
    
    // deletes the prize by ID
    @PutMapping("prize/remove/{id}") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePrizeByID(@PathVariable int id) {
        Prize existingPrize = prizeDao.getPrize(id);
        if (existingPrize == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else{
            prizeDao.removePrizeById(id);
        }
    }

    @PutMapping("prize/claim/{prizeId}")
    public void updateClaimedPrize(@PathVariable int prizeId){
        prizeDao.updateClaimedPrize(prizeId);
    }
    
    @PostMapping("awardedprize/{prizeId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public long CreatePrizeAward(@PathVariable int prizeId, @PathVariable int userId){
        return prizeDao.createPrizeUser(prizeId, userId);
    }

    @GetMapping("userawardedprizes/{userId}") 
    public List<Prize>  getAllAwardedPrizesByID(@PathVariable int userId) {
        return prizeDao.getPrizesByUserId(userId);
    }

    @GetMapping("familyawardedprizes/{familyId}") 
    public List<Award>  getAllAwardedPrizesByFamilyID(@PathVariable int familyId) {
        return prizeDao.getPrizesByFamilyId(familyId);
    }

}