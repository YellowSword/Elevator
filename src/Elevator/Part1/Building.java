package Elevator.Part1;

public class Building {
    protected int numFloors;

    public Building(int numFloors){
        this.numFloors = numFloors;
    }

    /**
     * Signal an elevator that we want to go up
     *
     * @param fromFloor  floor from which elevator is called
     * @return           instance of the elevator to use to go up
     */
    public Elevator CallUp(int fromFloor){

    }

    /**
     * Signal an elevator that we want to go down
     *
     * @param fromFloor  floor from which elevator is called
     * @return           instance of the elevator to use to go down
     */
    public Elevator CallDown(int fromFloor){

    }

}
