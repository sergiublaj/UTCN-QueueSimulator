package validator;

import model.Client;

import java.util.Comparator;

public class AscendingArrivalTimeSort implements Comparator<Client>  {

   @Override
   public int compare(Client firstClient, Client secondClient) {
      return firstClient.getArrivalTime() - secondClient.getArrivalTime();
   }
}
