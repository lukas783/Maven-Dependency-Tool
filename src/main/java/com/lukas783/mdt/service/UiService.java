package com.lukas783.mdt.service;

import com.lukas783.mdt.api.IUiServiceListener;

import java.io.File;
import java.util.ArrayList;

/**
 * A singleton-service class that an implementation of {@link IUiServiceListener} can utilize to listen to
 * various events fired by the service.
 *
 * @author Lucas Carpenter
 */
public class UiService {
    // Declaration of the internal instance to always use
    private static UiService INSTANCE;

    // Declaration of listeners the service will need to fire events to
    private ArrayList<IUiServiceListener> listeners;

    /**
     * The public facing method to get an instance of the class.
     * @return An instance of {@link UiService}
     */
    public static UiService getInstance() {
        if(INSTANCE == null)
            INSTANCE = new UiService();
        return INSTANCE;
    }

    /**
     * The private facing constructor used by the public-facing {@link #getInstance()} method
     * to create a new service instance reference if one does not already exist.
     */
    private UiService() {
        listeners = new ArrayList<>();
    }

    /**
     * Lets all listeneing implementations of {@link IUiServiceListener} know that a particular
     * component has an updated directory. The component to update is differentiated by the
     * provided componentTag variable.
     * @param componentTag The tag to update the directory of
     * @param directory The new directory
     */
    public void UpdateDirectory(String componentTag, File directory) {
        for(IUiServiceListener listener : listeners)
            listener.directoryUpdated(componentTag, directory);
    }

    /**
     * Adds an implementation of {@link IUiServiceListener} to the service's internal list of listeners
     * @param listener A class that implements {@link IUiServiceListener}
     * @return True if the listener was added successfully, False otherwise.
     */
    public boolean addListener(IUiServiceListener listener) {
        return listeners.add(listener);
    }

    /**
     * Removes an existing implementation of {@link IUiServiceListener} from the service's internal list
     * of listeners.
     * @param listener A class that implements {@link IUiServiceListener}
     * @return True if the listener was removed successfully, False otherwise.
     */
    public boolean removeListener(IUiServiceListener listener) {
        return listeners.remove(listener);
    }
}
