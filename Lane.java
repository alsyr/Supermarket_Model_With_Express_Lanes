

public class Lane extends Thread {
  private boolean consume;
  QueueList theQueue;
  private int progress;

  public Lane(QueueList theQueue) {
    consume = true;
    this.theQueue = theQueue;
    progress = 0;
  }

  public int numberOfCustomers() {
    return theQueue.size();
  }

  public void stopIt() {
    consume = false;
    interrupt();
  }

  public void run() {
    while (theQueue.size() != 0 || consume == true) {
      try {
        theQueue.processCustomer();
        progress++;
      } catch (NullPointerException e) {
      }
    }
    System.out.println("Run from Lane exiting");
  }

  public int getProgress() {
    return progress;
  }
}
