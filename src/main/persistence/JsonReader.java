package persistence;

import model.Account;
import model.SavingGoal;
import model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Represents a reader that reads workroom from JSON data stored in file
 * code is referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account details from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        int balance = jsonObject.getInt("balance");
        int wage = jsonObject.getInt("wage");
        Account acc = new Account(balance);
        acc.updateWage(wage);
        addTransactions(acc, jsonObject);
        addSavingGoals(acc, jsonObject);
        return acc;
    }

    // MODIFIES: acc
    // EFFECTS: parses transactions from JSON object and adds them to account
    private void addTransactions(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(acc, nextTransaction);
        }
    }

    // MODIFIES: acc
    // EFFECTS: parses transaction from JSON object and adds it to account
    private void addTransaction(Account acc, JSONObject jsonObject) {
        boolean isIncome = jsonObject.getBoolean("isIncome");
        int amount = jsonObject.getInt("amount");
        String category = jsonObject.getString("category");
        Transaction trans = new Transaction(isIncome, amount, category);
        acc.addTransactions(trans);
    }

    // MODIFIES: acc
    // EFFECTS: parses saving goals from JSON object and adds them to account
    private void addSavingGoals(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("savingGoals");
        for (Object json : jsonArray) {
            JSONObject nextSavingGoal = (JSONObject) json;
            addSavingGoal(acc, nextSavingGoal);
        }
    }

    // MODIFIES: acc
    // EFFECTS: parses saving goal from JSON object and adds it to workroom
    private void addSavingGoal(Account acc, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int target = jsonObject.getInt("target");
        int moneySaved = jsonObject.getInt("moneySaved");
        SavingGoal savingGoal = new SavingGoal(title, target);
        savingGoal.save(moneySaved);
        acc.addSavingGoal(savingGoal);
    }
}
