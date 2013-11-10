package EventBarrier;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        EventBarrier eventBarrier = new EventBarrier();
        GateKeeper gateKeeper = new GateKeeper(eventBarrier, "Gatekeeper");
        List<Minstrel> minstrels = new ArrayList<Minstrel>();
        for (int i = 0 ; i < 10; i++){
            minstrels.add(new Minstrel(eventBarrier, "Minstrel" + i));
        }
        for (Minstrel minstrel : minstrels){
            minstrel.getThread().start();
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e){
            System.out.println("Main thread interrupted.");
        }
        gateKeeper.getThread().start();
    }


}
