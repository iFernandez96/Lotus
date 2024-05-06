package com.example.lotus;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * CreateUserActivityTest is the test class for CreateUserActivity.
 */
public class ExampleUnitTest {

    private CreateUserActivity createUserActivity;

    @Before
    public void setUp() {
        createUserActivity = new CreateUserActivity();
    }

    @Test
    public void testInsertUserRecord() {
        // Test case 1: User already exists
        assertFalse(createUserActivity.insertUserRecord("testUser01", "testEmail", "testUser01"));

        // Test case 2: Password is empty
        assertFalse(createUserActivity.insertUserRecord("testUser", "testEmail", ""));

        // Test case 3: User does not exist and password is not empty
        assertTrue(createUserActivity.insertUserRecord("testUser", "testEmail", "testPassword"));

        // Test case 4: User does not exist and password is empty
        assertFalse(createUserActivity.insertUserRecord("testUser", "testEmail", ""));

        // Test case 5: Username is empty
        assertFalse(createUserActivity.insertUserRecord("", "testEmail", "testPassword"));
    }

}