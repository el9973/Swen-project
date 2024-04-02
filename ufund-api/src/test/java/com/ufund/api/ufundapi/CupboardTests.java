package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Animal;
import com.ufund.api.ufundapi.model.Cupboard;
import com.ufund.api.ufundapi.model.Need;

public class CupboardTests {
    
    @Test
    public void testConstructor() {
        Cupboard c = new Cupboard();
        assertNotNull(c);
    }

    @Test
    public void testAddAndGet() {
        Cupboard c = new Cupboard();
        Need n = new Need(1, "test", "test", (float)1.5, 1, new Animal("test", "test", "test"));
        c.addNeed(n);
        assertEquals(c.getSpecificNeed("test"), n);
        assertEquals(c.getCupboard().size(), 1);
    }

    @Test
    public void testGetSubNeeds() {
        Cupboard c = new Cupboard();
        for (int i = 0; i < 11; i ++) {
            c.addNeed(new Need(i, Integer.toString(i), "visit", (float)(i * 1.5), 1, new Animal("test", "test", "test")));
        }
        List<Need> subNeeds = c.getSubNeeds("1");
        assertEquals(subNeeds.size(), 2);
        List<Need> noSubNeeds = c.getSubNeeds("a;sfdkjasldfh;a;sjfd");
        assertEquals(noSubNeeds.size(), 0);
    }

    @Test
    public void testDeleteNeed() {
        Cupboard c = new Cupboard();
        Need n = new Need(100, "tobedeleted", null, (float)100, 1, null);
        for (int i = 0; i < 11; i ++) {
            c.addNeed(new Need(i, Integer.toString(i), "visit", (float)(i * 1.5), 1, new Animal("test", "test", "test")));
        }
        c.addNeed(n);
        assertEquals(c.getCupboard().size(), 12);
        c.deleteNeed(n);
        assertEquals(c.getCupboard().size(), 11);


    }
}
