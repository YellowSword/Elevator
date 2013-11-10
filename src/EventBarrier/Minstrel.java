import oracle.jrockit.jfr.ProducerDescriptor;

/**
 * Created with IntelliJ IDEA.
 * User: bitsbytesnblues
 * Date: 11/2/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Minstrel implements Runnable {

    private String name;
    private EventBarrier eventBarrier;
    private Thread thread;

    public Minstrel(EventBarrier eventBarrier, String name){
        this.name = name;
        this.eventBarrier = eventBarrier;
        this.thread = new Thread(this, this.name);
    }

    public Thread getThread(){
        return this.thread;
    }

    public void run(){
        eventBarrier.arrive();
        try {
            long randomTime = (long) (Math.random() * 10000);
            System.out.println(this.thread.getName() + " sleeping for " + randomTime + " ms");
            Thread.sleep(randomTime);
        } catch (InterruptedException e){
            System.out.println(this.thread.getName() + "interrupted.");
        }
        eventBarrier.complete();
    }

}
