package controller.listeners;

import controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopSimulationListener implements ActionListener {
    private final Controller appController;

    public StopSimulationListener(Controller appController) {
        this.appController = appController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appController.setSimulationRunning(false);
    }
}
