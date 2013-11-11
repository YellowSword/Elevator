package Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Building {
    private int numFloors;
    private int capacity;
    private int numElevators;
//    private Elevator elevator;

    private List<Elevator> elevators;
    private Elevator[] goingUpStops;
    private Elevator[] goingDownStops;

    /**
     *
     * @param numFloors
     * @param capacity
     * @param numElevators
     */
    public Building(int numFloors, int capacity, int numElevators){
        this.numFloors = numFloors;
        this.capacity = capacity;
//        this.elevator = new Elevator(1, this.numFloors, this.capacity, this);
        this.elevators = new ArrayList<Elevator>();

        for (int i = 0; i < numElevators; i++){
            elevators.add(new Elevator(i, this.numFloors, this.capacity, this));
        }

        this.goingUpStops = new Elevator[numFloors];
        this.goingDownStops = new Elevator[numFloors];
    }

    /**
     *
     * @param requestedFloor
     * @param isGoingUp
     * @param elevator
     */
    public synchronized void addStop(int requestedFloor, boolean isGoingUp, Elevator elevator){
        if(isGoingUp){
            this.goingUpStops[requestedFloor] = elevator;
        } else {
            this.goingDownStops[requestedFloor] = elevator;
        }
    }

    /**
     *
     * @param arrivedFloor
     * @param isGoingUp
     */
    public synchronized void removeStop(int arrivedFloor, boolean isGoingUp){
        if(isGoingUp){
            this.goingUpStops[arrivedFloor] = null;
        } else {
            this.goingDownStops[arrivedFloor] = null;
        }
    }

    /**
     *
     * @param floor
     * @param isGoingUp
     * @return
     */
    public synchronized Elevator find(int floor, boolean isGoingUp){
        Elevator elevator;
        boolean hasElevatorGoingUpToFloor = this.goingUpStops[floor] != null;
        boolean hasElevatorGoingDownToFloor = this.goingDownStops[floor] != null;
        if (isGoingUp && hasElevatorGoingUpToFloor){
            elevator = this.goingUpStops[floor];
            return elevator;
        } else if (!isGoingUp && hasElevatorGoingDownToFloor){
            elevator = this.goingDownStops[floor];
            return elevator;
        } else {
            Random random = new Random();
            int randomElevatorIndex = random.nextInt(this.elevators.size());
            elevator = elevators.get(randomElevatorIndex);
            this.addStop(floor, isGoingUp, elevator);
            return elevator;
        }
    }

    private Elevator call(int fromFloor, boolean goingUp, int riderId){
        Elevator elevator = find(fromFloor, goingUp);
        synchronized (this){
            while(elevator.reachedFullCapacity()){
                try {
                    System.out.println("Elevator has reached full capacity.");
                    wait();
                } catch (InterruptedException e){

                }
            }
        }
        if (goingUp){
            System.out.println("Elevator " + elevator.getElevatorId() +  " called by rider " + riderId + " on floor " + fromFloor +" to go up");
        } else {
            System.out.println("Elevator " + elevator.getElevatorId() +  " called by rider " + riderId + " on floor " + fromFloor +" to go down");
        }
        elevator.requestFloor(fromFloor, goingUp, riderId);
//        while(elevator.isGoingUp() != goingUp){
//            elevator.pass();
//            synchronized (this){
//                while(elevator.isDoorOpen()){
//                    try {
//                        System.out.println("Elevator is not going in the requested direction.");
//                        wait();
//                    } catch (InterruptedException e){
//
//                    }
//                }
//            }
//            elevator.requestFloor(fromFloor, goingUp, riderId);
//        }

        return elevator;
    }



    /**
     * Signal an elevator
     *
     * @param fromFloor floor from which elevator is called
     * @param goingUp signals elevator to go up or down
     * @param riderId id of rider
     * @return        instance of the elevator to use to go either up or down
     */
//    private Elevator call(int fromFloor, boolean goingUp, int riderId){
//        synchronized (this){
//            while(this.elevator.reachedFullCapacity()){
//                try {
//                    System.out.println("Elevator has reached full capacity.");
//                    wait();
//                } catch (InterruptedException e){
//
//                }
//            }
//        }
//        if (goingUp){
//            System.out.println("Elevator " + this.elevator.getElevatorId() +  " called by rider " + riderId + " on floor " + fromFloor +" to go up");
//        } else {
//            System.out.println("Elevator " + this.elevator.getElevatorId() +  " called by rider " + riderId + " on floor " + fromFloor +" to go down");
//        }
//        this.elevator.requestFloor(fromFloor, goingUp, riderId);
//        while(this.elevator.isGoingUp() != goingUp){
//            this.elevator.pass();
//            synchronized (this){
//                while(this.elevator.isDoorOpen()){
//                    try {
//                        System.out.println("Elevator is not going in the requested direction.");
//                        wait();
//                    } catch (InterruptedException e){
//
//                    }
//                }
//            }
//            this.elevator.requestFloor(fromFloor, goingUp, riderId);
//        }
//
//        return this.elevator;
//    }

    /**
     * Signal an elevator that we want to go up
     *
     * @param fromFloor  floor from which elevator is called
     * @return           instance of the elevator to use to go up
     */
    public Elevator callUp(int fromFloor, int riderId){
        return call(fromFloor, true, riderId);
    }

    /**
     * Signal an elevator that we want to go down
     *
     * @param fromFloor  floor from which elevator is called
     * @param riderId    id of the rider
     * @return           instance of the elevator to use to go down
     */
    public Elevator callDown(int fromFloor, int riderId){
        return call(fromFloor, false, riderId);
    }

    /**
     * Start elevator thread
     */
//    public void startElevator(){
//        this.elevator.getThread().start();
//    }

    public void startElevators(){
        for (Elevator elevator : elevators){
            elevator.getThread().start();
        }
    }

    /**
     * Stop elevator thread
     */
//    public void stopElevator(){
//        this.elevator.getThread().interrupt();
//    }

    public void stopElevators(){
        for (Elevator elevator : elevators){
            elevator.getThread().interrupt();
        }
    }

}
