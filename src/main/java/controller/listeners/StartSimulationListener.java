package controller.listeners;

import controller.Controller;
import model.SimulationManager;
import validator.InvalidInputException;
import view.Person;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartSimulationListener implements ActionListener {
    private final View appView;
    private final Controller appController;

    public StartSimulationListener(View appView, Controller appController) {
        this.appView = appView;
        this.appController = appController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.initiateSimulation();
        } catch (InvalidInputException invalidInput) {
            appView.setSimulationResults(invalidInput.getMessage(), true);
        }
    }

    private void initiateSimulation() throws InvalidInputException {
        int simulationTime = appView.getSimulationTime();
        int minArrivalTime = appView.getMinArrivalTime();
        int maxArrivalTime = appView.getMaxArrivalTime();
        int minServiceTime = appView.getMinServiceTime();
        int maxServiceTime = appView.getMaxServiceTime();

        if(minArrivalTime > maxArrivalTime || minArrivalTime > simulationTime) {
            throw new InvalidInputException("Invalid arrival times!");
        } else if (minServiceTime > maxServiceTime) {
            throw new InvalidInputException("Invalid service times!");
        }

        appView.enableButtons(false);
        appView.setSimulationResults("", true);
        Person.resetCashierID();
        appController.setSimulationRunning(true);
        SimulationManager simulationManager = new SimulationManager(appView, appController);
        Thread theSimulation = new Thread(simulationManager);
        theSimulation.start();
    }
}
