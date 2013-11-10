package Elevator.Part1;

import java.util.TreeSet;

import static java.lang.Thread.sleep;

public class Elevator implements Runnable {

    private int elevatorId;
    private int numFloors;
    private int currentFloor;
    private int numRiders;
    private boolean isGoingUp;
    private boolean isDoorOpen;
    private Building building;
    private TreeSet<Integer> upRequests;
    private TreeSet<Integer> downRequests;
    private EventBarrier[] floors;
    private Thread thread;


    public Elevator(int id, int numFloors, Building building){
        this.elevatorId = id;
        this.numFloors = numFloors;
        this.currentFloor = -1;
        this.numRiders = 0;
        this.isGoingUp = true;
        this.isDoorOpen = false;
        this.building = building;
        this.upRequests = new TreeSet<Integer>();
        this.downRequests = new TreeSet<Integer>();

        this.floors = new EventBarrier[numFloors];
        for (int i = 0; i < this.numFloors; i++){
            this.floors[i] = new EventBarrier();
        }
        this.thread = new Thread(this, ""+this.elevatorId);
    }

    public Thread getThread(){
        return this.thread;
    }

    public boolean isGoingUp(){
        return this.isGoingUp;
    }

    public boolean isDoorOpen(){
        return this.isDoorOpen;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public int getNumFloors(){
        return this.numFloors;
    }

    public int getTotalRequests(){
        return this.upRequests.size() + this.downRequests.size();
    }

    public int getNumUpRequests(){
        return this.upRequests.size();
    }

    public int getNumDownRequests(){
        return this.downRequests.size();
    }
    /**
     * Elevator control interface: invoked by Elevator thread.
     */

	/* Signal incoming and outgoing riders */
    public synchronized void openDoors(){
        this.isDoorOpen = true;
        System.out.println("Elevator doors opened on " + this.currentFloor);
        System.out.println("Floor " + this.currentFloor + " has " + this.floors[this.currentFloor].waiters() + " riders waiting");
        try {
            sleep(50);
        } catch (InterruptedException e){
            this.floors[this.currentFloor].raise(this.elevatorId, this.currentFloor);
        }
    }

    /**
     * When capacity is reached or the outgoing riders are exited and
     * incoming riders are in.
     */
    public synchronized void closedDoors(){
        this.isDoorOpen = false;
        synchronized (this.building){
            this.building.notifyAll();
        }
        System.out.println("Elevator doors closed on " + this.currentFloor);
    }

    /**
     * Go to a requested floor
     *
     * @param floor floor elevator is requested to visit
     */
    public synchronized void visitFloor(int floor){
        if (this.isGoingUp){
            upRequests.remove(floor);
        } else {
            downRequests.remove(floor);
        }
        this.currentFloor = floor;
        if(this.isGoingUp){
            System.out.println("Elevator has moved up to " + this.currentFloor);
        } else {
            System.out.println("Elevator has moved down to " + this.currentFloor);
        }
    }

    /**
     * Elevator rider interface (part 1): invoked by rider threads.
     */

	/**
     * Enter the elevator
     *
     * @param riderId id of rider
     * */
    public synchronized boolean enter(int riderId){
        this.numRiders++;
        System.out.println("Rider " + riderId + "enters elevator on floor " + this.currentFloor);
        this.floors[this.currentFloor].complete(this.elevatorId, this.currentFloor);
        return true;
    }

    /**
     * Exit the elevator
     *
     * @param riderId
     */
    public synchronized void exit(int riderId){
        this.numRiders--;
        System.out.println("Rider " + riderId + "exits elevator on floor " + this.currentFloor);
        this.floors[this.currentFloor].complete(this.elevatorId, this.currentFloor);
    }

    /**
     * Pass elevator if elevator not going in rider's desired direction
     */
    public void pass(){
        this.floors[this.currentFloor].complete(this.elevatorId, this.currentFloor);
    }

    /**
     * Request floor
     * @param floorRequested
     * @param riderId
     */
    public void requestFloor(int floorRequested, int riderId){
        boolean goingUp = floorRequested > this.currentFloor;
        requestFloor(floorRequested, goingUp, riderId);
    }

    /**
     * Request floor
     * @param floorRequested
     * @param goingUp
     * @param riderId
     */
    public void requestFloor(int floorRequested, boolean goingUp, int riderId){
        synchronized (this){
            if(goingUp){
                upRequests.add(floorRequested);
            } else {
                downRequests.add(floorRequested);
            }
            notify();
        }
        floors[floorRequested].arrive(this.elevatorId, this.currentFloor, riderId);
    }

    /**
     * Elevator gets the floor of its next request
     * @return nearest floor of the next request
     */
    private synchronized int getNextFloor(){
         if (isGoingUp()){
             Integer next = this.upRequests.higher(this.currentFloor);
             boolean hasNext = next != null;
             if (hasNext){
                 return next;
             } else {
                 this.isGoingUp = false;
                 next = this.downRequests.lower(this.currentFloor);
                 boolean hasNextRequestFromLowerFloor = next != null;
                 if (hasNextRequestFromLowerFloor){
                     return next;
                 } else {
                     return -1;
                 }
             }
         } else {
             Integer next = this.downRequests.lower(this.currentFloor);
             boolean hasNext = next != null;
             if (hasNext){
                 return next;
             } else {
                 this.isGoingUp = true;
                 next = this.upRequests.higher(this.currentFloor);
                 boolean hasNextRequestFromHigherFloor = next != null;
                 if (hasNextRequestFromHigherFloor){
                     return next;
                 } else {
                     return -1;
                 }
             }
         }
    }

    public void run(){
        while(true){
            int nextFloor = getNextFloor();
            boolean noMoreRequests = upRequests.isEmpty() && downRequests.isEmpty();
            if (noMoreRequests){
                currentFloor = -1;
                try {
                    wait();
                } catch (InterruptedException e){
                    return;
                }
            } else if (nextFloor != -1){
                visitFloor(nextFloor);
                openDoors();
                closedDoors();
            }
        }
    }
}
