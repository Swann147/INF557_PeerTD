import java.util.concurrent.SynchronousQueue;

public class HelloHandler implements SimpleMessageHandler, Runnable {

	private SynchronousQueue<String> incoming = new SynchronousQueue<String>();
	private MuxDemuxSimple myMuxDemux = null;
	private String senderIPAddress = null;
	
    public void setMuxDemux(MuxDemuxSimple md){
        myMuxDemux = md;
    }
    
    public void handleMessage(String m) {
    	try {
    		incoming.put(m);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
    public void handleMessage(String m, String IPAddress){
        try {
        	incoming.put(m);
        	senderIPAddress = IPAddress;
        	//System.out.println(m);
        	try {
        		HelloMessage hello = new HelloMessage(m);
        		if (myMuxDemux.myPeerTable.hasPeer(hello.getSenderID)) {
        			if (!myMuxDemux.myPeerTable.readSeqNumber(hello.getSenderID()).equals(hello.getSequenceNumber())) {
        				myMuxDemux.myPeerTable.setStateToInconsistent(hello.getSenderID());
        			}
        		}
        		else {
        			myMuxDemux.myPeerTable.addPeer(hello.getSenderID(),senderIPAddress,hello.getHelloInterval());
        		}
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
    public void run(){
    	while(true){
    		try{
    			String msg = incoming.take();
    			myMuxDemux.send(msg);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }
}
