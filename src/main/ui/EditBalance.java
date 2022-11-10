package ui;

import model.Account;
import model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Displays a popup for user to withdraw/deposit money from balance
 */

public class EditBalance extends JFrame {
    private static final int WIDTH = 360;
    private static final int HEIGHT = 270;
    private static final int MARGIN = 36;
    private static final int PANEL_WIDTH = WIDTH - (MARGIN * 2);
    private static final int PANEL_HEIGHT = (HEIGHT / 2) - (MARGIN * 2);

    private EditBalance.ActionHandler actionHandler;
    private Account user;
    private MoneyManagerUI ui;
    private JTextField inputArea;
    private JLabel inputLabel;

    // EFFECTS: constructs edit balance frame which given user and ui
    public EditBalance(Account user, MoneyManagerUI ui) {

        super();
        actionHandler = new ActionHandler();

        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLayout(null);

        this.user = user;
        this.ui = ui;

        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(MARGIN, MARGIN, PANEL_WIDTH, PANEL_HEIGHT);
        add(inputPanel);
        inputPanel.setLayout(new GridLayout(2, 1));

        // LABEL
        inputLabel = new JLabel("Enter amount (to the nearest int):");
        inputPanel.add(inputLabel);

        // TEXT FIELD
        inputArea = new JTextField((WIDTH / 2) - (MARGIN * 2));
        inputPanel.add(inputArea);

        // PANEL
        JPanel optionPanel = new JPanel();
        optionPanel.setBounds(MARGIN, (PANEL_HEIGHT + MARGIN), PANEL_WIDTH, PANEL_HEIGHT);
        add(optionPanel);
        optionPanel.setLayout(new GridLayout(1, 2));

        initializeButtons(optionPanel);

        setVisible(true);
    }

    // EFFECTS: initialize buttons on the frame
    public void initializeButtons(JPanel optionPanel) {
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(actionHandler);
        optionPanel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(actionHandler);
        optionPanel.add(withdrawButton);
    }

    // MODIFIES: user, ui
    // EFFECTS: constructs edit balance frame which given user and ui
    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();
            int amount = Integer.parseInt(inputArea.getText());

            switch (src.getText()) {
                case "Deposit":
                    // add balance to account
                    user.addBalance(amount);
                    // add new transaction to transaction history
                    Transaction deposit = new Transaction(true, amount, "Deposit");
                    user.addTransactions(deposit);
                    dispose();
                    break;
                case "Withdraw":
                    user.subtractBalance(amount);
                    Transaction spending = new Transaction(false, amount, "Withdrawn");
                    user.addTransactions(spending);
                    int workingHours = Math.round(amount / user.getWage());
                    System.out.println("You have withdrawn the equivalent of " + workingHours
                            + " working hours.");
                    dispose();
            }

            ui.updateDisplay();
        }
    }
}

