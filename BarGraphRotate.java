import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BarGraphRotate extends JPanel {
  GenerateDataRotate data;

  public BarGraphRotate(ArrayList<Long> npoints, int factor) {
    setupGUI(npoints, factor);
  }

  public GenerateDataRotate getData() {
    return data;
  }

  public void setupGUI(ArrayList<Long> npoints, int factor) {
    data = new GenerateDataRotate();
    data.setData(npoints);
    data.AnalyzeData(factor);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (data != null) {
      g.setColor(Color.BLUE);
      g.fill3DRect(0, 0, getWidth(), getHeight(), false);
      g.setColor(Color.ORANGE);
      int widthStart = 0;
      int width = getWidth() / data.Count();
      for (Integer i : data.getScale()) {
        int height = Math.abs(i - getHeight());
        g.fill3DRect(widthStart, height, width, getHeight(), true);
        widthStart += width;
      }
    }
  }
}
