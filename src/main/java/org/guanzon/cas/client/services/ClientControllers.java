package org.guanzon.cas.client.services;

import org.guanzon.appdriver.base.GRiderCAS;
import org.guanzon.appdriver.base.LogWrapper;
import org.guanzon.cas.client.Client;

public class ClientControllers {
    public ClientControllers(GRiderCAS applicationDriver, LogWrapper logWrapper) {
        poGRider = applicationDriver;
        poLogWrapper = logWrapper;
    }

    public Client Client() {
        if (poGRider == null) {
            poLogWrapper.severe("ClientControllers.Client: Application driver is not set.");
            return null;
        }

        if (poClient != null) {
            return poClient;
        }

        poClient = new Client(poGRider,"",poLogWrapper);
        return poClient;
    }

    private final GRiderCAS poGRider;
    private final LogWrapper poLogWrapper;

    private Client poClient;
}