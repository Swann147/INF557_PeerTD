import java.util.concurrent.SynchronousQueue;

public class HelloHandler implements SimpleMessageHandler, Runnable {

	private SynchronousQueue<String> incoming = new SynchronousQueue<String>();
	private MuxDemuxSimple myMuxDemux = null;
	private String senderIPAddress = null;
	private PeerTable pt;
	
	public HelloHandler(PeerTable pt) {
		this.pt = pt;
		new Thread(this).start();
	}
	
    public void setMuxDemux(MuxDemuxSimple md){
        myMuxDemux = md;
    }

    public void handleMessage(String m, String IPAddress){
        try {
        	incoming.put(m);
        	senderIPAddress = IPAddress;
        	//System.out.println(m);
        	try {
        		HelloMessage hello = new HelloMessage(m);
        		if (myMuxDemux.getPeerTable().hasPeer(hello.getSenderID())) {
        			if (!myMuxDemux.getPeerTable().readSeqNum(hello.getSenderID()).equals(hello.getSequenceNumber())) {
        				myMuxDemux.getPeerTable().setStateToInconsistent(hello.getSenderID());
        			}
        		}
        		else {
        			myMuxDemux.getPeerTable().addPeer(hello.getSenderID(),senderIPAddress,Integer.toString(hello.getHelloInterval()));
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
    			HelloMessage hello = new HelloMessage(msg); 
    			HelloSender myHelloSender = new HelloSender(pt);
    			myHelloSender.handleMessage(msg, null);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }
}
