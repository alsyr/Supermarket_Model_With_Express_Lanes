import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.beans.*;
import java.util.*;

public class UILayer
{
	private DataLayer dlayer;
	private BusinessLayer blayer;
	private Container c1;
	private JTextField _nl, _el, _br, _er;
	private JProgressBar pb = new JProgressBar(0, 100);;

	private Session task;
	private JTextArea taskOutput;
	private JPanel graphPanel;
	private JButton _launch= new JButton("Launch");

	public UILayer(Container c){
		c1 = c;

	}

	public DataLayer getDataLayer(){
		return dlayer;
	}

	public BusinessLayer getBusinessLayer(){
		return blayer;
	}

	public void setDataLayer(DataLayer layer){
		dlayer = layer;
	}

	public void setBusinessLayer(BusinessLayer layer){
		blayer = layer;
	}

	public void addControls(){
		c1.setLayout(new CardLayout()); 
		initInputDataPanel();
	}

	public void initInputDataPanel(){
		JPanel centerPanel;
		JLabel _numberLanes; 
		JLabel _expressLanes;
		JLabel _beginningRange; 

		JLabel _endingRange ; 
		JLabel _limitCustomers; 
		JLabel _limitItems;
		//		JButton _launch;

		centerPanel = new JPanel();

		centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		centerPanel.setLayout(new GridLayout(0,2));

		_numberLanes = new JLabel("Number of lanes:");
		_expressLanes = new JLabel("Number of express lanes:");
		_beginningRange = new JLabel("Beginning express lane item limit range:");
		_endingRange  = new JLabel("Ending express lane item limit range:");
		_limitCustomers = new JLabel("Total number of customers used at each limit is 200");
		_limitItems = new JLabel("Maximum number of items that can be in a customer's cart is 50");
		_nl = new JTextField(10);
		_el = new JTextField(10);
		_br = new JTextField(10);
		_er = new JTextField(10);

		_nl.addActionListener(new TextClickListener(_nl));
		_el.addActionListener(new TextClickListener(_el));
		_br.addActionListener(new TextClickListener(_br));
		_er.addActionListener(new TextClickListener(_er));

		//		_launch = new JButton("Launch");
		_launch.addActionListener(new EnterClickListener());

		pb.setValue(0);

		pb.setStringPainted(true);

		centerPanel.add(_numberLanes);
		centerPanel.add(_nl);
		centerPanel.add(_expressLanes);
		centerPanel.add(_el);
		centerPanel.add(_beginningRange);
		centerPanel.add(_br);
		centerPanel.add(_endingRange );
		centerPanel.add(_er); 
		centerPanel.add(_limitCustomers); 
		centerPanel.add(_limitItems); 

		centerPanel.add(_launch);
		centerPanel.add(pb);

		c1.add(centerPanel, "Main Panel"); 
	}

	public void initProgressBar()
	{
		pb.setValue(0);
		pb.setStringPainted(true);
		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false); 
	}

	public void initGraphPanel(JPanel chart)
	{
		graphPanel = chart; 
		c1.add(graphPanel, "Graph Panel");
	}

	public void showProgressBar()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				initProgressBar(); 
				showProgressBarPanel();
			}
		}); 
	}

	public void showGraph(JPanel chart)
	{
		initGraphPanel(chart);
		showGraphPanel();
	}

	protected class TextClickListener implements ActionListener
	{
		JTextField t;
		TextClickListener(JTextField t)
		{
			this.t = t;
		}
		@Override
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				Integer.parseInt(event.getActionCommand());
				if(!(event.getActionCommand().equals(null )))
				{
					validateForm();
					validateNumbers();
					t.transferFocus();
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error.\nPlease enter number.", 
						"Message", JOptionPane.INFORMATION_MESSAGE); 
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error.\n" + e.getMessage() , 
						"Message", JOptionPane.INFORMATION_MESSAGE  ); 

			}
		}
	}

	private void validateForm() throws Exception
	{
		if(!_nl.getText().equals("") && !_el.getText().equals("") &&
				Integer.parseInt(_nl.getText()) <=Integer.parseInt(_el.getText())) 
			throw new Exception("Too many express lanes!! \n Please enter correct number!");

		if(!_br.getText().equals("") && !_er.getText().equals("") &&
				Integer.parseInt(_br.getText()) >Integer.parseInt(_er.getText())) 
			throw new Exception("Express lane item limit range beginning is grater than its ending!! \n " + 
					"Please enter correct numbers!");
	}

	private void validateNumbers() throws Exception {
		if(!_nl.getText().equals("") &&
				Integer.parseInt(_nl.getText()) <= 0 ) 
			throw new Exception("Wrong number of lanes!! \n Please enter positive number!"); 
		if(!_el.getText().equals("") &&
				Integer.parseInt(_el.getText()) <= 0 ) 
			throw new Exception("Wrong number of express lanes!! \n Please enter positive number!");
		if(!_br.getText().equals("") &&
				Integer.parseInt(_br.getText()) <= 0 ) 
			throw new Exception("Wrong number of express lanes range beginnig!! \n Please enter positive number!"); 
		_br.addActionListener(new TextClickListener(_br) ); 
		if(!_er.getText().equals("") &&
				Integer.parseInt(_er.getText()) <= 0 ) 
			throw new Exception("Wrong number of express lanes range ending!! \n Please enter positive number!"); 
	}

	protected class EnterClickListener implements ActionListener
	{
		private Config info;
		EnterClickListener()
		{
			info = new Config();
		}

		@Override
		public void actionPerformed(ActionEvent event )
		{
			try
			{
				if(!(event.getActionCommand().equals(null))) {
					validateForm(); 
					validateNumbers();


					info.setRange(Integer.parseInt(_br.getText()), Integer.parseInt(_er.getText()));
					info.setLanes(Integer.parseInt(_nl.getText()), Integer.parseInt(_el.getText()));
					showProgressBar();
					task = blayer.startSimulation(info);
					task.addPropertyChangeListener(new PropertyChangeListener());
					task.execute();
					_launch.setEnabled(false);
				} 
			} 
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Error.\nPlease enter number.", 
						"Message", JOptionPane.INFORMATION_MESSAGE );
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error.\n" + e.getMessage() , 
						"Message", JOptionPane.INFORMATION_MESSAGE  ); 
			} 
		} 
	}

	protected class PropertyChangeListener implements java.beans.PropertyChangeListener
	{
		PropertyChangeListener() { }

		public void propertyChange(PropertyChangeEvent evt)
		{
			if ("progress" == evt.getPropertyName())
			{
				int progress = (Integer) evt.getNewValue();
				pb.setValue(progress);
			}
			else if ("state".equals(evt.getPropertyName()) && evt.getNewValue() == SwingWorker.StateValue.DONE)
			{
				pb.setValue(100);
				ArrayList<Long> npoints = new ArrayList<Long> ();
				Iterator<LaneManager> iterator = dlayer.getSessions().iterator();

				while (iterator.hasNext())
				{
					LaneManager s1 = (LaneManager)iterator.next(); 
					npoints.add(s1.getTime());
				}

				BarGraphRotate chart = new BarGraphRotate(npoints, c1.getHeight()); 
				showGraph(chart);
			}
		}
	}

	public void showProgressBarPanel()
	{
		CardLayout card1 = (CardLayout)c1.getLayout(); 
		card1.show( c1, "Progress Bar Panel" );
	}

	public void showGraphPanel()
	{
		CardLayout card1 = (CardLayout)c1.getLayout(); 
		card1.show( c1, "Graph Panel" );
	}
}