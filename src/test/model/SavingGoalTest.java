package model;

/**
 * Test for SavingGoal class
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingGoalTest {
    private SavingGoal goal1;

    @BeforeEach
    void testSavingGoal() {
        goal1 = new SavingGoal("Buy House", 12000);
    }

    @Test
    void testConstructor() {
        assertEquals("Buy House", goal1.getTitle());
        assertEquals(12000, goal1.getTarget());
        assertEquals(0, goal1.getMoneySaved());
    }

    @Test
    void testIncreaseTarget() {
        goal1.increaseTarget(200);
        assertEquals(12200, goal1.getTarget());
        goal1.increaseTarget(50);
        assertEquals(12250, goal1.getTarget());
    }

    @Test
    void testDecreaseTarget() {
        goal1.decreaseTarget(2000);
        assertEquals(10000, goal1.getTarget());
        goal1.decreaseTarget(10000);
        assertEquals(0, goal1.getTarget());
        goal1.decreaseTarget(11);
        assertEquals(0, goal1.getTarget());
    }

    @Test
    void testSave() {
        goal1.save(200);
        assertEquals(200, goal1.getMoneySaved());
        goal1.save(50);
        assertEquals(250, goal1.getMoneySaved());
    }

    @Test
    void testWithdraw() {
        goal1.withdraw(20);
        assertEquals(0, goal1.getMoneySaved());
        goal1.save(200);
        goal1.withdraw(30);
        assertEquals(170, goal1.getMoneySaved());
        goal1.withdraw(70);
        assertEquals(100, goal1.getMoneySaved());
    }

    @Test
    void testIsReachedFalse() {
        goal1.save(11999);
        assertFalse(goal1.isReached());
        assertEquals(11999, goal1.getMoneySaved());
    }

    @Test
    void testIsReachedTrue() {
        goal1.save(12000);
        assertTrue(goal1.isReached());
        assertEquals(12000, goal1.getMoneySaved());
        goal1.save(1000);
        assertTrue(goal1.isReached());
        assertEquals(13000, goal1.getMoneySaved());
    }
}
