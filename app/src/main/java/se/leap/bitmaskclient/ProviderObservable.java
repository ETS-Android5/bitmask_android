package se.leap.bitmaskclient;

import java.util.Observable;

/**
 * Created by cyberta on 05.12.18.
 */
public class ProviderObservable extends Observable {
    private static ProviderObservable instance;
    private Provider currentProvider;
    private Provider providerToSetup;

    public static ProviderObservable getInstance() {
        if (instance == null) {
            instance = new ProviderObservable();
        }
        return instance;
    }

    public synchronized void updateProvider(Provider provider) {
        instance.currentProvider = provider;
        instance.providerToSetup = null;
        instance.setChanged();
        instance.notifyObservers();
    }

    public Provider getCurrentProvider() {
        return instance.currentProvider;
    }

    public void setProviderToSetup(Provider provider) {
        this.providerToSetup = provider;
    }

    public Provider getProviderToSetup() {
        return instance.providerToSetup;
    }

}
