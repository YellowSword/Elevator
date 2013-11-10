package Elevator.Part1;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Building building = new Building(5);
        Rider rider = new Rider(1, building);
        rider.getThread().start();
        building.startElevator();
        rider.getThread().join();
        building.stopElevator();

    }
}
