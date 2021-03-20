package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputPanel extends JPanel {

    private final String panelTitle;
    private final JSpinner inputField;

    public InputPanel(String panelTitle) {
        this.panelTitle = panelTitle;
        this.inputField = new JSpinner(new SpinnerNumberModel(5, 0, 1000, 1));
        this.setUpPanel();
    }

    private void setUpPanel() {
        this.setLayout(new FlowLayout());
        JLabel titleLabel = new JLabel(panelTitle);
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        inputField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        this.add(titleLabel);
        this.add(inputField);
    }

    public JSpinner getInputField() {
        return inputField;
    }
}
