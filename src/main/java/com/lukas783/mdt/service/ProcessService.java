package com.lukas783.mdt.service;

import com.lukas783.mdt.api.IProcessServiceListener;
import com.lukas783.mdt.util.CommandLine;

import java.util.ArrayList;

/**
 * A singleton-service class that can be called statically by any class that needs to use it.
 * Keeps a list of listeners that implement {@link IProcessServiceListener} to fire events
 * as the service sees fit. The service is responsible for handling processing of tasks to-do.
 */
public class ProcessService {

    // Declaration of the internal instance to always use
    private static ProcessService INSTANCE;

    // Declaration of listeners the service will need to fire events to
    private ArrayList<IProcessServiceListener> listeners;

    /**
     * The public facing method to get an instance of the class.
     * @return An instance of {@link ProcessService}
     */
    public static ProcessService getInstance() {
        if(INSTANCE == null)
            INSTANCE = new ProcessService();
        return INSTANCE;
    }

    /**
     * The private facing constructor used by the public-facing {@link #getInstance()} method
     * to create a new service instance reference if one does not already exist.
     */
    private ProcessService() {
        listeners = new ArrayList<>();
    }

    /**
     * Executes the current tasks by using the {@link CommandLine#ExecuteCommandLine(String)} method
     */
    public void executeProcessTasks() {
        CommandLine.ExecuteCommandLine("tree C:/");
    }

    /**
     * A public facing method to let the service know to tell listeners of the service that there is
     * new execution output from a child process.
     * @param toAppend The string to tell other listeners about.
     */
    public void appendExecutionOutput(String toAppend) {
        for(IProcessServiceListener listener : listeners)
            listener.processOutputAppend(toAppend);
    }

    /**
     * Adds an implementation of {@link IProcessServiceListener} to the service's internal list of listeners
     * @param listener A class that implements {@link IProcessServiceListener}
     * @return True if the listener was added successfully, False otherwise.
     */
    public boolean addListener(IProcessServiceListener listener) {
        return listeners.add(listener);
    }

    /**
     * Removes an existing implementation of {@link IProcessServiceListener} from the service's internal list
     * of listeners.
     * @param listener A class that implements {@link IProcessServiceListener}
     * @return True if the listener was removed successfully, False otherwise.
     */
    public boolean removeListener(IProcessServiceListener listener) {
        return listeners.remove(listener);
    }
}
