package controller;

import model.Model;
import view.View;

public class Controller {
    private final Model appModel;
    private final View appView;

    public Controller(Model appModel, View appView) {
        this.appModel = appModel;
        this.appView = appView;
    }
}
