package AvailabilityDemand;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BnBProvider implements iPublisher {

	private String providerName;

	private List<Room> rooms;

	// private String providerName;

	// private List<Room> roomList;

	// private Room[] room;

	// private Room[] room;

	// private Broker broker;

	public BnBProvider(String providerName) {
		this.providerName =  providerName;
		this.rooms = new ArrayList<Room>();
	}

	public boolean publish(String providerName, String location, Date availableFrom, Date availableDate) 
	{
		Calendar cal = new GregorianCalendar(2021, Calendar.NOVEMBER, 27);
		Date d1 = cal.getTime();

		//check if its before epoch (11/27/2021)
		if(availableFrom.before(d1) || availableDate.before(d1))
		{
			return false;
		}
		//so date can't be before 11/27


		if(availableFrom.after(availableDate) || availableDate.equals(availableFrom))
		{
			return false;
		}
		
		if(rooms.isEmpty())
		{
			rooms.add(new Room(location, availableFrom, availableDate));
			return true;
		}
		//assumption : provider cannot have multiple overlapping stays at same location, 
		// but can 
		// boolean locationSame = false;

		for(Room r : this.rooms)
		{
			if(r.getLocation().equals(location)) //same location
			{
				// locationSame = true;
				StayPeriod period = r.getStay();
				if(period.getFrom().equals(availableFrom) || period.getTo().equals(availableDate)) //same dates
				{
					return false;
				}
				boolean fromValid = (!availableFrom.before(period.getFrom()) && !availableFrom.after(period.getTo()));
				boolean toValid = (!availableDate.before(period.getFrom()) && !availableDate.after(period.getTo()));
				if(fromValid || toValid)
				{
					return false;
				}
			}
		}
		rooms.add(new Room(location, availableFrom, availableDate));
		return true;
	}

	/**
	 * @deprecated
	 * @param room
	 * @return true of false if room can be created
	 */
	public boolean addRoom(Room room) 
	{
		return false;
	}

	/**
	 * 
	 * @return the name of the b&b
	 */
	public String getProvider() {
		//return null;
		return this.providerName;
	}

	/**
	 * 
	 * @return the list of rooms
	 */
	public List<Room> getRooms() {
		//return null;
		return this.rooms;
	}

}
