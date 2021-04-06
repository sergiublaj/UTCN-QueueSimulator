package model;

import controller.Controller;
import view.Person;
import view.View;

import javax.swing.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {
   private final View appView;
   private final Controller appController;

   private final BlockingQueue<Client> enqueuedClients;
   private final AtomicInteger waitingTime;
   private final int queueNumber;

   public Queue(View appView, Controller appController, int queueNumber) {
      this.appView = appView;
      this.appController = appController;
      this.queueNumber = queueNumber;
      enqueuedClients = new LinkedBlockingQueue<>();
      waitingTime = new AtomicInteger(0);
   }

   public void addClient(Client newClient) {
      try {
         enqueuedClients.put(newClient);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      waitingTime.getAndAdd(newClient.getServiceTime());
   }

   @Override
   public void run() {
      Timer runningQueue = new Timer();
      runningQueue.schedule(new TimerTask() {
         @Override
         public void run() {
            if (!appController.isSimulationRunning().get()) {
               runningQueue.cancel();
            } else {
               new SwingWorker<Void, Object>() {
                  @Override
                  protected Void doInBackground() {
                     if (!appController.isSimulationRunning().get()) {
                        runningQueue.cancel();
                     } else {
                        if (!enqueuedClients.isEmpty()) {
                           Client atCashier = enqueuedClients.peek();
                           if (atCashier.getServiceTime() > 1) {
                              atCashier.decrementServiceTime();
                              publish(queueNumber, new Person(atCashier));
                           } else {
                              enqueuedClients.poll();
                              publish(queueNumber);
                           }
                           waitingTime.decrementAndGet();
                        }
                     }
                     return null;
                  }

                  @Override
                  protected void process(List<Object> chunks) {
                     super.process(chunks);
                     if(appController.isSimulationRunning().get()) {
                        if (chunks.size() == 1) {
                           appView.removePerson((int) chunks.get(0));
                        } else {
                           appView.updatePerson((int) chunks.get(0), (Person) chunks.get(1));
                        }
                     }
                  }
               }.execute();
            }
         }
      }, 0, 1000);
   }

   @Override
   public String toString() {
      StringBuilder queueStatus = new StringBuilder("[Q" + queueNumber + "] ");
      if (!enqueuedClients.isEmpty()) {
         for (Client clientIt : enqueuedClients) {
            queueStatus.append(clientIt.toString());
            queueStatus.append(" ");
         }
      } else {
         queueStatus.append("closed");
      }
      queueStatus.append("\n");
      return queueStatus.toString();
   }

   public AtomicInteger getWaitingTime() {
      return waitingTime;
   }

   public AtomicInteger getClientsNumber() { return new AtomicInteger(enqueuedClients.size()); }
}
