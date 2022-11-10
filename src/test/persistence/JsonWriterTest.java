package persistence;

import model.Account;
import model.SavingGoal;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for JsonWriter
 * code is referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */


public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Account acc = new Account(0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            Account acc = new Account(300);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals(Account.MINIMUM_WAGE_BC, acc.getWage());
            assertEquals(300, acc.getBalance());
            assertTrue(acc.getTransactions().isEmpty());
            assertTrue(acc.getSavingGoals().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            Account acc = new Account(140);
            acc.addTransactions(new Transaction(true, 200, "Deposit"));
            acc.addTransactions(new Transaction(false, 10, "Food"));
            acc.addTransactions(new Transaction(false, 50, "Deposit to saving goal"));
            acc.addSavingGoal(new SavingGoal("Retirement", 2000));
            acc.getSavingGoal("Retirement").save(50);
            acc.addSavingGoal(new SavingGoal("House", 400));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            acc = reader.read();
            assertEquals(Account.MINIMUM_WAGE_BC, acc.getWage());
            assertEquals(140, acc.getBalance());

            List<Transaction> transactions = acc.getTransactions();
            assertEquals(3, transactions.size());
            checkTransaction(true, 200, "Deposit", transactions.get(0));
            checkTransaction(false, 10, "Food", transactions.get(1));
            checkTransaction(false, 50, "Deposit to saving goal", transactions.get(2));

            List<SavingGoal> savingGoals = acc.getSavingGoals();
            assertEquals(2, savingGoals.size());
            checkSavingGoal("Retirement", 2000, 50, savingGoals.get(0));
            checkSavingGoal("House", 400, 0, savingGoals.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
