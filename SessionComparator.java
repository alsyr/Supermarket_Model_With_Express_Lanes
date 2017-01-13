
import java.util.Comparator;

public class SessionComparator implements Comparator<Object>{

	public int compare( Object a, Object b){

		LaneManager v1 = (LaneManager)a; 
		LaneManager v2 = (LaneManager)b;

		if(v1.getExpressLimit() < v2.getExpressLimit()){
			return -1; 
		}
		else if(v1.getExpressLimit() == v2.getExpressLimit()){
			return 0; 
		}else
		{
			return 1;
		}
	} }
