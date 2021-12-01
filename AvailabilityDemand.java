package AvailabilityDemand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

/***
 * 
 * @class AvailabilityDemand
 * This class is the main runner class through which the inputs are deciphered
 * 
 */
public class AvailabilityDemand {

	private Broker broker;

	public AvailabilityDemand()
	{
		broker = new Broker();
	}

	/***
	 * 
	 * @param command input from the user
	 */
	public void processInput(String command)
	{
		String[] split = command.toLowerCase().trim().split(", ");
		for(String s : split)
			s = s.trim();
		
		if(split.length!=5)
		{
			return;
		}

		try {
			new SimpleDateFormat("MM/dd/yyyy").parse(split[3]);
			new SimpleDateFormat("MM/dd/yyyy").parse(split[4]);
		}
		catch(ParseException pe)
		{
			return;
		}
		if(split[0].equals("subscribe"))
		{
			// boolean check = false;
			Customer c = new Customer(split[1]);
			Date d1=new Date();
			Date d2 = new Date();
			try{
				d1 = new SimpleDateFormat("MM/dd/yyyy").parse(split[3]);
				d2 = new SimpleDateFormat("MM/dd/yyyy").parse(split[4]);
			}
			catch(ParseException e)
			{
				return;
			}
			
			boolean customerAdded=false;
			
			int j=0;
			for(Customer customer: broker.subscribers)
			{
				if(customer.getCustomer().equals(split[1]))
				{
					if(customer.subscribe(split[2], d1, d2))
					{
						customerAdded=true;
						j = broker.subscribers.indexOf(customer);
						break;
					}
				}
			}

			if(customerAdded ==false)
			{
				if(c.subscribe(split[2], d1, d2))
				{
					broker.subscribers.add(c);
					j = broker.subscribers.indexOf(c);
				}
			}

			Customer tempCustomer = broker.subscribers.get(j);

			for(BnBProvider bnb : broker.providers)
			{
				for(Room room : bnb.getRooms())
				{
					if(broker.subscribeToProvider(room, tempCustomer)) {
						String message = broker.createMessage(bnb, room, tempCustomer, split);
						if(!broker.messageSet.contains(message))
						{
							broker.messageSet.add(message);
							broker.messages.add(message);
						}
					}
				}
			}
		}

		else if(split[0].equals("unsubscribe"))
		{
			Date d1=new Date();
			Date d2 = new Date();
			try{
				d1 = new SimpleDateFormat("MM/dd/yyyy").parse(split[3]);
				d2 = new SimpleDateFormat("MM/dd/yyyy").parse(split[4]);
			}
			catch(ParseException e)
			{
				return;
			}

			for(Customer c : broker.subscribers)
			{
				if(c.getCustomer().equals(split[1]))
				{
					//unsubscribe will take it out of the customer's list of subscriptions
					if(c.unSubscribe(split[2], d1, d2))
					{
						if(c.getSubscriptions().isEmpty())
							broker.subscribers.remove(c);
						break;
					}
				}
			}
		}

		else if(split[0].equals("publish"))
		{
			boolean ifContains=false, published=false;
			BnBProvider p = new BnBProvider(split[1]);
			Date d1=new Date();
			Date d2 = new Date();
			try{
				d1 = new SimpleDateFormat("MM/dd/yyyy").parse(split[3]);
				d2 = new SimpleDateFormat("MM/dd/yyyy").parse(split[4]);
			}
			catch(ParseException e)
			{
				return;
			}
			if(broker.providers.isEmpty())
			{
				if(p.publish(split[1], split[2], d1, d2))
				{
					broker.providers.add(p);
				}
			}
			int i=0;
			BnBProvider tempBProvider;

			for(BnBProvider provider : broker.providers)
			{
				if(provider.getProvider().equals(split[1]))
				{
					ifContains = true; //check if bnb exists
					if(provider.publish(split[1], split[2], d1, d2))
					{
						published =true;
						i = broker.providers.indexOf(provider);
						break;
					}
				}
			}
			//if we didnt find the bnb in the brokers, and we havent published anything 
			if(p.publish(split[1], split[2], d1, d2) && published ==false && ifContains==false && i==0)
			{
				broker.providers.add(p);
				i = broker.providers.indexOf(p);
			}	
			if(broker.providers.size()==0)
			{
				return;
			}
			tempBProvider = broker.providers.get(i);

			for(Customer c : broker.subscribers)
			{
				for(Room r: tempBProvider.getRooms())
				{
					if(broker.subscribeToProvider(r, c))
					{
						String message = broker.createMessage(tempBProvider,r,c,split);
						if(!broker.messageSet.contains(message))
						{
							broker.messageSet.add(message);
							broker.messages.add(message);
						}
					}
				}
			}
		}
	}

	/***
	 * 
	 * @return list of the full output
	 */
	public List<String> getAggregatedOutput() {
		return broker.exportOutput();
		//return null;
	}

	//resets all the lists
	public void reset() {
		broker.subscribers.clear();
		broker.messages.clear();
		broker.providers.clear();
		broker.messageSet.clear();
	}
}
