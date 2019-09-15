package com.lukas783.mdt.ui;

import com.lukas783.mdt.api.IProcessServiceListener;
import com.lukas783.mdt.service.ProcessService;
import com.lukas783.mdt.ui.actions.ProcessButtonAction;
import com.lukas783.mdt.util.CommandLine;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class ProcessPanel extends JPanel implements IProcessServiceListener {

    private SpringLayout layout;

    private JTextArea outputArea;
    private JScrollPane scrollPane;
    private JButton processButton;

    private static final String NORTH = SpringLayout.NORTH;
    private static final String EAST = SpringLayout.EAST;
    private static final String WEST = SpringLayout.WEST;
    private static final String SOUTH = SpringLayout.SOUTH;

    public ProcessPanel() {
        super();
        layout = new SpringLayout();
        setLayout(layout);

        outputArea = new JTextArea(20, 80);
        ((DefaultCaret)outputArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane = new JScrollPane(outputArea);
        processButton = new JButton("Process Tasks");
        processButton.addActionListener(new ProcessButtonAction());

        add(scrollPane);
        add(processButton);

        layout.putConstraint(NORTH, processButton, 5, NORTH, this);
        layout.putConstraint(WEST, processButton, 5, WEST, this);

        layout.putConstraint(NORTH, scrollPane, 10 ,SOUTH, processButton);
        layout.putConstraint(WEST, scrollPane, 0, WEST, processButton);

        layout.putConstraint(SOUTH, this, 5, SOUTH, scrollPane);
        layout.putConstraint(EAST, this, 5, EAST, scrollPane);

        ProcessService.getInstance().addListener(this);
    }

    @Override
    public void processOutputAppend(String processOutput) {
        outputArea.append(processOutput);
    }
}
