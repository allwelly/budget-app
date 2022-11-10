package model;

/**
 * Test for Account
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.font.TransformAttribute;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account user;
    private SavingGoal goal1;
    private SavingGoal goal2;

    @BeforeEach
    void testAccount() {
        user = new Account(1000);
        goal1 = new SavingGoal("Buy House", 2020);
        goal2 = new SavingGoal("Retirement", 30000);
    }

    @Test
    void testConstructor() {
        assertEquals(1000, user.getBalance());
        assertEquals(15, user.getWage());
        assertTrue(user.getSavingGoals().isEmpty());
        // assertTrue(user.getTransactions().isEmpty());
    }

    @Test
    void testAddSavingGoal() {
        user.addSavingGoal(goal1);
        assertEquals(user.getSavingGoals().size(), 1);
        assertEquals(user.getSavingGoal("Buy House").getTitle(), "Buy House");
        assertEquals(user.getSavingGoal("Buy House").getTarget(), 2020);
        user.addSavingGoal(goal2);
        assertEquals(user.getSavingGoals().size(), 2);
        assertEquals(user.getSavingGoal("Retirement").getTitle(), "Retirement");
        assertEquals(user.getSavingGoal("Retirement").getTarget(), 30000);
    }

    @Test
    void testDeleteSavingGoal() {
        user.addSavingGoal(goal1);
        user.addSavingGoal(goal2);
        user.deleteSavingGoal("Buy House");
        assertEquals(user.getSavingGoals().size(), 1);
        assertNull(user.getSavingGoal("Buy House"));
        user.deleteSavingGoal("Build Ice Rink");
        assertEquals(user.getSavingGoals().size(), 1);
        assertNull(user.getSavingGoal("Buy House"));
    }

    @Test
    void testAddBalance() {
        user.addBalance(200);
        assertEquals(1200, user.getBalance());
        user.addBalance(50);
        assertEquals(1250, user.getBalance());
    }

    @Test
    void testSubtractBalance() {
        user.subtractBalance(20);
        assertEquals(980, user.getBalance());
        user.subtractBalance(900);
        assertEquals(80, user.getBalance());
        user.subtractBalance(100);
        assertEquals(80, user.getBalance());
    }

    @Test
    void testUpdateWage() {
        user.updateWage(Account.MINIMUM_WAGE_BC + 1);
        assertEquals(Account.MINIMUM_WAGE_BC + 1, user.getWage());
    }

    @Test
    void testAddTransaction() {
        Transaction spending = new Transaction(false, 200, "Clothes");
        Transaction income = new Transaction(true, 3000, "Saving");
        user.addTransactions(spending);
        assertEquals(1, user.getTransactions().size());
        user.addTransactions(income);
        assertEquals(2, user.getTransactions().size());
    }
}