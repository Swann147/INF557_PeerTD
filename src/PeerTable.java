import java.util.*;
import java.time.Clock;

<<<<<<< HEAD
=======

>>>>>>> a50fd51143e4301f49a5818fa7ec41db39959226
public class PeerTable {
	
	Vector<Vector<String>> table;
	
	//Constructor
	PeerTable(){
		table = new Vector<Vector<String>>();
	}
	
	//Check if this peer has already been seen
	public boolean hasPeer(String peerID){
		for(int i = 0; i < table.size(); i++){
			if(table.get(i).get(0).equals(peerID)){
				return true;
			}
		}
		return false;
	}
	
	//Return the vector with all the information about this peer if found, else an exception.
	public Vector<String> getPeer(String peerID) throws Exception{
		int indicePeer = 0;
		boolean found = false;
		for(int i = 0; i < table.size(); i++){
			if(table.get(i).get(0).equals(peerID)){
				indicePeer = i;
				found = true;
			}
		}
		if(!found){
			throw new Exception(peerID + "unknown");
		} else {
			return table.get(indicePeer);
		}
	}
	
	//Add a new peer to the table. The verification that the peer was unknown has already been done
	public void addPeer(String peerID, String peerIP, String HelloInterval){
		Vector<String> peer = new Vector<String>();
		peer.add(peerID);
		peer.add(peerIP);
		peer.add("-1");
		peer.add(Integer.toString((int)(long)System.currentTimeMillis() + Integer.parseInt(HelloInterval) * 1000));
		peer.add("heard");
		table.add(peer);
	}
	
	//Set the state of a known peer to one of the 3 possibilities
	public void setStateToDying(String peerID){
		try{
			Vector<String> peer = this.getPeer(peerID);
			peer.set(4, "Dying");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void setStateToInconsistent(String peerID){
		try{
			Vector<String> peer = this.getPeer(peerID);
			peer.set(4, "Dying");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void setStateToSynchronised(String peerID){
		try{
			Vector<String> peer = this.getPeer(peerID);
			peer.set(4, "Dying");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//Return as String the various information of the peer
	public String readToString(String peerID) throws Exception{
		try{
			Vector<String> peer = this.getPeer(peerID);
			String allInfo = peer.get(0);
			for(int indiceInfo = 1; indiceInfo < peer.size(); indiceInfo++){
				allInfo += ";" + peer.get(indiceInfo);
			}
			return allInfo;
		} catch(Exception e){
			throw e;
		}
	}
	
	public String readIP(String peerID) throws Exception{
		try{
			Vector<String> peer = this.getPeer(peerID);
			return peer.get(1);
		} catch(Exception e){
			throw e;
		}
	}
	
	public String readSeqNum(String peerID) throws Exception{
		try{
			Vector<String> peer = this.getPeer(peerID);
			return peer.get(2);
		} catch(Exception e){
			throw e;
		}
	}
	
	public String readState(String peerID) throws Exception{
		try{
			Vector<String> peer = this.getPeer(peerID);
			return peer.get(3);
		} catch(Exception e){
			throw e;
		}
	}
	
}
