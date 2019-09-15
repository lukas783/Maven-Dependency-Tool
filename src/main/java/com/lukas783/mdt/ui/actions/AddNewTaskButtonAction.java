package com.lukas783.mdt.ui.actions;

import com.lukas783.mdt.ui.AddNewTaskPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the 'Add New Task' button action.
 *
 * @author Lucas Carpenter
 */
public class AddNewTaskButtonAction implements ActionListener {
    /**
     * Performs the event for the action. Opens a new window on a separate thread
     * that displays the {@link AddNewTaskPanel} panel.
     * @param e The event given by the action performed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> {
            JFrame taskFrame = new JFrame("Add New Task");
            taskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            taskFrame.add(new AddNewTaskPanel(), BorderLayout.CENTER);
            taskFrame.pack();
            taskFrame.setLocationRelativeTo(null);
            taskFrame.setVisible(true);
        }).start();
    }
}
