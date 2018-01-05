package io.indoorlocation.manual;

import io.indoorlocation.core.IndoorLocation;
import io.indoorlocation.core.IndoorLocationProvider;

public class ManualIndoorLocationProvider extends IndoorLocationProvider {

    private boolean isStarted = false;

    public ManualIndoorLocationProvider() {
        super("setlocation");
    }

    public void setIndoorLocation(IndoorLocation indoorLocation) {
        dispatchIndoorLocationChange(indoorLocation);
    }

    @Override
    public boolean supportsFloor() {
        return true;
    }

    @Override
    public void start() {
        this.isStarted = true;
    }

    @Override
    public void stop() {
        this.isStarted = false;
    }

    @Override
    public boolean isStarted() {
        return this.isStarted;
    }
}
