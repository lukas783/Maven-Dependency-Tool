package com.lukas783.mdt.service;

import com.lukas783.mdt.api.IProcessServiceListener;
import com.lukas783.mdt.util.CommandLine;

import java.util.ArrayList;

public class ProcessService {

    private static ProcessService INSTANCE;

    private ArrayList<IProcessServiceListener> listeners;

    public static ProcessService getInstance() {
        if(INSTANCE == null)
            INSTANCE = new ProcessService();
        return INSTANCE;
    }

    private ProcessService() {
        listeners = new ArrayList<>();
    }

    public void executeProcessTasks() {
        CommandLine.ExecuteCommandLine("tree C:/");
    }

    public void appendExecutionOutput(String toAppend) {
        for(IProcessServiceListener listener : listeners)
            listener.processOutputAppend(toAppend);
    }

    public boolean addListener(IProcessServiceListener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(IProcessServiceListener listener) {
        return listeners.remove(listener);
    }
}
