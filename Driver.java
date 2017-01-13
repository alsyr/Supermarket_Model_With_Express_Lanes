
import javax.swing.JFrame;

public class Driver extends JFrame
{
	UILayer uiLayer;
	BusinessLayer businessLayer; 
	DataLayer dataLayer;

	public void init()
	{
		uiLayer = new UILayer(getContentPane());
		businessLayer = new BusinessLayer();
		dataLayer = new DataLayer();
		businessLayer.setDataLayer(dataLayer);
		businessLayer.setUILayer(uiLayer);
		uiLayer.setDataLayer(dataLayer);
		uiLayer.setBusinessLayer(businessLayer); 
	}

	public void start()
	{
		uiLayer. addControls();
	}

	public Driver( String title )
	{
		super(title);
		init();
		start(); 
	}

	public static void main(String [ ] args)
	{
		javax.swing.SwingUtilities. invokeLater(new Runnable()
		{
			public void run()
			{
				Driver driver = new Driver("ExpressLane Simulation");
				Console.run(driver); }
		});
	}
}