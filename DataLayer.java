
import java.util.TreeSet;

public class DataLayer {
  private TreeSet<LaneManager> slist;

  public DataLayer() {
    slist = new TreeSet<LaneManager>(new SessionComparator());
  }

  public TreeSet<LaneManager> getSessions() {
    return slist;
  }

  public void addSession(LaneManager sm) {
    slist.add(sm);
  }
}
