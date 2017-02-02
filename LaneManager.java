

import java.util.ArrayList;

public class LaneManager extends Thread {
  private ArrayList<Lane> _normalLaneList;
  private ArrayList<QueueList> _queueLaneList;
  private ArrayList<Lane> _expressLaneList;
  private ArrayList<Lane> _newList;

  private int _numberLanes;
  private int _expressLanes;
  private int _limitExpressLane;
  private int progress;
  private long startTime;
  private long endTime;
  private long totalTime;

  public LaneManager(int numberLanes, int expressLanes, int limitExpressLane) {
    startTime = 0;
    endTime = 0;
    totalTime = 0;
    progress = 0;
    _numberLanes = numberLanes;
    _expressLanes = expressLanes;
    _limitExpressLane = limitExpressLane;
    _normalLaneList = new ArrayList<Lane>();
    _expressLaneList = new ArrayList<Lane>();
    _queueLaneList = new ArrayList<QueueList>();
    _newList = new ArrayList<Lane>();
  }

  public void createQueueList(int numberLanes) {
    for (int i = 0; i < numberLanes; i++) {
      _queueLaneList.add(new QueueList());
    }
  }

  public void createLanes(int numberLanes, int expressLanes) {
    for (int i = 0; i < numberLanes; i++) {
      if (i < expressLanes)
        _expressLaneList.add(new Lane(_queueLaneList.get(i)));
      else
        _normalLaneList.add(new Lane(_queueLaneList.get(i)));
    }
  }

  public void createListOfLanesToStop(ArrayList<Lane> l1, ArrayList<Lane> l2) {

    for (int i = 0; i < _normalLaneList.size(); i++) {
      _newList.add(_normalLaneList.get(i));
    }
    for (int i = 0; i < _expressLaneList.size(); i++) {
      _newList.add(_expressLaneList.get(i));
    }
  }

  public long getTime() {
    return totalTime;
  }

  public int getProgress() {
    progress = 0;
    for (Lane l : _normalLaneList)
      progress += l.getProgress();
    for (Lane l : _expressLaneList)
      progress += l.getProgress();
    return progress;
  }

  public int getExpressLimit() {
    return _limitExpressLane;
  }

  public void run() {

    createQueueList(_numberLanes);

    createLanes(_numberLanes, _expressLanes);

    createListOfLanesToStop(_normalLaneList, _expressLaneList);

    Factory customerFactory = new Factory(_queueLaneList, _expressLanes, _limitExpressLane, _newList);

    startTime = System.currentTimeMillis();

    customerFactory.start();

    for (Lane ln : _normalLaneList)
      ln.start();
    for (Lane ln : _expressLaneList)
      ln.start();

    try {
      customerFactory.join();
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    for (Lane ln : _normalLaneList)
      try {
        ln.join();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    for (Lane ln : _expressLaneList)
      try {
        ln.join();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    endTime = System.currentTimeMillis();
    totalTime = endTime - startTime;

    System.out.println("Time of simulation: " + totalTime);
  }

  //	public static void main(String[] args){
  //
  //		LaneManager l1 = new LaneManager(6,2,1);
  //		LaneManager l2 = new LaneManager(6,2,2);
  //		LaneManager l3 = new LaneManager(6,2,3);
  //		LaneManager l4 = new LaneManager(6,2,4);
  //		LaneManager l5 = new LaneManager(6,2,5);
  //
  //		System.out.println("Simulation 1");
  //		l1.run();
  //		System.out.println("Simulation 2");
  //		l2.run();
  //
  //		System.out.println("Main exiting");
  //
  //	}
}
