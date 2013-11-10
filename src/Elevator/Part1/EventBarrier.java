package Elevator.Part1;

public class EventBarrier {
    private int waiters;
    private boolean eventInProgress;

    public EventBarrier(){
        this.waiters = 0;
        this.eventInProgress = false;
    }

    public synchronized void arrive(){
        this.waiters++;
        if (this.eventInProgress) {
            return;
        }
        while(!this.eventInProgress){
            try {
                wait();
            } catch (InterruptedException e){
                System.out.println("InterruptedException caught");
            }
        }
    }

    public synchronized void raise(){
        if (this.eventInProgress){
            return;
        }
        this.eventInProgress = true;
        notifyAll();
        while(this.waiters != 0){
            try {
                wait();
            } catch (InterruptedException e){
                System.out.println("InterruptedException caught");
            }
        }
        this.eventInProgress = false;
    }

    public synchronized void complete(){
        this.waiters--;
        if (this.waiters == 0){
            notify();
        }
    }

    public int waiters(){
        return this.waiters;
    }
}
