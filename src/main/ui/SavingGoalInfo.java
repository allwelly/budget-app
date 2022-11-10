package ui;

import model.SavingGoal;

import javax.swing.*;
import java.awt.*;

/**
 * displays a popup containing details of a specific saving goal
 */

public class SavingGoalInfo extends JFrame {
    private static final int WIDTH = 360;
    private static final int HEIGHT = 270;
    private static final int MARGIN = 36;
    private static final int PANEL_WIDTH = WIDTH - (MARGIN * 2);
    private static final int PANEL_HEIGHT = (HEIGHT / 2) - (MARGIN * 2);

    private SavingGoal goal;
    private JLabel nameLabel;
    private JLabel statusLabel;

    // EFFECTS: constructs a saving goal info popup
    public SavingGoalInfo(SavingGoal goal) {
        super();

        this.goal = goal;

        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLayout(null);

        // PANEL
        JPanel popupPanel = new JPanel();
        popupPanel.setBounds(MARGIN, MARGIN, PANEL_WIDTH, PANEL_HEIGHT);
        popupPanel.setLayout(new GridLayout(2, 1));
        add(popupPanel);

        // INFO
        nameLabel = new JLabel("Goal name: " + goal.getTitle());
        popupPanel.add(nameLabel);
        statusLabel = new JLabel("You have saved $" + goal.getMoneySaved()
                + " out of $" + goal.getTarget());
        popupPanel.add(statusLabel);

        setVisible(true);
    }
}
