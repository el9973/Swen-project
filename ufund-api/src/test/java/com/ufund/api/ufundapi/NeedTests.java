package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Animal;
import com.ufund.api.ufundapi.model.Need;

public class NeedTests {
    
    @Test
    public void testConstructor() {
        Need n = new Need(1, "test", "test", (float)1.5, 1, new Animal("test", "test", "test"));
        assertNotNull(n);
    }

    @Test
    public void testAccessor() {
        Animal a = new Animal("test", "test", "test");
        Need n = new Need(1, "test", "test", (float)1.5, 1, a);
        assertEquals(n.getName(), "test");
        assertEquals(n.getType(), "test");
        assertEquals(n.getCost(), 1.5);
        assertEquals(n.getQuantity(), 1);
        assertEquals(n.getAnimal(), a);
    }

    @Test
    public void testMutator() {
        Need n = new Need(1, "test", "test", (float)1.5, 1, new Animal("test", "test", "test"));
        assertEquals(n.getQuantity(), 1);
        n.setQuantiy(5);
        assertEquals(n.getQuantity(), 5);
    }

    @Test
    public void testHashCode() {
        Need n = new Need(1, "test", "test", (float)1.5, 1, new Animal("test", "test", "test"));
        assertEquals(n.hashCode(), n.getName().hashCode());
    }

    
}
