package view;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.text.ParseException;

public class InputPanel extends JPanel {
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 1000;
    private static final int DEFAULT_VALUE = 5;
    private static final int STEP_SIZE = 1;

    private final String panelTitle;
    private final JSpinner inputField;

    public InputPanel(String panelTitle) {
        this.panelTitle = panelTitle;
        this.inputField = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE, MIN_VALUE, MAX_VALUE, STEP_SIZE));
        this.setUpPanel();
    }

    private void setUpPanel() {
        this.setLayout(new FlowLayout());
        JLabel titleLabel = new JLabel(panelTitle);
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        inputField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        ((DefaultFormatter)((JSpinner.DefaultEditor)inputField.getEditor()).getTextField().getFormatter()).setAllowsInvalid(false);
        this.add(titleLabel);
        this.add(inputField);
    }

    public Integer getInputValue() {
        try {
            inputField.commitEdit();
        } catch (ParseException parseEx) {
            System.out.println(parseEx.getMessage());
        }
        return (Integer)inputField.getValue();
    }

    public void resetToDefault() {
        this.inputField.setValue(DEFAULT_VALUE);
    }
}
