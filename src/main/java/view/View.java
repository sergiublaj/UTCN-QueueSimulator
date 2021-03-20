package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class View extends JFrame {
    public static final String APP_TITLE = "Queue simulator";

    private static final int APP_WIDTH = 900;
    private static final int APP_HEIGHT = 600;
    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 500;

    private JPanel contentPanel;
    private JPanel imagePanel;
    private JPanel mainPanel;

    private final InputPanel queuesNumber = new InputPanel("Queues");
    private final InputPanel clientsNumber = new InputPanel("Clients");
    private final InputPanel simulationTime = new InputPanel("Simulation Time");
    private final InputPanel minArrivalTime = new InputPanel("Minimum Arrival Time");
    private final InputPanel maxArrivalTime = new InputPanel("Maximum Arrival Time");
    private final InputPanel minServiceTime = new InputPanel("Minimum Service Time");
    private final InputPanel maxServiceTime = new InputPanel("Maximum Service Time");

    private final JButton startSimulation = new JButton("START");
    private final JButton stopSimulation = new JButton("STOP");
    private final JButton clearSimulation = new JButton("CLEAR");
    private final JTextArea logOfEvents = new JTextArea(10, 60);
    private final JTextField simulationResult = new JTextField();

    public View() {
        this.setUpFrame();
        this.setUpContentPanel();
        this.addLeftSideInformation();
        this.addMainPanelComponents();
    }

    private void setUpFrame() {
        // DOAR PT AL DOILEA MONITOR
        // this.setLocation(2350, 250);

        this.setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        this.setResizable(false);
        this.setTitle(APP_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setUpContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.LIGHT_GRAY);
        this.setContentPane(contentPanel);
    }

    private void addLeftSideInformation() {
        this.setUpImagePanel();
        this.addQueueImage();
        this.addAuthorInfo();
    }

    private void setUpImagePanel() {
        imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        imagePanel.setLayout(new BorderLayout());
        contentPanel.add(imagePanel, BorderLayout.WEST);
    }

    private void addQueueImage() {
        JLabel mathImage = new JLabel();
        mathImage.setIcon(new ImageIcon("src/main/resources/images/queue.jpg"));
        imagePanel.add(mathImage, BorderLayout.CENTER);
    }

    private void addAuthorInfo() {
        JLabel shortInfo = new JLabel("<html>Ver: 1.1_00.14<br>" +
                "@ author Blaj Sergiu<br>" +
                "@ group 30225</html>");
        shortInfo.setFont(new Font("JetBrains Mono", Font.PLAIN, 15));
        shortInfo.setBorder(new EmptyBorder(5, 5, 5, 5));
        shortInfo.setForeground(Color.WHITE);
        shortInfo.setBackground(Color.BLACK);
        shortInfo.setOpaque(true);
        imagePanel.add(shortInfo, BorderLayout.SOUTH);
    }

    private void addMainPanelComponents() {
        this.setUpAppPanel();
        this.addAppTitle();
        this.addSetupPanel();
        this.addButtonsPanel();
        this.addLogPanel();
        this.addResultPanel();
    }

    private void setUpAppPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        contentPanel.add(mainPanel, BorderLayout.CENTER);
    }

    private void addAppTitle() {
        JLabel appTitle = new JLabel(APP_TITLE.toUpperCase());
        appTitle.setFont(new Font("Impact", Font.BOLD, 40));
        appTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
        appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(appTitle);
    }

    private void addSetupPanel() {
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.Y_AXIS));
        mainPanel.add(setupPanel);

        JPanel simulationPanel = new JPanel();
        simulationPanel.add(queuesNumber);
        simulationPanel.add(clientsNumber);
        simulationPanel.add(simulationTime);
        setupPanel.add(simulationPanel);

        JPanel dualPanel = new JPanel();
        dualPanel.setLayout(new BoxLayout(dualPanel, BoxLayout.X_AXIS));
        setupPanel.add(dualPanel);

        JPanel arrivalPanel = new JPanel();
        arrivalPanel.setLayout(new BoxLayout(arrivalPanel, BoxLayout.Y_AXIS));
        arrivalPanel.add(minArrivalTime);
        arrivalPanel.add(maxArrivalTime);
        dualPanel.add(arrivalPanel);

        JPanel servicePanel = new JPanel();
        servicePanel.setLayout(new BoxLayout(servicePanel, BoxLayout.Y_AXIS));
        servicePanel.add(minServiceTime);
        servicePanel.add(maxServiceTime);
        dualPanel.add(servicePanel);
    }

    private void addButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3));
        buttonsPanel.setBorder(new EmptyBorder(0, 25, 0, 25));
        mainPanel.add(buttonsPanel);

        View.initButton(startSimulation, Color.GREEN);
        View.initButton(stopSimulation, Color.RED);
        View.initButton(clearSimulation, Color.BLACK);

        buttonsPanel.add(startSimulation);
        buttonsPanel.add(stopSimulation);
        buttonsPanel.add(clearSimulation);
    }

    private static void initButton(JButton crtButton, Color crtColor) {
        crtButton.setFont(new Font("Impact", Font.BOLD, 23));
        crtButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        crtButton.setForeground(Color.WHITE);
        crtButton.setBackground(crtColor);
    }

    private void addLogPanel() {
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
        logPanel.setBorder(new EmptyBorder(0, 25, 0, 25));
        mainPanel.add(logPanel);

        JLabel logLabel = new JLabel("LOG OF EVENTS");
        logLabel.setFont(new Font("Impact", Font.PLAIN, 25));
        logLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logPanel.add(logLabel);

        logOfEvents.setFont(new Font("Tahoma", Font.PLAIN, 18));
        logOfEvents.setLineWrap(true);
        logOfEvents.setWrapStyleWord(true);
        JScrollPane logScroll = new JScrollPane(logOfEvents);
        logScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        logPanel.add(logScroll);
    }

    private void addResultPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(new EmptyBorder(10, 25, 10, 25));
        mainPanel.add(resultPanel);

        JLabel resultLabel = new JLabel("Results of simulation:");
        resultLabel.setFont(new Font("Impact", Font.PLAIN, 25));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultPanel.add(resultLabel);

        simulationResult.setFont(new Font("Tahoma", Font.PLAIN, 18));
        simulationResult.setEditable(false);
        resultPanel.add(simulationResult);
    }
}