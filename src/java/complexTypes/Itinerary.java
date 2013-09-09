package complexTypes;


public class Itinerary {
	
	private String  timeToGo;
	private int     seat;
	private String price;
	private String  service;
	public String getTimeToGo() {
		return timeToGo;
	}
		
	
	public void setTimeToGo(String timeToGo) {
		this.timeToGo = timeToGo;
	}
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	
	

}
