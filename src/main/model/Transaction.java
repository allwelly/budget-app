package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represent individual transactions made within the Account
 */

public class Transaction implements Writable {
    private boolean isIncome;
    private int amount;
    private String category;

    // REQUIRES: amount > 1
    // EFFECTS: constructs project with given amount, category,
    // and isIncome is false if it's spending/saving, true if its deposit/withdrawal from saving.
    // The amount is negative if it's money out of the account,
    // and positive if money goes into the account.
    public Transaction(boolean isIncome, int amount, String category) {
        this.isIncome = isIncome;
        this.amount = amount;
        this.category = category;
    }

    public boolean getIsIncome() {
        return isIncome;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("isIncome", isIncome);
        json.put("amount", amount);
        json.put("category", category);
        return json;
    }
}
