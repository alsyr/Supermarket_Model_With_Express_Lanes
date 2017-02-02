import java.beans.PropertyChangeEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class BusinessLayer {

  private DataLayer dlayer;
  private UILayer ulayer;
  private Session manager;

  public DataLayer getDataLayer() {
    return dlayer;
  }

  public UILayer getUILayer() {
    return ulayer;
  }

  public void setDataLayer(DataLayer layer) {
    dlayer = layer;
  }

  public void setUILayer(UILayer layer) {
    ulayer = layer;
  }

  public Session startSimulation(Config info) {
    manager = new Session(info);
    manager.addPropertyChangeListener(new PropertyChangeListener());
    return manager;
  }

  public double getSimulationProgress() {
    return manager.getProgress();
  }

  protected class PropertyChangeListener implements java.beans.PropertyChangeListener {

    PropertyChangeListener() {
    }

    // Invoked when task's progress property changes.
    public void propertyChange(PropertyChangeEvent evt) {
      if ("state".equals(evt.getPropertyName()) && evt.getNewValue() == SwingWorker.StateValue.DONE) {
        try {
          for (LaneManager sm : manager.get()) {
            dlayer.addSession(sm);
          }
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (ExecutionException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }
}
