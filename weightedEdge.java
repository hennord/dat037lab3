

public class weightedEdge{
	
	private int weight;
	private String end;
	private String start;
	
	public weightedEdge(String start, String end, int weight) {
		this.weight = weight;
		this.end = end;
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public String getStart() {
		return start;
	}
	
	public String toString() {
		return start + " " + end + " " + weight;
	}
	
	public int getWeight() {
		return weight;
	}
	
	//Combines two edges and returns the combined edge, the added edge e has to have the same start location
	//as the end location of this edge. 
	public weightedEdge combine(weightedEdge e) {
		/*if(!e.getStart().equals(end)) {
			throw new IllegalArgumentException();
		}*/
		return new weightedEdge(start,end,weight+e.getWeight());
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof weightedEdge)) {
			return false;
		}
		
		weightedEdge e = (weightedEdge) o;
		if(this.end.equals(e.getEnd())) {
			return true;
		}
		return false;
	}
	
	//Used by HashSet to see if graph already has this edge.
	//Only checks the end destination, cannot update already existing edges with new
	//weights using addEdge().
	@Override
    public int hashCode() {
        return 3*start.hashCode() + 5*end.hashCode();
    }
}