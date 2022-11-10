package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/**
 * Represent individual saving goals
 * writable code implementation was referenced
 * from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class Account implements Writable {
    public static final int MINIMUM_WAGE_BC = 15;


    private int wage = MINIMUM_WAGE_BC;
    private int balance;
    private ArrayList<SavingGoal> savingGoals = new ArrayList<SavingGoal>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    // REQUIRES: initialBalance >= 1
    // EFFECTS: constructs project with given initial balance and wage,
    // empty list of transaction and saving goals initiated
    public Account(int initialBalance) {
        balance = initialBalance;
    }

    // EFFECTS: creates a saving goal with given name, target, and
    // initial saving, adds it to the list of saving goals, logs the event to event log
    // MODIFIES: this
    public void addSavingGoal(SavingGoal savingGoal) {
        savingGoals.add(savingGoal);

        EventLog.getInstance().logEvent(new Event(savingGoal.getTitle()
                + " was added to the list of saving goals"));
    }

    // MODIFIES: this
    // EFFECTS: add transaction to list of transactions
    public void addTransactions(Transaction trans) {
        transactions.add(trans);
    }

    // EFFECTS: deletes the selected saving goal from lists of saving goals
    // MODIFIES: this
    public void deleteSavingGoal(String title) {
        SavingGoal goal = getSavingGoal(title);
        if (goal != null) {
            savingGoals.remove(goal);
        }
    }

    // REQUIRES: amount >= 15
    // MODIFIES: this
    // EFFECTS: updates this to wage
    public void updateWage(int wage) {
        this.wage = wage;
    }

    // REQUIRES: amount > 1
    // EFFECTS: adds money to account
    public void addBalance(int amount) {
        balance += amount;
    }

    // REQUIRES: amount > 1 && amount <= getBalance()
    // EFFECTS: takes money out of the account
    public void subtractBalance(int amount) {
        if (amount <= balance) {
            balance -= amount;
        }
    }

    public SavingGoal getSavingGoal(String title) {
        for (SavingGoal goal: savingGoals) {
            if (goal.getTitle().equals(title)) {
                EventLog.getInstance().logEvent(new Event("Information about the saving goal "
                        + goal.getTitle()
                        + " was accessed by the user"));
                return goal;
            }
        }
        return null;
    }

    public int getWage() {
        return wage;
    }

    public int getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<SavingGoal> getSavingGoals() {
        return savingGoals;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", balance);
        json.put("wage", wage);
        json.put("transactions", transactionsToJson());
        json.put("savingGoals", savingGoalsToJson());
        return json;
    }

    // EFFECTS: returns transactions in this account as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns saving goals in this account as a JSON array
    private JSONArray savingGoalsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SavingGoal sg : savingGoals) {
            jsonArray.put(sg.toJson());
        }

        return jsonArray;
    }
}
