package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.users.Helper;
import com.ufund.api.ufundapi.model.users.Manager;
import com.ufund.api.ufundapi.model.users.Visitor;

public class UsersTests {
    
    @Test
    public void testConstructor() {
        Helper h = new Helper("helper");
        Manager m = new Manager();
        Visitor v = new Visitor("visitor");
        assertNotNull(h);
        assertNotNull(v);
        assertNotNull(m);
    }

    @Test
    public void testAccessors() {
        Helper h = new Helper("helper");
        Manager m = new Manager();
        Visitor v = new Visitor("visitor");
        assertEquals(h.getUserName(), "helper");
        assertEquals(m.getUserName(), "admin");
        assertEquals(v.getUserName(), "visitor");
    }
}
