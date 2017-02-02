import java.util.ArrayList;

public class Factory extends Thread {

  ArrayList<QueueList> theQueueList;
  int _limitExpressLane;
  private int _expressLanes;
  ArrayList<Lane> _listToStop;

  Factory(ArrayList<QueueList> theQueueList, int expressLanes, int limitExpressLane,
          ArrayList<Lane> listToStop) {
    _listToStop = listToStop;
    _expressLanes = expressLanes;
    _limitExpressLane = limitExpressLane;
    this.theQueueList = theQueueList;
  }

  public void run() {
    int totalNumberOfCustomers = 0;
    while (totalNumberOfCustomers < 200) {
      QueueList q1 = null;
      QueueList q2 = null;
      Customer c = new Customer(50);

      if (c.getNumberOfItems() < _limitExpressLane) {
        for (int k = 0; k < _expressLanes; k++) {
          if (q1 == null || ((theQueueList.get(k)).size()) < q1.size())
            q1 = theQueueList.get(k);
        }
        q1.addCustomer(c);
      } else {
        for (int k = _expressLanes; k < theQueueList.size(); k++) {
          if (q2 == null || ((theQueueList.get(k)).size()) < q2.size())
            q2 = theQueueList.get(k);
        }
        q2.addCustomer(c);
      }
      totalNumberOfCustomers++;
    }
    System.out.println(totalNumberOfCustomers);
    for (Lane k : _listToStop) {
      k.stopIt();
    }
    System.out.println("Run from Factory exiting");
  }
}


