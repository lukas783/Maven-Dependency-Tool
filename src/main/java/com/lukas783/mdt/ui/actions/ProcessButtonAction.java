package com.lukas783.mdt.ui.actions;

import com.lukas783.mdt.service.ProcessService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the action for processing tasks. Creates a new thread and runs the
 * {@link ProcessService#executeProcessTasks()} function as an event.
 *
 * @author Lucas Carpenter
 */
public class ProcessButtonAction implements ActionListener {
    /**
     * Performs the action related to the event by spawning a new thread to
     * execute the {@link ProcessService#executeProcessTasks()} method.
     * @param e The ActionEvent passed to the action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> {
            ProcessService.getInstance().executeProcessTasks();
        }).start();
    }
}
