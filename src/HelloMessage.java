/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author Swann
 */
public class HelloMessage {
	String senderID;
	int sequenceNumber;
	int helloInterval;
	int numPeers;
	Vector<String> peers;

	static public String HELLO = "HELLO"; 


	/*
	*	Constructor from a fromatted string
	*	Initializarion with given peers
	*/
	public HelloMessage(String s) throws Exception
	{
		String slist[] = s.split(";");

		if(slist.length < 5){
			throw new Exception("Missing arguments in the hello string");
		}

		Pattern helloPattern = Pattern.compile("(h|H)(e|E)(l|L){2}(o|O)");
		Matcher helloMatcher = helloPattern.matcher(slist[0]);
		
		if(!helloMatcher.matches())
		{
			throw new Exception("Not a Hello message");
		}

		senderID = slist[1];
		sequenceNumber = Integer.parseInt(slist[2]);
		helloInterval = Integer.parseInt(slist[3]);
		numPeers = Integer.parseInt(slist[4]);

		if(numPeers != (slist.length - 5))
		{
			throw new Exception("Wrong number of peer given...");
		}

		peers = new Vector<String>();

		for(int i = 5; i < (5 + numPeers); i++)
		{
			peers.add(slist[i]);
		}
	}


	/*
	*	Constructor from info
	*	Initialization with no peers
	*/
	public HelloMessage(String senderIdIn, int sequenceNo, int helloIntervalIn)
	{
		senderID = senderIdIn;
		sequenceNumber = sequenceNo;
		helloInterval = helloIntervalIn;
		numPeers = 0;
		peers = new Vector<String>();		
	}
	
	public HelloMessage(String senderIdIn, int sequenceNo, int helloIntervalIn, int numPeerIn, Vector<String> peersIn) {
		senderID = senderIdIn;
		sequenceNumber = sequenceNo;
		helloInterval = helloIntervalIn;
		numPeers = numPeerIn;
		peers = peersIn;
	}


	public String getHelloMessageAsEncodedString() throws Exception
	{
		String result = HELLO;
		result += ";";
		result += senderID;
		result += ";";
		result += sequenceNumber;
		result += ";";
		result += helloInterval;
		result += ";";
		result += numPeers;

		if(numPeers != peers.size())
		{
			throw new Exception("numPeers isn't equal to peers.size().");
		}

		for(int i = 0; i < numPeers; i++)
		{
			result += ";";
			result += peers.elementAt(i);
		}

		return result;
	}
	

	public void addPeer(String peerID) throws Exception 
	{
		if (numPeers++ > 255)
		{
			throw new Exception("Cannot add another peer : maximal number of peers reached"); 
		}
		peers.add(peerID);
	}
	
	public String toString()
	{
		String result = "The sender is " + senderID + "\n";
		result += "Senquence number is " + sequenceNumber; 
		result += " and HelloInterval is " + helloInterval + "\n";
		
		if(numPeers != peers.size())
		{
			System.out.println("numPeers isn't equal to peers.size().");
		}

		if(numPeers > 0)
		{
			result += "The " + numPeers + " peers are :\n";
			result += peers.elementAt(0);
			for(int i = 1; i < peers.size(); i++)
			{
				result += ", " + peers.elementAt(i);
			}
			result+= ".\n";
		}
		else
		{
			result += "There are no peers\n";
		}
		return result;
	}
	
	public String getSenderID() 
	{
		//necessary to identify the peer who sent the message in the peerTable when receiving
		return senderID ;
	}
	
	public int getSequenceNumber() 
	{
		//necessary to check the consistency of the message
		return sequenceNumber ;
	}
	
	public int getHelloInterval() 
	{
		//necessary for knowing at which frequency the messages are to be sent
		return helloInterval ;
	}
}
