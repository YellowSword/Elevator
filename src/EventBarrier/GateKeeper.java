/**
 * Created with IntelliJ IDEA.
 * User: bitsbytesnblues
 * Date: 11/2/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class GateKeeper implements Runnable {

    private String name;
    private EventBarrier eventBarrier;
    private Thread thread;

    public GateKeeper(EventBarrier eventBarrier, String name){
        this.name = name;
        this.eventBarrier = eventBarrier;
        this.thread = new Thread(this, this.name);
    }

    public Thread getThread(){
        return this.thread;
    }

    public void run() {
        eventBarrier.raise();
    }
}
