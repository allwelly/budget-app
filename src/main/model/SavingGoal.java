package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represent individual saving goals
 */

public class SavingGoal implements Writable {
    private String title;
    private int target;
    private int moneySaved = 0;

    // REQUIRES: target > 100
    // EFFECTS: constructs project with given title and target
    public SavingGoal(String title, int target) {
        this.title = title;
        this.target = target;
    }

    // REQUIRES: amount > 1
    // EFFECTS: increase target by amount
    // MODIFIES: this
    public void increaseTarget(int amount) {
        target += amount;
    }

    // REQUIRES: amount <= getTarget() and amount > 1
    // EFFECTS: decrease target by amount
    // MODIFIES: this
    public void decreaseTarget(int amount) {
        if (amount <= target) {
            target -= amount;
        }
    }

    // REQUIRES: amount > 1
    // EFFECTS: adds
    public void save(int amount) {
        moneySaved += amount;
    }

    // REQUIRES: amount <= getMoneySaved()
    // EFFECTS: takes money out of the saving
    public void withdraw(int amount) {
        if (amount <= moneySaved) {
            moneySaved -= amount;
        }
    }

    // EFFECTS: return true is saving goal is reached, else false
    public boolean isReached() {
        if (moneySaved >= target) {
            return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public int getTarget() {
        return target;
    }

    public int getMoneySaved() {
        return moneySaved;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("target", target);
        json.put("moneySaved", moneySaved);
        return json;
    }
}
