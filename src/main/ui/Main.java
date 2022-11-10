package ui;

import model.Event;
import model.EventLog;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        MoneyManagerUI ui = new MoneyManagerUI();

        for (Event e : EventLog.getInstance()) {
            System.out.println(e.toString());
        }
    }
}
