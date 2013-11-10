package Elevator.Part1;

public class Rider implements Runnable {

    private int riderId;
    private Elevator elevator;
    private Building building;
    private Thread thread;

    public Rider(int riderId, Elevator elevator, Building building){
        this.riderId = riderId;
        this.elevator = elevator;
        this.building = building;
        this.thread = new Thread(this, ""+this.riderId);
    }

    public Thread getThread(){
        return this.thread;
    }

    public int getRiderId(){
        return this.riderId;
    }

    public void run(){

    }
}
