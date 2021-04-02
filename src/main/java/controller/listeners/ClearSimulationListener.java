package controller.listeners;

import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearSimulationListener implements ActionListener {
    private final View appView;

    public ClearSimulationListener(View appView) {
        this.appView = appView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        appView.clearInputs();
        appView.setSimulationResults("Inputs cleared successfully!", true);
    }
}
