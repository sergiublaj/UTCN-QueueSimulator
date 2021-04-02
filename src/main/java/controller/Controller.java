package controller;

import controller.listeners.ClearSimulationListener;
import controller.listeners.StartSimulationListener;
import controller.listeners.StopSimulationListener;
import view.View;

import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {
    private final View appView;
    private final AtomicBoolean simulationRunning;

    public Controller(View appView) {
        this.appView = appView;
        this.simulationRunning = new AtomicBoolean(false);
        this.addEventListeners();
    }

    private void addEventListeners() {
        appView.addStartSimulationListener(new StartSimulationListener(appView, this));
        appView.addStopSimulationListener(new StopSimulationListener(this));
        appView.addClearSimulationListener(new ClearSimulationListener(appView));
    }

    public AtomicBoolean isSimulationRunning() {
        return this.simulationRunning;
    }

    public void setSimulationRunning(boolean isRunning) {
        this.simulationRunning.set(isRunning);
    }
}
