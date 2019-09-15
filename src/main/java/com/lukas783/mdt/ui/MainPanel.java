package com.lukas783.mdt.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The main panel for the application. Should be responsible for displaying the various sub-panels of the
 * application however it sees fit.
 *
 * @author Lucas Carpenter
 */
public class MainPanel extends JPanel {

    // Declaration of layout and layout constants
    private SpringLayout layout;

    // Declaration of components used by the panel
    private JTabbedPane tabbedPane;

    /**
     * Instantiates, builds, and displays the main panel to be visible throughout the applications life.
     */
    public MainPanel() {
        // Instantiate and declare layouts
        super(new GridLayout(1, 1));
        layout = new SpringLayout();

        // Instnatiate and set-up the tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Process", null, new ProcessPanel(), "Opens the processing panel");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.addTab("Config", null, new JPanel(), "Opens configuration for the tool.");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        // Add components to the main panel
        add(tabbedPane);

        // Set any listeners or policies needed by the components
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}
