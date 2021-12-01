package AvailabilityDemand;
import java.util.Date;

public class Room {

	private String location;

	private StayPeriod stay;

	// private String location;

	// private StayPeriod stay;

	// private StayPeriod stayPeriod;

	// private StayPeriod stayPeriod;

	/**
	 * 
	 * @param location of the room
	 * @param from Date that the room is available from
	 * @param to Date that room is available until
	 */
	public Room(String location, Date from, Date to) {
		this.location = location;
		this.stay = new StayPeriod(from, to);
	}

	/***
	 * Gets the location of the Room
	 * @return a string that represents the location
	 */
	public String getLocation() {
		//return null;
		return this.location;
	}

	/**
	 * Gets the stay period of the room
	 * @return StayPeriod object that contains to and from date
	 */
	public StayPeriod getStay() {
		// return null;
		return stay;
	}

}
