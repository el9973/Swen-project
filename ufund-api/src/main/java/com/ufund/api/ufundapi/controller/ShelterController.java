package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ufund.api.ufundapi.model.Need;

//test json
//[{"id":4, "name":"Flea Medicine", "type":"Essential", "cost":40.99, "quantity":2, "animal":{"name":"Cat", "type":"Pet", "age":4}}]

@RestController
@RequestMapping("shelter")
public class ShelterController {
    private static final Logger LOG = Logger.getLogger(ShelterController.class.getName());
    private ShelterDAO shelterDAO;

    public ShelterController(ShelterDAO shelterDAO) {
        this.shelterDAO = shelterDAO;
    }

    @GetMapping("/needs/{id}")
    public ResponseEntity<Need> getNeed(@PathVariable int id) {
        LOG.info("GET /shelter/needs/" + id);
        try {
            if (shelterDAO.getNeed(id) != null) {
                Need n = shelterDAO.getNeed(id);
                return new ResponseEntity<Need>(n, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Need[]> getNeeds() {
        LOG.info("GET /shelter/");
        try {
            return new ResponseEntity<Need[]>(shelterDAO.getNeeds(), HttpStatus.OK);
        } 
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/needs/name")
    public ResponseEntity<Need[]> searchNeeds(@RequestParam String name) {
        LOG.info("GET /shelter/needs?name="+name);
        try {
            return new ResponseEntity<Need[]>(shelterDAO.findNeeds(name), HttpStatus.OK);
        } 
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/needs")
    public ResponseEntity<Need> createNeed(@RequestBody Need n) {
        LOG.info("POST /shelter/needs/" + n);
        // check if it already exists
        // try making it
        try {
            shelterDAO.createNeed(n);
            return new ResponseEntity<Need>(n, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/needs/{need}")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /shelter/needs/ " + need);
        try {
            Need n = shelterDAO.updateNeed(need);
            if (n == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Need>(n, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/needs/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable int id) {
        LOG.info("DELETE /shelter/needs/" + id);
        try {
            Need n = shelterDAO.getNeed(id);
            shelterDAO.deleteNeed(n);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e ) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Adds a need to a user's basket
     * @param n Need ID
     * @param user Username
     * @return ReponseEntity OK, CONFLICT or INTERNAL_SERVER_ERROR
     */
    @PostMapping("/needs/{user}/{id}")
    public ResponseEntity<Need> addNeedToBasket(@RequestBody int n, @RequestBody String user) {
        LOG.info("POST /shelter/needs/{user}/" + n);
        try {
            if(shelterDAO.addNeedtoBasket(n, user)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Removes a need from a user's basket
     * @param n Need ID
     * @param user Username
     * @return ReponseEntity OK, CONFLICT or INTERNAL_SERVER_ERROR
     */
    @DeleteMapping("/needs/{user}/{id}")
    public ResponseEntity<Need> removeNeedfromBasket(@RequestBody int n, @RequestBody String user) {
        LOG.info("DELETE /shelter/needs/{user}/" + n);
        try {
            if(shelterDAO.removeNeedfromBasket(n, user)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Checks out the user
     * @param n Need ID
     * @param user Username
     * @return ReponseEntity OK, CONFLICT or INTERNAL_SERVER_ERROR
     */
    @PostMapping("/needs/{user}/checkout")
    public ResponseEntity<Need> removeNeedfromBasket(@RequestBody String user) {
        LOG.info("CHECKOUT /shelter/needs/{user}/checkout");
        try {
            if(shelterDAO.checkout(user)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Gets a need from the users basket
     * @param n Need ID
     * @param user Username
     * @return ReponseEntity OK, NOT_FOUND or INTERNAL_SERVER_ERROR
     */
    @GetMapping("/needs/{user}")
    public ResponseEntity<Need[]> getBasket(@RequestBody String user) {
        LOG.info("GET /shelter/{user}");
        try {
            return new ResponseEntity<Need[]>(shelterDAO.getBasket(user), HttpStatus.OK);
        } 
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
