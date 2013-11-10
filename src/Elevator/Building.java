package Elevator;

public class Building {
    private int numFloors;
    private int capacity;
    private Elevator elevator;

    public Building(int numFloors, int capacity){
        this.numFloors = numFloors;
        this.capacity = capacity;
        this.elevator = new Elevator(1, this.numFloors, this.capacity, this);
    }

    /**
     * Signal an elevator
     *
     * @param fromFloor floor from which elevator is called
     * @param goingUp signals elevator to go up or down
     * @param riderId id of rider
     * @return        instance of the elevator to use to go either up or down
     */
    private Elevator call(int fromFloor, boolean goingUp, int riderId){
        synchronized (this){
            while(this.elevator.reachedFullCapacity()){
                try {
                    System.out.println("Elevator has reached full capacity.");
                    wait();
                } catch (InterruptedException e){

                }
            }
        }
        if (goingUp){
            System.out.println("Elevator " + this.elevator.getElevatorId() +  " called by rider " + riderId + " on floor " + fromFloor +" to go up");
        } else {
            System.out.println("Elevator " + this.elevator.getElevatorId() +  " called by rider " + riderId + " on floor " + fromFloor +" to go down");
        }
        elevator.requestFloor(fromFloor, goingUp, riderId);
        while(elevator.isGoingUp() != goingUp){
            elevator.pass();
            synchronized (this){
                while(elevator.isDoorOpen()){
                    try {
                        System.out.println("Elevator is not going in the right direction.");
                        wait();
                    } catch (InterruptedException e){

                    }
                }
            }
            elevator.requestFloor(fromFloor, goingUp, riderId);
        }

        return elevator;
    }

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
    public void startElevator(){
        this.elevator.getThread().start();
    }

    /**
     * Stop elevator thread
     */
    public void stopElevator(){
        this.elevator.getThread().interrupt();
    }

}
