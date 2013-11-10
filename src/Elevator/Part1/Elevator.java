package Elevator.Part1;

import EventBarrier.EventBarrier;

public class Elevator {

    private int numFloors;
    private int currentFloor;
    private int numRiders;
    private boolean isGoingUp;
    private boolean hasDoorsOpen;
    private ElevatorController elevatorController;
    private Building building;
    private EventBarrier[] floors;

    public Elevator(int numFloors, ElevatorController elevatorController, Building building){
        this.numFloors = numFloors;
        this.elevatorController = elevatorController;
        this.building = building;
        this.currentFloor = -1;
        this.floors = new EventBarrier[numFloors];
        this.numRiders = 0;
        this.hasDoorsOpen = false;
        this.isGoingUp = true;
        for (int i = 0; i < this.numFloors; i++){
            this.floors[i] = new EventBarrier();
        }
    }

    public boolean isGoingUp(){
        return this.isGoingUp;
    }

    public boolean hasDoorsOpen(){
        return this.hasDoorsOpen;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public int getNumFloors(){
        return this.numFloors;
    }

    /**
     * Elevator control interface: invoked by Elevator thread.
     */

	/* Signal incoming and outgoing riders */
    public void openDoors(){

    }

    /**
     * When capacity is reached or the outgoing riders are exited and
     * incoming riders are in.
     */
    public void closedDoors(){

    }

    /* Go to a requested floor */
    public void visitFloor(int floor){

    }

    /**
     * Elevator rider interface (part 1): invoked by rider threads.
     */

	/* Enter the elevator */
    public boolean enter(){

    }

    public void exit(){

    }

    public void requestFloor(int floor){

    }

}
