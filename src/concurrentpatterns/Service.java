package concurrentpatterns;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This service takes tasks from a queue and runs them in a separate thread
 * Resembles a Single ThreadPoolExecutor
 * the service must have a global access point and is heavy-resourced ?
 * global access point - one method (static) that returns the instance of the Service
 *
 * Singleton:
 * 1. Ensures creation of only one instance of the class
 * 2. supplies one global access point to it
 */
public class Service<T extends Runnable> {
    private static volatile Service singleService; // initialized to null by default
    private static ReentrantReadWriteLock lock;

    //    private static volatile Service singleService = new Service(); // eager-loading
    private boolean doWork = true; // when the service needs to stop
    private BlockingQueue<T> taskQueue;
    private Thread consumerThread;

    private Service(){ // accessed only by the class itself
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


    public static Service notThreadSafeInstance(){
        if (singleService == null){
            singleService = new Service();
        }
        return singleService;
    }
    /*
    Race conditions
    t1,t2
    t1 checks if singleService is null -> true
    t1's program counter is moved to line 36 but not executed
    t1 preempted
    t2 checks if singleService is null -> true
    t2's program counter is moved to line 36 but not executed
    t2 preempted
    t1 executes line 36 -> service is instantiated
    t1's program counter is moved to line 38 and executed
    singleService is returned (it has an address)
    t2 executes line 36 -> service is instantiated AGAIN
    t2 returns the second instance
     */

    /*
    Synchronization - some operations ought to be done atomically
    atomic operation - operation that is not preempted
    is x++ done atomically?
    int number = 0;
    number++;

    number++:
    1. int temp = number;
    2. temp = temp + 1;
    3. number = temp
    4. write to register

    Java synchronization is achieved by:
    - synchronized methods
    - synchronized blocks
    - Locks
    - Semaphores
    - Atomic variables
     */

    public synchronized static Service threadSafeInstance(){
        if (singleService == null){
            singleService = new Service();
        }
        return singleService;
    }

    public static Service threadSafeInstance2(){
        synchronized (Service.class){
            if (singleService == null){
                singleService = new Service();
            }
            return singleService;
        }
    }
    /*
    t1,t2
    t1 in line 94-> null. preempted
    t2 in line 94-> null. get the lock, initializes singleService
    t2 releases the lock than returns
    t1 gets the lock, creates a new instance, releases the lock and returns
     */
    public static Service threadSafeInstance3(){
            if (singleService == null){
                synchronized (Service.class){
                singleService = new Service();
            }
        }
        return singleService;
    }

    public static Service threadSafeInstance4(){
        if (singleService == null){
            synchronized (Service.class){
                singleService = new Service();
            }
        }
        return singleService;
    }

    // Double-checked locking pattern
    public static Service doubleCheckedLockingInstance(){
        if (singleService == null) {
            synchronized (Service.class) {
                if (singleService == null) {
                    singleService = new Service(); // lazy-loading
                    /*
                     by design, only one thread can eventually create
                     the singleService.
                     Because our instance is volatile, it ensures happens-before operation
                     */
                }
            }
        }
        return singleService;
    }

    // Double-checked locking pattern
    public static Service doubleCheckedLockingInstance2(){
        if (singleService == null) {
            lock.writeLock().lock();
                if (singleService == null) {
                    singleService = new Service(); // lazy-loading
                    /*
                     by design, only one thread can eventually create
                     the singleService.
                     Because our instance is volatile, it ensures happens-before operation
                     */
                }
            lock.writeLock().unlock();
        }
        return singleService;
    }

    public synchronized String threadInfo1(){
        return "Thread name: " + Thread.currentThread().getName()
        + " Thread priority: " + Thread.currentThread().getPriority();
    }

    public String threadInfo2(){
        synchronized (this){
            return "Thread name: " + Thread.currentThread().getName()
                    + " Thread priority: " + Thread.currentThread().getPriority();
        }
    }

    /*
    ReentrantReadWriteLock - a lock that contains 2 separate locks:
    1. ReadLock- support 2 major operations: ReadLock.lock() and ReadLock.unlock()
    2. WriteLock - 2 major operations: WriteLock.lock() and WriteLock.unlock()

    ReadLock
    ________
    ReadLock.lock() - if another thread is writing, lock will not be obtained
    if one or more threads that are reading (multiple threads can read),
    no writing thread (thread that tries to acquire WriteLock) can access the critical section
     */








}
