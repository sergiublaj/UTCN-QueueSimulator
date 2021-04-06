package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private final int clientID;
    private final int arrivalTime;
    private final AtomicInteger serviceTime;

    public Client(int clientID, int arrivalTime, int serviceTime) {
        this.clientID = clientID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = new AtomicInteger(serviceTime);
    }

    public void decrementServiceTime() {
        this.serviceTime.decrementAndGet();
    }

    @Override
    public String toString() {
        return String.format("C(%d-%d-%d)", this.clientID, this.arrivalTime, this.serviceTime.get());
    }

    public int getClientID() {
        return clientID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime.get();
    }
}
