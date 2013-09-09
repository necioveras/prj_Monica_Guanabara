package complexTypes;

import java.util.LinkedList;
import java.util.List;

public class ItineraryList {
	
	private List<Itinerary> list = new LinkedList<Itinerary>();;	
	
	
	public void add(Itinerary i){
		list.add(i);	
	}
	
	public Itinerary getItinerary(int index){
		return list.get(index);
	}

}
