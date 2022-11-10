package persistence;

import model.Account;
import model.SavingGoal;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for JsonReader
 * code is referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account acc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account acc = reader.read();
            assertEquals(18, acc.getWage());
            assertEquals(0, acc.getBalance());
            assertTrue(acc.getTransactions().isEmpty());
            assertTrue(acc.getSavingGoals().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccount() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account acc = reader.read();
            assertEquals(15, acc.getWage());
            assertEquals(2000, acc.getBalance());

            List<Transaction> transactions = acc.getTransactions();
            checkTransaction(true, 2150, "Deposit", transactions.get(0));
            checkTransaction(false, 100, "Clothes", transactions.get(1));
            checkTransaction(false, 50, "Deposit to saving goal", transactions.get(2));

            List<SavingGoal> savingGoals = acc.getSavingGoals();
            checkSavingGoal("Birthday", 200, 50, savingGoals.get(0));
            checkSavingGoal("House", 4000, 0, savingGoals.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
