package com.lukas783.mdt.ui.actions;

import com.lukas783.mdt.service.ProcessService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProcessButtonAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ProcessService.getInstance().executeProcessTasks();
            }
        });
        t.start();
    }
}
