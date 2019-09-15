package com.lukas783.mdt.ui;

import com.lukas783.mdt.ui.actions.AddNewTaskButtonAction;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * A panel to display the list of tasks as well as components to modify the tasks
 *
 * @author Lucas Carpenter
 */
public class TaskPanel extends JPanel {

    // Declaration of logger for debug/error messages
    private static final Logger logger = Logger.getLogger(TaskPanel.class.getName());

    // Declaration of layout and layout constants
    private SpringLayout layout;
    private static final String NORTH = SpringLayout.NORTH;
    private static final String EAST = SpringLayout.EAST;
    private static final String WEST = SpringLayout.WEST;
    private static final String SOUTH = SpringLayout.SOUTH;

    // Declaration of components used by the panel
    private JButton addNewTaskButton;
    private JButton removeTaskButton;
    private JButton editTaskButton;

    /**
     * Instantiate, build, display, and attach listeners to components of the panel.
     */
    public TaskPanel() {
        super();
        // Instantiate and set-up layout
        layout = new SpringLayout();
        setLayout(layout);

        // Instantiate buttons for the panel
        addNewTaskButton = new JButton("Add New Task");
        addNewTaskButton.addActionListener(new AddNewTaskButtonAction());

        // Add components to the panel
        add(addNewTaskButton);

        // Declare layout constraints for the panel and its components.
        layout.putConstraint(NORTH, addNewTaskButton, 5, NORTH, this);
        layout.putConstraint(WEST, addNewTaskButton, 5, WEST, this);
        layout.putConstraint(EAST, this, 5, EAST, addNewTaskButton);
        layout.putConstraint(SOUTH, this, 5, SOUTH, addNewTaskButton);

        // Attach any listeners needed by the panel.
    }
}
