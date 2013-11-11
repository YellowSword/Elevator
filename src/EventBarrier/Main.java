package EventBarrier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();

        int numMinstrels = random.nextInt(100);

        EventBarrier eventBarrier = new EventBarrier();
        GateKeeper gateKeeper = new GateKeeper(eventBarrier, "Gatekeeper");
        List<Minstrel> minstrels = new ArrayList<Minstrel>();
        for (int i = 0 ; i < numMinstrels; i++){
            minstrels.add(new Minstrel(eventBarrier, "Minstrel " + i));
        }
        for (Minstrel minstrel : minstrels){
            minstrel.getThread().start();
        }

        gateKeeper.getThread().start();

        for (Minstrel minstrel : minstrels){
            minstrel.getThread().join();
        }

        gateKeeper.getThread().interrupt();
    }


}
