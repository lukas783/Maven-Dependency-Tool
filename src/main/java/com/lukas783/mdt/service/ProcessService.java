package com.lukas783.mdt.service;

import com.lukas783.mdt.api.IProcessServiceListener;
import com.lukas783.mdt.api.MavenTask;
import com.lukas783.mdt.util.CommandLine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * A singleton-service class that can be called statically by any class that needs to use it.
 * Keeps a list of listeners that implement {@link IProcessServiceListener} to fire events
 * as the service sees fit. The service is responsible for handling processing of tasks to-do.
 */
public class ProcessService {

    // Declaration of logger for debug/error handling messages.
    private static final Logger logger = Logger.getLogger(ProcessService.class.getName());

    // Declaration of the internal instance to always use
    private static ProcessService INSTANCE;

    // Declaration of listeners the service will need to fire events to
    private ArrayList<IProcessServiceListener> listeners;

    private Map<UUID, MavenTask> taskMap;

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
        taskMap = new HashMap<>();
    }

    /**
     * Executes the current tasks by using the {@link CommandLine#ExecuteCommandLine(File, String)} method
     */
    public void executeProcessTasks() {
        for(MavenTask task : taskMap.values()) {
            StringBuilder commandString = new StringBuilder();

            // Build the command to traverse to the proepr working directory.
            File workingDirectory = new File(task.getWorkingDirectory());

            if(!workingDirectory.isDirectory()) {
                logger.warning("Provided MavenTask: "+task.getTaskName()+" has bad working directory.");
                continue;
            }

            // Build the actual maven command
            commandString.append("mvn ");

            if(task.cleanTarget())
                commandString.append("clean ");

            if(task.doInstall())
                commandString.append("install ");
            else
                commandString.append("package");



            CommandLine.ExecuteCommandLine(workingDirectory, commandString.toString());
        }
    }

    /**
     * Returns a new {@link HashMap} of tasks to be used by other parts of the application.
     * @return A new {@link HashMap} of {@link UUID}, {@link MavenTask} mappings.
     */
    public Map<UUID, MavenTask> getTasks() {
        return new HashMap<>(taskMap);
    }

    /**
     * Returns a specific {@link MavenTask} from the set of all maven tasks held by the service.
     * @param id The UUID to retrieve from the map of tasks.
     * @return A {@link MavenTask} object.
     */
    public MavenTask getTask(UUID id) {
        return taskMap.get(id);
    }

    /**
     * Adds a new {@link MavenTask} to the service's map of tasks.
     * @param task The {@link MavenTask} to add to the map.
     * @return True if object was added successfully, False otherwise.
     */
    public boolean addTask(MavenTask task) {
        if(taskMap.containsKey(task.getId()))
            return updateTask(task);

        taskMap.put(task.getId(), task);

        if(taskMap.containsKey(task.getId())) {
            for (IProcessServiceListener listener : listeners) {
                listener.taskAdded(task);
            }
            appendExecutionOutput("\n\nNew maven task: " + task.getId() + " with name: " + task.getTaskName() + " has been added.\n");
            return true;
        }
        return false;
    }

    /**
     * Updates an existing {@link MavenTask} from the service's map of tasks.
     * @param task The {@link MavenTask} to update from the map of tasks.
     * @return True if the task updated successfully, False otherwise.
     */
    public boolean updateTask(MavenTask task) {
        if(!taskMap.containsKey(task.getId()))
            return addTask(task);

        taskMap.replace(task.getId(), task);

        if(taskMap.containsKey(task.getId())) {
            for (IProcessServiceListener listener : listeners) {
                listener.taskUpdated(task);
            }
            appendExecutionOutput("\n\nVaven task: " + task.getId() + " with name: " + task.getTaskName() + " has been updated.\n");
            return true;
        }
        return false;
    }

    /**
     * Removes a {@link MavenTask} from the service's map of tasks.
     * @param task The {@link MavenTask} to remove from the map of tasks.
     * @return True if the object was successfully removed, False otherwise.
     */
    public boolean removeTask(MavenTask task) {
        boolean removed = taskMap.remove(task.getId(), task);

        if(removed) {
            for (IProcessServiceListener listener : listeners) {
                listener.taskRemoved(task);
            }
            appendExecutionOutput("\n\nMaven task: " + task.getId() + " with name: " + task.getTaskName() + " has been removed.\n");
            return true;
        }
        return false;
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
