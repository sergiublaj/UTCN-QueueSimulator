package model;

import view.Person;
import view.View;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
   private final CopyOnWriteArrayList<Queue> availableQueues;
   private final View appView;

   public Scheduler(View appView, CopyOnWriteArrayList<Queue> availableQueues) {
      this.appView = appView;
      this.availableQueues = availableQueues;
   }

   public int sendClientToQueue(Client newClient) {
      int minWaitingTime = Integer.MAX_VALUE;
      int minWaitingQueue = -1;
      for (int i = 0; i < availableQueues.size(); i++) {
         int crtWaitingTime = availableQueues.get(i).getWaitingTime().get();
         if (crtWaitingTime < minWaitingTime) {
            minWaitingTime = crtWaitingTime;
            minWaitingQueue = i;
         }
      }
      int hasToWait = availableQueues.get(minWaitingQueue).getWaitingTime().get();
      availableQueues.get(minWaitingQueue).addClient(newClient);
      appView.addPerson(minWaitingQueue, new Person(newClient));
      return hasToWait;
   }

   public AtomicInteger getTotalWaitingTime() {
      AtomicInteger totalWaitingTime = new AtomicInteger(0);
      for(Queue crtQueue : availableQueues) {
         totalWaitingTime.getAndAdd(crtQueue.getWaitingTime().get());
      }
      return totalWaitingTime;
   }

   public CopyOnWriteArrayList<Queue> getAvailableQueues() {
      return availableQueues;
   }
}
