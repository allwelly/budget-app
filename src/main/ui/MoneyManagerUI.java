package ui;

import model.Account;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main UI that represents MoneyManagerApp
 *
 */

public class MoneyManagerUI extends JFrame {
    private static final int MARGIN = 36;
    private static final int WIDTH = 360;
    private static final int HEIGHT = 500;
    private static final int PANEL_WIDTH = WIDTH - (MARGIN * 2);
    private static final int TOP_PANEL_HEIGHT = HEIGHT / 4;
    private static final int BOTTOM_PANEL_HEIGHT = HEIGHT / 2;
    private static final String JSON_STORE = "./data/accountDetails.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Account user;
    private ActionHandler actionHandler;
    private JLabel balance;
    private JLabel savingGoalStatus;


    // EFFECTS: Constructs MoneyManagerUI class
    public MoneyManagerUI() {
        super();
        actionHandler = new ActionHandler();

        initializeFrame();
        initializeJson();
        user = new Account(0);

        initializeAccountDetails();

        initializeButtons();

        setVisible(true);

    }

    // EFFECTS: initialize MoneyManagerUI frame
    public void initializeFrame() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLayout(null);

        // referenced from
        // https://stackoverflow.com/questions/60516720/java-how-to-print-message-when-a-jframe-is-closed
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.toString());
                }

                System.exit(0);
            }

        });
    }

    // EFFECTS: initialize JSON fields
    public void initializeJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: initialize labels for account details on page
    public void initializeAccountDetails() {
        JPanel accountSummaryPanel = new JPanel();
        accountSummaryPanel.setBackground(Color.white);
        accountSummaryPanel.setBounds(MARGIN, MARGIN, PANEL_WIDTH, TOP_PANEL_HEIGHT);
        add(accountSummaryPanel);
        accountSummaryPanel.setLayout(new GridLayout(2,1));

        balance = new JLabel(" $" + user.getBalance());
        balance.setFont(new Font(Font.MONOSPACED, Font.BOLD, 32));
        accountSummaryPanel.add(balance);

        savingGoalStatus = new JLabel("  You have " + user.getSavingGoals().size() + " saving goals.");
        savingGoalStatus.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        accountSummaryPanel.add(savingGoalStatus);
    }

    // EFFECTS: initialize buttons for page
    public void initializeButtons() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.yellow);
        bottomPanel.setBounds(MARGIN, TOP_PANEL_HEIGHT + (MARGIN * 2), PANEL_WIDTH, BOTTOM_PANEL_HEIGHT);
        add(bottomPanel);
        bottomPanel.setLayout(new GridLayout(4, 1));

        JButton editBalanceButton = new JButton("Edit Balance");
        editBalanceButton.addActionListener(actionHandler);
        bottomPanel.add(editBalanceButton);

        JButton editSavingButton = new JButton("Edit Saving Goals");
        editSavingButton.addActionListener(actionHandler);
        bottomPanel.add(editSavingButton);

        JButton saveButton = new JButton("Save Account Details");
        saveButton.addActionListener(actionHandler);
        bottomPanel.add(saveButton);

        JButton loadButton = new JButton("Load Account Details");
        loadButton.addActionListener(actionHandler);
        bottomPanel.add(loadButton);
    }

    // EFFECTS: execute commands according to buttons clicked, update account details display
    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();

            switch (src.getText()) {
                case "Edit Balance":
                    editBalance();
                    break;
                case "Edit Saving Goals":
                    editSavingGoals();
                    break;
                case "Save Account Details":
                    saveAccountDetails();
                    break;
                case "Load Account Details":
                    loadAccountDetails();
                    break;
            }

            updateDisplay();
        }
    }

    // MODIFIES: this, user
    // EFFECTS: edit the account balance then display on ui
    public void editBalance() {
        new EditBalance(user, this);
    }

    // MODIFIES: this, user
    // EFFECTS: edit the saving goals then display on ui
    public void editSavingGoals() {
        new EditSavingGoals(user);
    }


    // EFFECTS: saves the account details to file
    public void saveAccountDetails() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved account details " + JSON_STORE);
            new SuccessPopup();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            new ErrorPopup();
        }
    }

    // MODIFIES: this, user
    // EFFECTS: loads account details from file, display on ui
    public void loadAccountDetails() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded account details from " + JSON_STORE);
            new SuccessPopup();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            new ErrorPopup();
        }
    }

    // EFFECTS: updates account details display
    public void updateDisplay() {
        balance.setText(" $" + user.getBalance());
        savingGoalStatus.setText("  You have " + user.getSavingGoals().size() + " saving goals.");
    }
}
