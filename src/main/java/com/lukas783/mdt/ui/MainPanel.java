package com.lukas783.mdt.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel {

    private SpringLayout layout;
    private JTabbedPane tabbedPane;

    public MainPanel() {
        super(new GridLayout(1, 1));
        layout = new SpringLayout();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Process", null, new ProcessPanel(), "Opens the processing panel");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.addTab("Config", null, new JPanel(), "Opens configuration for the tool.");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}
