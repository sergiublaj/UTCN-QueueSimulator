package model;

import java.util.UUID;

public class Client {
    private String clientID;
    private int arrivalTime;
    private int serviceTime;
    private int queueNumber;
    private int waitingTime;

    public Client(int arrivalTime, int serviceTime) {
        this.clientID = UUID.randomUUID().toString();
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.queueNumber = 0;
    }

    public String getClientID() {
        return clientID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isEnqueued() {
        return this.queueNumber > 0;
    }
}
