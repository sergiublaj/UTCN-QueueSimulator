package view;

import model.Client;

import javax.swing.*;
import java.awt.*;

public class Person extends JPanel {
   private final String personMessage;
   private static int cashierID = 1;

   public Person(Client personType) {
      JLabel personImage = new JLabel();
      if(personType != null) {
         personImage.setIcon(new ImageIcon("src/main/resources/images/client.png"));
         personMessage = "Client (" + personType.getClientID() + "-" + personType.getServiceTime() + ")";
      } else {
         personImage.setIcon(new ImageIcon("src/main/resources/images/cashier.png"));
         personMessage = "Cashier (" + Person.cashierID + ")";
         Person.cashierID++;
      }
      this.add(personImage);
   }

   public static void resetCashierID() {
      Person.cashierID = 1;
   }

   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      this.setForeground(Color.RED);
      g.drawString(personMessage, 20, 10);
   }
}
