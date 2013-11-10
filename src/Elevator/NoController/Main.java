package Elevator.NoController;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Building building = new Building(5, 5);
        Rider rider1 = new Rider(1, building, 2, 4);
        Rider rider2 = new Rider(2, building, 2, 4);
        Rider rider3 = new Rider(3, building, 1, 4);
        Rider rider4 = new Rider(4, building, 2, 3);
        Rider rider5 = new Rider(5, building, 3, 4);
        Rider rider6 = new Rider(6, building, 0, 2);
        rider1.getThread().start();
        rider2.getThread().start();
        rider3.getThread().start();
        rider4.getThread().start();
        rider5.getThread().start();
        rider6.getThread().start();
        building.startElevator();
        rider1.getThread().join();
        rider2.getThread().join();
        rider3.getThread().join();
        rider4.getThread().join();
        rider5.getThread().join();
        rider6.getThread().join();
        building.stopElevator();

    }
}
