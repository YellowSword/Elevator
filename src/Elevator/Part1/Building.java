package Elevator.Part1;

public class Building {
    private int numFloors;
    private Elevator elevator;

    public Building(int numFloors){
        this.numFloors = numFloors;
        this.elevator = new Elevator(1, this.numFloors, this);
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
        if (goingUp){
            System.out.println("Elevator " + this.elevator.getElevatorId() +  " called by rider " + riderId + " on floor " + fromFloor +" to go up");
        } else {
            System.out.println("Elevator " + this.elevator.getElevatorId() +  " called by rider " + riderId + " on floor " + fromFloor +" to go down");
        }
        elevator.requestFloor(fromFloor, goingUp, riderId);
//        while(elevator.isGoingUp() != goingUp){
//            elevator.pass();
//            synchronized (this){
//                while(elevator.isDoorOpen()){
//                    try {
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
