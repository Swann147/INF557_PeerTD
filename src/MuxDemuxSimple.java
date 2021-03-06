import java.util.concurrent.SynchronousQueue;
import java.net.*;
import java.io.*;

public class MuxDemuxSimple implements Runnable{
	
	/*Possibilité de faire une implémentation symétrique entre thread qui reçoivent ou qui envoient un message*/

    private DatagramSocket mySocket = null;
    private BufferedReader in;
    private SimpleMessageHandler[] myMessageHandlers;
    //private SynchronousQueue<String> outgoing = new SynchronousQueue<String>();
    private String id = null ;
    private PeerTable myPeerTable = new PeerTable();
    
    MuxDemuxSimple (SimpleMessageHandler[] h, DatagramSocket s, PeerTable pt){
        mySocket = s;
        id = "para";
        myPeerTable = pt;
        pt.addPeer(id, s.getLocalSocketAddress().toString(), "10");
        try {
			s.setBroadcast(true);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try{
        	mySocket.setBroadcast(true);
        } catch (Exception e){
        	e.printStackTrace();
        }
        myMessageHandlers = h;
    }
    
    public void run(){
        for (int i=0; i<myMessageHandlers.length; i++){
            myMessageHandlers[i].setMuxDemux(this);
        }
        try{
        	while(true){
        		byte[] buf = new byte[8192];
        		DatagramPacket receivedMsg = new DatagramPacket(buf, 8192);
        		mySocket.receive(receivedMsg);
        		String message = new String(receivedMsg.getData());
        		for (int i=0; i<myMessageHandlers.length; i++){
                    myMessageHandlers[i].handleMessage(message, receivedMsg.getAddress().toString());
                }
        	}
        }catch (IOException e){ 
        	e.printStackTrace();
        }		
        try{
            in.close(); mySocket.close();
        }catch(IOException e){ 
        	e.printStackTrace();
        }
    }
	
	public void send(String s) {
		try {
			byte[] buf = s.getBytes();
			DatagramPacket sendMsg = new DatagramPacket(buf, buf.length, InetAddress.getByName("255.255.255.255"), mySocket.getLocalPort());
			mySocket.send(sendMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public String getID() {
    	return id;
    }
    
    public PeerTable getPeerTable() {
    	return myPeerTable ;
    }
    
    public static void main(String[] args){
    	
    }

}
