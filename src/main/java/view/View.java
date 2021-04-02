package view;

import validator.DurationFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class View extends JFrame {
   public static final String APP_TITLE = "Queue simulator";

   private static final int APP_WIDTH = 1000;
   private static final int APP_HEIGHT = 700;
   private static final int IMAGE_WIDTH = 150;
   private static final int IMAGE_HEIGHT = 500;

   private final JPanel contentPanel = new JPanel();
   private final JPanel imagePanel = new JPanel();
   private final JPanel mainPanel = new JPanel();
   private final JPanel simulationPanel = new JPanel();
   private final ArrayList<JPanel> queuesPanel = new ArrayList<>();

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
   private JLabel simulationTimer = new JLabel();
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
      JLabel shortInfo = new JLabel("<html>Ver: 1.14_22.11<br>" +
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
      this.addSimulationPanel();
      this.addResultPanel();
   }

   private void setUpAppPanel() {
      mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
      mainPanel.setBorder(new EmptyBorder(0, 25, 10, 25));
      contentPanel.add(mainPanel, BorderLayout.CENTER);
   }

   private void addAppTitle() {
      JLabel appTitle = new JLabel(APP_TITLE.toUpperCase());
      appTitle.setFont(new Font("Impact", Font.BOLD, 40));
      appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
      mainPanel.add(appTitle);
   }

   private void addSetupPanel() {
      JPanel setupPanel = new JPanel();
      setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.Y_AXIS));
      mainPanel.add(setupPanel);

      JPanel initializationPanel = new JPanel();
      initializationPanel.add(queuesNumber);
      initializationPanel.add(clientsNumber);
      initializationPanel.add(simulationTime);
      setupPanel.add(initializationPanel);

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
      mainPanel.add(buttonsPanel);

      View.initButton(startSimulation, Color.GREEN);
      View.initButton(stopSimulation, Color.RED);
      View.initButton(clearSimulation, Color.BLACK);

      buttonsPanel.add(startSimulation);
      buttonsPanel.add(stopSimulation);
      buttonsPanel.add(clearSimulation);

      this.enableButtons(true);
   }

   private static void initButton(JButton crtButton, Color crtColor) {
      crtButton.setFont(new Font("Impact", Font.BOLD, 25));
      crtButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
      crtButton.setForeground(Color.WHITE);
      crtButton.setBackground(crtColor);
   }

   private void addSimulationPanel() {
      JPanel logTitlePanel = new JPanel(new FlowLayout());
      mainPanel.add(logTitlePanel);

      JLabel logLabel = new JLabel("Live simulation");
      logLabel.setFont(new Font("Impact", Font.PLAIN, 25));
      logLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      logTitlePanel.add(logLabel);

      simulationTimer = new JLabel();
      simulationTimer.setFont(new Font("Impact", Font.PLAIN, 25));
      simulationTimer.setAlignmentX(Component.CENTER_ALIGNMENT);
      this.showTimer(false);
      logTitlePanel.add(simulationTimer);

      simulationPanel.setPreferredSize(new Dimension(500, 2000));
      simulationPanel.setLayout(new GridLayout(20, 1));
      JScrollPane logScroll = new JScrollPane(simulationPanel);
      mainPanel.add(logScroll);
   }

   private void addResultPanel() {
      JLabel resultLabel = new JLabel("Results of simulation:");
      resultLabel.setFont(new Font("Impact", Font.PLAIN, 25));
      resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      mainPanel.add(resultLabel);

      simulationResult.setFont(new Font("Tahoma", Font.PLAIN, 18));
      simulationResult.setEditable(false);
      mainPanel.add(simulationResult);
   }

   public void addPerson(int queueNb, Person toAdd) {
      try {
         queuesPanel.get(queueNb).add(toAdd);
      } catch (Exception eX) {
         queuesPanel.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
         queuesPanel.get(queueNb).add(toAdd);
         simulationPanel.add(queuesPanel.get(queueNb));
      }
      simulationPanel.revalidate();
   }

   public void updatePerson(int queueNb, Person updatedPerson) {
      try {
         this.queuesPanel.get(queueNb).remove(1);
         this.queuesPanel.get(queueNb).add(updatedPerson, 1);
      } catch (ArrayIndexOutOfBoundsException e) {
         e.printStackTrace();
      }
      simulationPanel.repaint();
      simulationPanel.revalidate();
   }

   public void removePerson(int queueNb) {
      try {
         this.queuesPanel.get(queueNb).remove(1);
      } catch (ArrayIndexOutOfBoundsException e) {
         e.printStackTrace();
      }
      simulationPanel.repaint();
      simulationPanel.revalidate();
   }

   public void removeAll() {
      for(JPanel crtQueue : queuesPanel) {
         crtQueue.removeAll();
      }
      simulationPanel.repaint();
      simulationPanel.revalidate();
   }

   public void setSimulationResults(String newResults, boolean disappearFast) {
      this.simulationResult.setText(newResults);
      if(disappearFast) {
         ScheduledExecutorService clearMessage = Executors.newSingleThreadScheduledExecutor();
         clearMessage.schedule(() -> this.setSimulationResults("", false), 3, TimeUnit.SECONDS);
      }
   }

   public void addStartSimulationListener(ActionListener crtListener) {
      this.startSimulation.addActionListener(crtListener);
   }

   public void addStopSimulationListener(ActionListener crtListener) {
      this.stopSimulation.addActionListener(crtListener);
   }

   public void addClearSimulationListener(ActionListener crtListener) {
      this.clearSimulation.addActionListener(crtListener);
   }

   public void showTimer(boolean timerIsShowing) {
      this.simulationTimer.setVisible(timerIsShowing);
   }

   public void setTimer(int countValue) {
      String onTimer = DurationFormatter.getTimerFormat(countValue);
      this.simulationTimer.setText("| " + onTimer);
   }

   public void clearInputs() {
      queuesNumber.resetToDefault();
      clientsNumber.resetToDefault();
      simulationTime.resetToDefault();
      minArrivalTime.resetToDefault();
      maxArrivalTime.resetToDefault();
      minServiceTime.resetToDefault();
      maxServiceTime.resetToDefault();
   }

   public void enableButtons(boolean isEnabled) {
      this.startSimulation.setEnabled(isEnabled);
      this.stopSimulation.setEnabled(!isEnabled);
      this.clearSimulation.setEnabled(isEnabled);
   }

   public Integer getQueuesNumber() {
      return queuesNumber.getInputValue();
   }

   public Integer getClientsNumber() {
      return clientsNumber.getInputValue();
   }

   public Integer getSimulationTime() {
      return simulationTime.getInputValue();
   }

   public Integer getMinArrivalTime() {
      return minArrivalTime.getInputValue();
   }

   public Integer getMaxArrivalTime() {
      return maxArrivalTime.getInputValue();
   }

   public Integer getMinServiceTime() {
      return minServiceTime.getInputValue();
   }

   public Integer getMaxServiceTime() {
      return maxServiceTime.getInputValue();
   }
}