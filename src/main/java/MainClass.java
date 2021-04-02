import controller.Controller;
import view.View;

public class MainClass {
    public static void main(String[] args) {
        View appView = new View();
        Controller appController = new Controller(appView);
        appView.setVisible(true);
    }
}

