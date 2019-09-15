package com.lukas783.mdt.ui;

import com.lukas783.mdt.api.IProcessServiceListener;
import com.lukas783.mdt.service.ProcessService;
import com.lukas783.mdt.ui.actions.ProcessButtonAction;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * A panel to display processing information. Listens to the process service to update a JTextArea
 * field with the appropriate output from the service.
 *
 * @author Lucas Carpenter
 */
public class ProcessPanel extends JPanel implements IProcessServiceListener {

    // Declaration of layout and layout constants
    private SpringLayout layout;
    private static final String NORTH = SpringLayout.NORTH;
    private static final String EAST = SpringLayout.EAST;
    private static final String WEST = SpringLayout.WEST;
    private static final String SOUTH = SpringLayout.SOUTH;

    // Declaration of components used by the panel.
    private JTextArea outputArea;
    private JScrollPane scrollPane;
    private JButton processButton;


    /**
     * Instantiate, build, display, and attach listeners to the panel and its components.
     */
    public ProcessPanel() {
        super();
        // Instantiate and set layout
        layout = new SpringLayout();
        setLayout(layout);

        // Instantiate text area and its scroll pane
        outputArea = new JTextArea(20, 80);
        ((DefaultCaret)outputArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane = new JScrollPane(outputArea);

        // Instantiate buttons and set listeners for the buttons
        processButton = new JButton("Process Tasks");
        processButton.addActionListener(new ProcessButtonAction());

        // Add components to the panel
        add(scrollPane);
        add(processButton);

        // Declare layout constraints for the panel
        layout.putConstraint(NORTH, processButton, 5, NORTH, this);
        layout.putConstraint(WEST, processButton, 5, WEST, this);

        layout.putConstraint(NORTH, scrollPane, 10 ,SOUTH, processButton);
        layout.putConstraint(WEST, scrollPane, 0, WEST, processButton);

        layout.putConstraint(SOUTH, this, 5, SOUTH, scrollPane);
        layout.putConstraint(EAST, this, 5, EAST, scrollPane);

        // Attach any listeners needed by the panel.
        ProcessService.getInstance().addListener(this);
    }

    /**
     * A method required by {@link IProcessServiceListener}. The purpose is to listen for processing
     * output and update {@link #outputArea} appropriately.
     * @param processOutput The most recent output string from the process service
     */
    @Override
    public void processOutputAppend(String processOutput) {
        outputArea.append(processOutput);
    }
}
