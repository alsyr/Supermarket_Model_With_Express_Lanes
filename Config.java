
public class Config {
  private int _numberLanes;
  private int _expressLanes;
  private int _minLimitExpressLanes;
  private int _maxLimitExpressLanes;

  public Config() {
  }

  public void setRange(int minLimitExpressLanes, int maxLimitExpressLanes) {
    _minLimitExpressLanes = minLimitExpressLanes;
    _maxLimitExpressLanes = maxLimitExpressLanes;
  }

  public void setLanes(int numberLanes, int expressLanes) {
    _numberLanes = numberLanes;
    _expressLanes = expressLanes;
  }

  public int getNumberLanes() {
    return _numberLanes;
  }

  public int getExpressLanes() {
    return _expressLanes;
  }

  public int getMinLimitExpressLanes() {
    return _minLimitExpressLanes;
  }

  public int getMaxLimitExpressLanes() {
    return _maxLimitExpressLanes;
  }
}