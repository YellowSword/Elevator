import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bitsbytesnblues
 * Date: 11/2/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventBarrierDemo {
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
