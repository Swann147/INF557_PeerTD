import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class PeerTable {
	
	private Vector<Vector<String>> table;
	private int nbPeersToTalk; //keep the number of peers in a good state to talk
	private final ReentrantLock lock = new ReentrantLock();
	
	//Constructor
	PeerTable(){
		table = new Vector<Vector<String>>();
		nbPeersToTalk = 0;
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
	
	//Get functions
	
	//Return the vector with all the information about this peer if found, else an exception.
	public Vector<String> getPeer(String peerID) throws Exception{
		lock.lock();
		int indicePeer = 0;
		boolean found = false;
		for(int i = 0; i < table.size(); i++){
			if(table.get(i).get(0).equals(peerID)){
				indicePeer = i;
				found = true;
			}
		}
		if(!found){
			lock.unlock();
			throw new Exception(peerID + "unknown");
		} else {
			lock.unlock();
			return table.get(indicePeer);
		}
	}
	
	public int getNbPeers() {
		return nbPeersToTalk;
	}
	
	public Vector<String> getAllPeersName(){
		lock.lock();
		Vector<String> names = new Vector<String>();
		for(int i = 0; i < table.size(); i++) {
			names.add(table.get(i).get(0));
		}
		lock.unlock();
		return names;
	}
	
	
	//Add Functions
	
	//Add a new peer to the table. The verification that the peer was unknown has already been done
	public void addPeer(String peerID, String peerIP, String HelloInterval){
		lock.lock();
		Vector<String> peer = new Vector<String>();
		peer.add(peerID);
		peer.add(peerIP);
		peer.add("-1");
		peer.add(Integer.toString((int)(long)System.currentTimeMillis() + Integer.parseInt(HelloInterval) * 1000));
		peer.add("heard");
		table.add(peer);
		nbPeersToTalk ++;
		lock.unlock();
	}
	
	
	//Set functions
	
	//Set the state of a known peer to one of the 3 possibilities
	public void setStateToDying(String peerID){
		lock.lock();
		try{
			Vector<String> peer = this.getPeer(peerID);
			peer.set(4, "Dying");
			nbPeersToTalk --;
		} catch (Exception e){
			e.printStackTrace();
		}
		lock.unlock();
	}
	
	public void setStateToInconsistent(String peerID){
		lock.lock();
		try{
			Vector<String> peer = this.getPeer(peerID);
			peer.set(4, "Inconsistent");
			nbPeersToTalk --;
		} catch (Exception e){
			e.printStackTrace();
		}
		lock.unlock();
	}
	
	public void setStateToSynchronised(String peerID){
		lock.lock();
		try{
			Vector<String> peer = this.getPeer(peerID);
			peer.set(4, "Synchronised");
		} catch (Exception e){
			e.printStackTrace();
		}
		lock.unlock();
	}
	
	
	//Read functions
	
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
	
	
	//Taking care of the TimeOut functions
	
	//Deletes a peer
	public void deletePeer(int i) throws Exception{
		try {
			table.remove(i);
			nbPeersToTalk--;
		} catch(Exception e){
			throw e;
		}
	}
	
	//Goes through the whole table and if the peer is out dated, deletes it
	// out dated means : time stamp < now
	public Vector<String> cleanTable() {
		lock.lock();
		for(int i = 0 ; i < table.size() ; i++) {
			if (Integer.parseInt(table.get(i).get(3)) < (int)(long)System.currentTimeMillis()*1000) {
				try {
					this.deletePeer(i);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		Vector<String> finalTable = this.getAllPeersName();
		lock.unlock();
		return finalTable;
	}
	
	
}
