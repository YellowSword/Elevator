package Elevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
//        File log = new File("elevator.log");
//        System.setOut(new PrintStream(new FileOutputStream(log)));

        Building building = new Building(5, 5, 3);
        Rider rider1 = new Rider(1, building, 2, 4);
        Rider rider2 = new Rider(2, building, 2, 4);
        Rider rider3 = new Rider(3, building, 1, 4);
        Rider rider4 = new Rider(4, building, 2, 3);
        Rider rider5 = new Rider(5, building, 3, 4);
        Rider rider6 = new Rider(6, building, 4, 0);
        Rider rider7 = new Rider(7, building, 2, 3);
        Rider rider8 = new Rider(8, building, 3, 0);
        Rider rider9 = new Rider(9, building, 0, 2);
        rider1.getThread().start();
        rider2.getThread().start();
        rider3.getThread().start();
        rider4.getThread().start();
        rider5.getThread().start();
        rider6.getThread().start();
        rider7.getThread().start();
        rider8.getThread().start();
        rider9.getThread().start();
        building.startElevators();
        rider1.getThread().join();
        rider2.getThread().join();
        rider3.getThread().join();
        rider4.getThread().join();
        rider5.getThread().join();
        rider6.getThread().join();
        rider7.getThread().join();
        rider8.getThread().join();
        rider9.getThread().join();
        building.stopElevators();
    }
}
