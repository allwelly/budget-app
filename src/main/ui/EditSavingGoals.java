package ui;

import model.Account;
import model.SavingGoal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * displays a popup containing lists of saving goals, and buttons to add and view saving goals
 */

public class EditSavingGoals extends JFrame {
    private static final int MARGIN = 36;
    private static final int WIDTH = 360;
    private static final int HEIGHT = 500;
    private static final int PANEL_WIDTH = WIDTH - (MARGIN * 2);
    private static final int PANEL_HEIGHT = HEIGHT - (MARGIN * 2);

    private EditSavingGoals.ActionHandler actionHandler;
    private DefaultListModel<String> model;
    private JList<String> savingGoals;
    private Account user;

    private JTextField nameArea;
    private JLabel nameLabel;
    private JTextField amountArea;
    private JLabel amountLabel;

    // EFFECTS: constructs an edit saving goal frame with the given user and ui
    public EditSavingGoals(Account user) {
        super();
        actionHandler = new ActionHandler();

        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLayout(null);

        this.user = user;

        JPanel savingGoalsPanel = new JPanel();
        savingGoalsPanel.setBounds(MARGIN, MARGIN, PANEL_WIDTH, PANEL_HEIGHT);
        add(savingGoalsPanel);
        savingGoalsPanel.setLayout(new GridLayout(3, 1));


        initializeList(savingGoalsPanel);

        initializeTextArea(savingGoalsPanel);

        initializeButtons(savingGoalsPanel);

        setVisible(true);
    }

    // EFFECTS: initialize buttons for edit saving goals frame
    private void initializeButtons(JPanel savingGoalsPanel) {
        // BUTTONS
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.yellow);
        buttonsPanel.setBounds(MARGIN, MARGIN + PANEL_HEIGHT, PANEL_WIDTH, PANEL_HEIGHT / 3);
        savingGoalsPanel.add(buttonsPanel);
        buttonsPanel.setLayout(new GridLayout(2, 1));

        JButton createButton = new JButton("Create saving goal");
        createButton.addActionListener(actionHandler);
        buttonsPanel.add(createButton);

        JButton selectButton = new JButton("Select saving goal");
        selectButton.addActionListener(actionHandler);
        buttonsPanel.add(selectButton);
    }

    // EFFECTS: initialize text area for edit saving goals frame
    private void initializeTextArea(JPanel savingGoalsPanel) {
        // TEXT INPUT
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setBounds(MARGIN, MARGIN + PANEL_HEIGHT, PANEL_WIDTH, PANEL_HEIGHT / 3);
        savingGoalsPanel.add(textAreaPanel);
        textAreaPanel.setLayout(new GridLayout(4, 1));

        // NAME
        nameLabel = new JLabel("Enter saving goal name (case sensitive):");
        textAreaPanel.add(nameLabel);
        nameArea = new JTextField((WIDTH / 2) - (MARGIN * 2));
        textAreaPanel.add(nameArea);

        // AMOUNT/TARGET
        amountLabel = new JLabel("Enter amount (to the nearest integer):");
        textAreaPanel.add(amountLabel);
        amountArea = new JTextField((WIDTH / 2) - (MARGIN * 2));
        textAreaPanel.add(amountArea);
    }

    // EFFECTS: initialize list view for edit saving goals frame
    private void initializeList(JPanel savingGoalsPanel) {
        model = new DefaultListModel();

        savingGoals = new JList<>(model);
        savingGoals.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        savingGoals.setLayoutOrientation(JList.VERTICAL);
        savingGoals.setVisibleRowCount(-1);


        JScrollPane listScroller = new JScrollPane(savingGoals);
        listScroller.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        savingGoalsPanel.add(listScroller);
    }

    // EFFECTS: execute commands according to buttons pressed
    private class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton src = (JButton) e.getSource();

            String name = nameArea.getText();
            int amount = Integer.parseInt(amountArea.getText());

            switch (src.getText()) {
                case "Create saving goal":
                    handleAdd(name, amount);
                    break;
                case "Select saving goal":
                    handleSelect(name);
                    break;
            }

        }
    }

    // MODIFIES: this
    // EFFECTS: add saving goal to model list
    public void addSavingGoals(SavingGoal goal) {
        String text = goal.getTitle();

        model.addElement(text);
    }

    // REQUIRES: user.getSavingGoal(name) != null
    // MODIFIES: this
    // EFFECTS: select a saving goal to view information
    public void handleSelect(String name) {
        SavingGoal selected = user.getSavingGoal(name);
        new SavingGoalInfo(selected);
    }

    // MODIFIES: this, user
    // EFFECTS: add saving goal to list of saving goals for ui and user
    public void handleAdd(String name, int amount) {
        SavingGoal goal = new SavingGoal(name, amount);
        user.addSavingGoal(goal);
        addSavingGoals(goal);
    }
}
