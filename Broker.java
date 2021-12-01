package AvailabilityDemand;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;

/***
 * @version 1.0 of Broker
 */
public class Broker {

	public List<BnBProvider> providers; // List of all providers (B&Bs)

	public List<Customer> subscribers; //List of all subscribers (Customers)

	public List<String> messages; //List of all messages to be sent to subsribers

	public HashSet<String> messageSet; //HashSet to make sure no duplicate messages are sent

	/***
	 * @return The broker object which initializes all lists
	 */
	public Broker() 
	{
		this.providers = new ArrayList<BnBProvider>();
		this.subscribers = new ArrayList<Customer>();
		this.messages = new ArrayList<String>();
		this.messageSet = new HashSet<String>();
	}
	/***
	 * 
	 * @param b the b&b that we are getting the dates and name from
	 * @param r the room that is being subscribed to
	 * @param c the customer that is requesting the availability
	 * @param split the string array of inputs from processInput
	 * @return a string that represents what message is to be sent
	 */
	public String createMessage(BnBProvider b, Room r, Customer c, String[] split) //check in astah
	{
		Date from = r.getStay().getFrom();
		Date to = r.getStay().getTo();
		String s1 ="";
		String s2 = "";
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try
		{
			s1 = df.format(from);
			s2 = df.format(to);
		}
		catch(Exception e)
		{
			return "";
		}
		StringBuilder str = new StringBuilder();
		str.append(c.getCustomer());
		str.append(" notified of B&B availability in ");
		str.append(r.getLocation());
		str.append(" from ");
		str.append(s1);
		str.append(" to ");
		str.append(s2);
		str.append(" by ");
		str.append(b.getProvider());
		str.append(" B&B");

		return str.toString();
	}

	public boolean subscribeToProvider(Room r, Customer c)
	{
		for(Subscription s : c.getSubscriptions())
		{
			if(r.getLocation().equals(s.getLocation()))
			{
				boolean fromBetween  = (!s.getFrom().before(r.getStay().getFrom()) && !s.getFrom().after(r.getStay().getTo()));
				boolean toBetween = (!s.getTo().before(r.getStay().getFrom()) && !s.getTo().after(r.getStay().getTo()));

				if(fromBetween && toBetween)
				{
					return true;
				}
			}
		}
		return false;
	}

	public List<String> exportOutput() 
	{
		return this.messages;
	}

}
