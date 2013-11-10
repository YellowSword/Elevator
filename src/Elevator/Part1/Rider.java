package Elevator.Part1;

public class Rider implements Runnable {

    private String name;
    private Elevator elevator;
    private Building building;
    private Thread thread;

    public Rider(String name, Elevator elevator, Building building){
        this.name = name;
        this.elevator = elevator;
        this.building = building;
        this.thread = new Thread(this, this.name);
    }

    public Thread getThread(){
        return this.thread;
    }

    public void run(){

    }
}
