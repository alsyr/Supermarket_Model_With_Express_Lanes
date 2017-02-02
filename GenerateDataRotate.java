
import java.util.*;

public class GenerateDataRotate {
  Random rand = new Random(System.currentTimeMillis());
  ArrayList<Long> ilist;
  ArrayList<Integer> iscale;
  Integer numberPoints = -1;
  Long smallest = Long.MAX_VALUE;
  Long largest = Long.MIN_VALUE;

  public GenerateDataRotate() {
    ilist = new ArrayList<Long>();
    iscale = new ArrayList<Integer>();
  }

  public ArrayList<Long> getList() {
    return ilist;
  }

  public ArrayList<Integer> getScale() {
    return iscale;
  }

  public Long getSmallest() {
    return smallest;
  }

  public Long getLargest() {
    return largest;
  }

  public Integer Count() {
    return numberPoints;
  }

  public Long range() {
    return largest - smallest;
  }

  public void generateData(int count) {
    numberPoints = count;
    for (int x = 0; x < count; x++)
      ilist.add(new Long(rand.nextInt(100)));
  }

  public void setData(ArrayList<Long> npoints) {
    numberPoints = npoints.size();
    ilist = npoints;
  }

  public Long minimum() {
    for (Long i : ilist) {
      if (i < smallest) smallest = i;
    }
    return smallest;
  }

  public Long maximum() {
    for (Long i : ilist) {
      if (i > largest) largest = i;
    }
    return largest;
  }

  public void scaleData(int factor) {
    System.out.println("height = " + factor);
    for (Long i : ilist) {
      float percentage = (float) i / (float) largest;
      float adjust = percentage * factor;
      System.out.println(i + " " + adjust);
      iscale.add((int) adjust);
    }
  }

  public void AnalyzeData(int factor) {
    minimum();
    maximum();
    scaleData(factor);
  }
}