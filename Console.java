
import javax.swing.JFrame;
public class Console
{
	public static void run(JFrame frame)
	{
		frame.setLocationRelativeTo( null );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE  );

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	} 
}