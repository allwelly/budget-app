package persistence;

import model.SavingGoal;
import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for each transaction and saving goal
 * code is referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonTest {
    protected void checkTransaction(boolean isIncome, int amount, String category, Transaction trans) {
        assertEquals(isIncome, trans.getIsIncome());
        assertEquals(amount, trans.getAmount());
        assertEquals(category, trans.getCategory());
    }

    protected void checkSavingGoal(String title, int target, int moneySaved, SavingGoal savingGoal) {
        assertEquals(title, savingGoal.getTitle());
        assertEquals(target, savingGoal.getTarget());
        assertEquals(moneySaved, savingGoal.getMoneySaved());
    }
}
