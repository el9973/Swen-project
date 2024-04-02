package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.controller.ShelterFileDAO;
import com.ufund.api.ufundapi.controller.ShelterController;
import com.ufund.api.ufundapi.model.Animal;
import com.ufund.api.ufundapi.model.Need;

public class ShelterControllerTests {
    private ShelterFileDAO shelterDAO;
    private Need[] testNeeds;
    private ObjectMapper mapper;
    ShelterController controller;

    @BeforeEach
    public void setupShelter() throws IOException {
        this.testNeeds = new Need[3];
        for (int i = 0; i < 3; i ++) {
            this.testNeeds[i] = new Need(i, "example need", "example", (float) 15.00, 5, new Animal("test", "test", "test"));
        }
        this.mapper = mock(ObjectMapper.class);
        when(mapper.readValue(new File("irrelevant.txt"), Need[].class)).thenReturn(this.testNeeds);
        this.shelterDAO = new ShelterFileDAO("irrelevant.txt", this.mapper);
        this.controller = new ShelterController(shelterDAO);
    }

    @Test
    public void testGetDAO() throws Exception {
        assertNotNull(shelterDAO.getNeed(0));
        assertNull(shelterDAO.getNeed(5));
    }
    // testDelete written by Jacob Lurvey
    @Test
    public void testDeleteDAO() throws Exception {
        shelterDAO.createNeed(new Need(3, "TestNeed", "Testing", 12.0f, 1, new Animal()));
        assertNotNull(shelterDAO.getNeed(3));
        shelterDAO.deleteNeed(3);
        assertNull(shelterDAO.getNeed(3));
    }

    @Test
    public void testPostDAO() throws Exception {
        assertNull(shelterDAO.getNeed(3));
        ///controller.createNeed(new Need(3, "TestNeed1234567890", "example", (float) 15.00, 5, new Animal("test", "test", "test")));
        shelterDAO.createNeed(new Need(3, "TestNeed1234567890", "example", (float) 15.00, 5, new Animal("test", "test", "test")));
        assertNotNull(shelterDAO.getNeed(3));
    }

    @Test
    public void testAddToBasketDAO() throws IOException {
        shelterDAO.createNeed(new Need(3, "TestNeed", "Testing", 12.0f, 1, new Animal()));
        boolean res = shelterDAO.addNeedtoBasket(3, "test");
        assertTrue(res);
        boolean newRes = shelterDAO.addNeedtoBasket(3, "test");
        assertFalse(newRes);
    }

    @Test
    public void testRemoveFromBasketDAO() throws IOException {
        shelterDAO.createNeed(new Need(3, "TestNeed", "Testing", 12.0f, 1, new Animal()));
        shelterDAO.addNeedtoBasket(3, "test");
        boolean res = shelterDAO.removeNeedfromBasket(3, "test");
        assertFalse(res);
        boolean newRes = shelterDAO.removeNeedfromBasket(3, "test");
        assertFalse(newRes);
    }

    @Test
    public void testCheckoutDAO() throws IOException {
        shelterDAO.createNeed(new Need(3, "TestNeed", "Testing", 12.0f, 1, new Animal()));
        shelterDAO.addNeedtoBasket(3, "test");
        boolean res = shelterDAO.checkout("nonexistent");
        assertFalse(res);
        boolean newRes = shelterDAO.checkout("test");
        assertTrue(newRes);
    }

    @Test
    public void testViewCupboard() {
        Need[] contents = shelterDAO.viewCupboard("admin");
        Need[] noContents = shelterDAO.viewCupboard("nonadmin");
        assertNotNull(contents);
        assertNull(noContents);
    }

    @Test
    public void testPutDAO() throws Exception {
        Need n = new Need(3, "TestNeed", "example", (float) 15.00, 5, new Animal("test", "test", "test"));
        shelterDAO.createNeed(n);
        assertNotNull(shelterDAO.getNeed(3));
        assertEquals((float) 15.00, shelterDAO.getNeed(3).getCost());
        n.setCost((float) 20.00);
        shelterDAO.updateNeed(n);
        assertEquals((float) 20.00, shelterDAO.getNeed(3).getCost());
    }

    @Test
    public void testGetController() throws Exception {
        ResponseEntity<Need> realNeed = controller.getNeed(0);
        ResponseEntity<Need> nullNeed = controller.getNeed(99);
        assertEquals(realNeed.getStatusCode(), HttpStatus.OK);
        assertEquals(nullNeed.getStatusCode(), HttpStatus.NOT_FOUND);
        when(controller.getNeed(10)).thenThrow(new IOException());
    }

    @Test
    public void testCreateAndDeleteController() throws Exception {
        Need n = new Need(3, "test", "testNeed", 15, 5, new Animal()); //null animal
        controller.createNeed(n);
        assertNotNull(controller.getNeed(3));
        controller.deleteNeed(3);
        assertEquals(controller.getNeed(3).getStatusCode().value(), HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testUpdateNeed() throws Exception {
        Need n = new Need(3, "test", "testNeed", 15, 5, new Animal()); //null animal
        controller.createNeed(n);
        n.setCost((float)150); //null animal
        controller.updateNeed(n);
        float retrievedCost = controller.getNeed(3).getBody().getCost();
        assertEquals(retrievedCost, (float)150);
        ResponseEntity<Need> nullUpdate = controller.updateNeed(null);
        assertEquals(nullUpdate.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testSearch() throws Exception {
        Need n = new Need(3, "searchNeed", "testNeed", 15, 5, new Animal()); //null animal
        controller.createNeed(n);
        ResponseEntity<Need[]> need = controller.searchNeeds("search");
        assertEquals(need.getBody()[0].getName(), "searchNeed");
        ResponseEntity<Need[]> noNeeds = controller.searchNeeds("no needs to see here");
        assertEquals(noNeeds.getBody().length, 0);
    }

    @Test
    public void testGetNeeds() throws Exception {
        ResponseEntity<Need[]> needs = controller.getNeeds();
        assertEquals(needs.getBody().length, 3);
    }

    @Test
    public void testAddToBasketController() {
        Need n = new Need(3, "test", "testNeed", 15, 5, new Animal()); //null animal
        controller.createNeed(n);
        ResponseEntity<Need> okay = controller.addNeedToBasket(3, "admin");
        assertEquals(okay.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testRemoveFromBasketController() {
        Need n = new Need(3, "test", "testNeed", 15, 5, new Animal()); //null animal
        controller.createNeed(n);
        controller.addNeedToBasket(3, "admin");
        ResponseEntity<Need> okay = controller.removeNeedfromBasket(3, "admin");
        assertEquals(okay.getStatusCode(), HttpStatus.NOT_FOUND); 
    }

    @Test
    public void testCheckoutController() {
        Need n = new Need(3, "test", "testNeed", 15, 5, new Animal()); //null animal
        controller.createNeed(n);
        controller.addNeedToBasket(3, "admin");
        ResponseEntity<Need> res = controller.removeNeedfromBasket("admin");
        assertEquals(res.getStatusCode(), HttpStatus.OK);
        ResponseEntity<Need> newRes = controller.removeNeedfromBasket("notfound");
        assertEquals(newRes.getStatusCode(), HttpStatus.NOT_FOUND);
        
    }
 
    

}
