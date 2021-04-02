package model;

import controller.Controller;
import view.Person;
import view.View;

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
   private int maxClients;

   public Queue(View appView, Controller appController, int queueNumber) {
      this.appView = appView;
      this.appController = appController;
      this.queueNumber = queueNumber;
      this.maxClients = 0;
      enqueuedClients = new LinkedBlockingQueue<>();
      waitingTime = new AtomicInteger(0);
   }

   public void addClient(Client newClient) {
      try {
         enqueuedClients.put(newClient);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      int currentSize = enqueuedClients.size();
      if (currentSize > maxClients) {
         maxClients = currentSize;
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
               cancel();
            } else {
               if (!enqueuedClients.isEmpty()) {
                  Client atCashier = enqueuedClients.peek();
                  if (atCashier.getServiceTime() > 1) {
                     atCashier.decrementServiceTime();
                     appView.updatePerson(queueNumber, new Person(atCashier));
                  } else {
                     enqueuedClients.poll();
                     appView.removePerson(queueNumber);
                  }
                  waitingTime.decrementAndGet();
               }
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