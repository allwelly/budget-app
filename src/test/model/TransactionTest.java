package model;

/**
 * Test for Transaction class
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    Transaction spending;
    Transaction income;

    @BeforeEach
    void constructTransaction() {
        spending = new Transaction(false, 200, "Clothes");
    }

    @Test
    void testConstructor() {
        assertFalse(spending.getIsIncome());
        assertEquals(200, spending.getAmount());
        assertEquals("Clothes", spending.getCategory());
    }
}
