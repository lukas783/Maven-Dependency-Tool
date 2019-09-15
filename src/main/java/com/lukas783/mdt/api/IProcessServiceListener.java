package com.lukas783.mdt.api;

import com.lukas783.mdt.service.ProcessService;

/**
 * A listener interface that can be implemented by any class that needs to listen to
 * the {@link com.lukas783.mdt.service.ProcessService} singleton service.
 *
 * @author Lucas Carpenter
 */
public interface IProcessServiceListener {
    /**
     * A required method that is meant to be called when {@link ProcessService#executeProcessTasks()}
     * finishes.
     * @param processOutput The most recent output string from a command line execution.
     */
    public void processOutputAppend(String processOutput);
}
