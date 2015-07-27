package sortie.parfile;

import javax.swing.SwingUtilities;

/**
* * This is the 3rd version of SwingWorker (also known as
 * SwingWorker 3), an abstract class that you subclass to
 * perform GUI-related work in a dedicated thread.
 *
 * Note that the API changed slightly in the 3rd version:
 * You must now invoke start() on the SwingWorker after
 *  creating it.
 *
 * I (LEM) downloaded this from the Java tutorial.  This allows me to do some
 * minor multi-threading.
 */
public abstract class SwingWorker {
  private Object value; /**< see getValue(), setValue() */

  /**
   * Class to maintain reference to current worker thread
   * under separate synchronization control.
   * 12-8-2004 (LEM) - made this protected so I could have access to it
   * in the children
   */
  protected static class ThreadVar {

    /** Worker thread */
    private Thread thread;

    /**
     * Constructor
     * @param t Thread current worker thread
     */
    ThreadVar(Thread t) {
      thread = t;
    }

    /**
     * Get the current worker thread.
     * @return Thread Current worker thread.
     */
    synchronized Thread get() {
      return thread;
    }

    /**
     * Clear the current worker thread.
     */
    synchronized void clear() {
      thread = null;
    }

    /**
    * Sleeps the thread
    * @param millis How long to sleep the thread.
    * @throws InterruptedException if the thread cannot be slept
    * Created 12-8-2004 (LEM)
    */
    public void sleep(long millis) throws java.lang.InterruptedException {
      Thread.sleep(millis);
    }

    /**
     * Waits the thread.
     * @throws InterruptedException if the thread cannot wait
     */
    public void threadWait() throws java.lang.InterruptedException {
      thread.wait();
    }
  }

  /**12-8-2004 (LEM) - made this protected so I could have access to it
   * in the children*/
  protected ThreadVar threadVar;

  /**
   * Get the value produced by the worker thread, or null if it
   * hasn't been constructed yet.
   * @return Object Value produced by worker thread
   */
  protected synchronized Object getValue() {
    return value;
  }

  /**
   * Set the value produced by worker thread
   * @param x Value produced by worker thread
   */
  private synchronized void setValue(Object x) {
    value = x;
  }

  /**
   * Compute the value to be returned by the get method.
   * @return value to be returned by get method
   */
  public abstract Object construct();

  /**
   * Called on the event dispatching thread (not on the worker thread)
   * after the construct method has returned.
   */
  public void finished() {}

  /**
   * A new method that interrupts the worker thread. Call this method
   * to force the worker to stop what it's doing.
   */
  public void interrupt() {
    Thread t = threadVar.get();
    if (t != null) {
      t.interrupt();
    }
    threadVar.clear();
  }

  /**
   * Return the value created by the construct method.
   * Returns null if either the constructing thread or the current
   * thread was interrupted before a value was produced.
   *
   * @return the value created by the construct method
   */
  public Object get() {
    while (true) {
      Thread t = threadVar.get();
      if (t == null) {
        return getValue();
      }
      try {
        t.join();
      }
      catch (InterruptedException e) {
        Thread.currentThread().interrupt(); // propagate
        return null;
      }
    }
  }

  /**
   * Start a thread that will call the construct method
   * and then exit. */
  public SwingWorker() {
    final Runnable doFinished = new Runnable() {
      public void run() {
        finished();
      }
    };
    Runnable doConstruct = new Runnable() {
      public void run() {
        try {
          setValue(construct());
        }
        finally {
          threadVar.clear();
        }
        SwingUtilities.invokeLater(doFinished);
      }
    };
    Thread t = new Thread(doConstruct);
    threadVar = new ThreadVar(t);
  }

  /**
   * Start the worker thread.
   */
  public void start() {
    Thread t = threadVar.get();
    if (t != null) {
      t.start();
    }
  }
}
