package concurrentpatterns;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EagerService<T extends Runnable> {
        private static volatile EagerService singleService = new EagerService(); // eager-loading
        private boolean doWork = true; // when the service needs to stop
        private BlockingQueue<T> taskQueue;
        private Thread consumerThread;

        private EagerService(){ // accessed only by the class itself
            this.taskQueue = new LinkedBlockingQueue<>();
            this.consumerThread = new Thread(()->{
                // main logic of our service
                while(doWork){
                    try{
                        taskQueue.take().run();
                    } catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
            });
            consumerThread.start();
        }

    public static EagerService getServiceInstance(){
        return singleService;
    }
}
