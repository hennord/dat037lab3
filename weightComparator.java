

import java.util.Comparator;

public class weightComparator implements Comparator<weightedEdge>{
	@Override
	public int compare(weightedEdge e1, weightedEdge e2) {
		if(e1.getWeight() == e2.getWeight()) {
			return 0;
		}else if(e1.getWeight() < e2.getWeight()){
			return -1;
		}
		return 1;
	}
}