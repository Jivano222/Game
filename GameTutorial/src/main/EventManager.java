package main;

public class EventManager {
	
	public int set1 = 0;
	public boolean event1Active = false;
	

	public void checkEvent() {
		if(set1 == 4) {
			event1Active = true;
		}
	}
}
