package com.lukas783.mdt.ui;

import com.lukas783.mdt.ui.actions.CancelButtonAction;
import com.lukas783.mdt.ui.actions.SubmitTaskButtonAction;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * A panel for creating a new task to process.
 *
 * @author Lucas Carpenter
 */
public class AddNewTaskPanel extends JPanel {

    // Declaration of logger for debug/error messages
    private static final Logger logger = Logger.getLogger(TaskPanel.class.getName());

    // Declaration of layout and layout constants
    private SpringLayout layout;
    private static final String NORTH = SpringLayout.NORTH;
    private static final String EAST = SpringLayout.EAST;
    private static final String WEST = SpringLayout.WEST;
    private static final String SOUTH = SpringLayout.SOUTH;

    // Declaration of components used by the panel
    private JTextArea taskName;
    private JLabel taskNameLabel;
    private JButton submitTaskButton;
    private JButton cancelTaskButton;

    /**
     * Instantiate, build, display, and attach listeners to components of the panel.
     */
    public AddNewTaskPanel() {
        super();
        // Instantiate and set layout for the panel
        layout = new SpringLayout();
        setLayout(layout);

        // Instantiate labels for the panel
        taskNameLabel = new JLabel("Task Name");
        taskName = new JTextArea();

        // Instantiate buttons for the panel
        submitTaskButton = new JButton("Submit");
        submitTaskButton.addActionListener(new SubmitTaskButtonAction());

        cancelTaskButton = new JButton("Cancel");
        cancelTaskButton.addActionListener(new CancelButtonAction());

        // Add components to the panel
        add(taskNameLabel);
        add(taskName);
        add(submitTaskButton);
        add(cancelTaskButton);

        // Set constraints for taskNameLabel
        layout.putConstraint(NORTH, taskNameLabel, 15, NORTH, this);
        layout.putConstraint(WEST, taskNameLabel, 15, WEST, this);

        // Set constraints for taskName
        layout.putConstraint(NORTH, taskName, 5, SOUTH, taskNameLabel);
        layout.putConstraint(WEST, taskName, 0, WEST, taskNameLabel);
        layout.putConstraint(SOUTH, taskName, 15, NORTH, taskName);
        layout.putConstraint(EAST, taskName, 300, WEST, taskName);

        // Set constraints for cancelTaskButton
        layout.putConstraint(NORTH, cancelTaskButton, 30, SOUTH, taskName);
        layout.putConstraint(EAST, cancelTaskButton, 0, EAST, taskName);
        layout.putConstraint(WEST, cancelTaskButton, -80, EAST, cancelTaskButton);

        // Set constraints for submitTaskButton
        layout.putConstraint(NORTH, submitTaskButton, 0, NORTH, cancelTaskButton);
        layout.putConstraint(WEST, submitTaskButton, -80, WEST, cancelTaskButton);

        // Set constraints for panel size
        layout.putConstraint(EAST, this, 15, EAST, taskName);
        layout.putConstraint(SOUTH, this, 15, SOUTH, cancelTaskButton);

    }
}
