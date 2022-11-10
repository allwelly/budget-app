package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a popup indicating an error
 */

public class ErrorPopup extends JFrame {
    private static final int WIDTH = 360;
    private static final int HEIGHT = 270;
    private static final int MARGIN = 36;
    private static final int PANEL_WIDTH = WIDTH - (MARGIN * 2);
    private static final int PANEL_HEIGHT = HEIGHT - (MARGIN * 2);

    // EFFECTS: constructs failure pop up class
    public ErrorPopup() {
        super();

        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLayout(null);

        // PANEL
        JPanel popupPanel = new JPanel();
        popupPanel.setBounds(MARGIN, MARGIN, PANEL_WIDTH, PANEL_HEIGHT);
        add(popupPanel);

        // IMAGE
        ImageIcon errorImage = new ImageIcon(this.getClass().getResource("failed.jpg"));
        JLabel image = new JLabel("", SwingConstants.CENTER);
        image.setIcon(errorImage);
        popupPanel.add(image);

        setVisible(true);
    }
}
