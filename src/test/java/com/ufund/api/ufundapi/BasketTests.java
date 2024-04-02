package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Animal;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.basket.Basket;

public class BasketTests {
    
    @Test
    public void testConstructor() {
        Basket b1 = new Basket();
        ArrayList<Need> needs = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            needs.add(new Need(i, Integer.toString(i), "visit", (float)(i * 1.5), 1, new Animal("test", "test", "test")));
        }
        Basket b2 = new Basket(needs);
        assertNotNull(b1);
        assertNotNull(b2);
    }

    @Test
    public void testAccessor() {
        Basket b1 = new Basket();
        Need n = new Need(1, "test", "visit", (float)1.5, 1, new Animal("test", "test", "test"));
        b1.add(n);
        assertEquals(b1.size(), 1);
        b1.remove(n);
        assertEquals(b1.size(), 0);
    }

    @Test
    public void testTotal() {
        Basket b1 = new Basket();
        for (int i = 0; i < 10; i ++) {
            b1.add(new Need(i, Integer.toString(i), "visit", (float)(i * 2), 1, new Animal("test", "test", "test")));
        }
        assertEquals(b1.totalCost(), 90);
    }

    @Test
    public void testToString() {
        Basket b = new Basket();
        String basketString = b.toString();
        String expected = "Cost: $0.0\n[]";
        assertEquals(basketString, expected);
    }

    @Test
    public void testGetNeeds() {
        Basket b = new Basket();
        assertEquals(b.getNeeds().size(), 0);
        for (int i = 0; i < 10; i ++) {
            b.add(new Need(i, Integer.toString(i), "visit", (float)(i * 2), 1, new Animal("test", "test", "test")));
        }
        assertEquals(b.getNeeds().size(), 10);
    }

    @Test
    public void testSearch() {
        Basket b = new Basket();
        Need n = new Need(99, null, null, 0, 0, null);
        Need notInBasket = new Need(999, null, null, 0, 0, null);

        for (int i = 0; i < 10; i ++) {
            b.add(new Need(i, Integer.toString(i), "visit", (float)(i * 2), 1, new Animal("test", "test", "test")));
        }
        b.add(n);
        assertTrue(b.search(n));
        assertFalse(b.search(notInBasket));

    }
}
