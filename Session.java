

import java.util.ArrayList;
import javax.swing.SwingWorker;

public class Session extends SwingWorker<ArrayList<LaneManager>, Void> {

  private Config info;
  private ArrayList<LaneManager> _allTheLaneManagers;
  private Integer progress;
  private int totalProgress;

  public Session(Config data) {
    this.info = data;
    this.progress = 0;
    totalProgress = (info.getMaxLimitExpressLanes() - info.getMinLimitExpressLanes() + 1) * 200;
    _allTheLaneManagers = new ArrayList<LaneManager>();
  }

  public void runSimulation() {
    while (getProgress() < 100) {
      try {
        Thread.sleep(200);
        progress++;
        System.out.println(getProgress());
      } catch (InterruptedException ignore) {
      }
    }
  }

  public int getSimulationProgress() {
    progress = 0;
    for (LaneManager lm : _allTheLaneManagers) {
      progress += lm.getProgress();
    }
    return (int) ((progress / (totalProgress * 1.0)) * 100);
  }

  @Override
  protected ArrayList<LaneManager> doInBackground() throws Exception {
    int progress = 0;
    // Initialize progress property.
    setProgress(0);
    for (int i = info.getMinLimitExpressLanes(); i <= info.getMaxLimitExpressLanes(); i++) {
      LaneManager lm;
      lm = new LaneManager(info.getNumberLanes(), info.getExpressLanes(), i);
      _allTheLaneManagers.add(lm);
    }

    for (LaneManager l : _allTheLaneManagers) {
      l.start();
    }

    while (progress < 100) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException ignore) {
      }
      progress = getSimulationProgress();
      setProgress(Math.min(progress, 100));
    }
    return _allTheLaneManagers;
  }
}
