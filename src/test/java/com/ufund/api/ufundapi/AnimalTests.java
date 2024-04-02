package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Animal;

public class AnimalTests {
    
    @Test
    public void testConstructor() {
        Animal a = new Animal("test", "test", "test");
        assertNotNull(a);
    }

    @Test
    public void testAccessors() {
        Animal a = new Animal("test1", "test2", "test3");
        assertEquals(a.getName(), "test1");
        assertEquals(a.getSpecie(), "test2");
        assertEquals(a.getLocation(), "test3");
    }
}
