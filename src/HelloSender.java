import java.util.concurrent.SynchronousQueue;

public class HelloSender implements SimpleMessageHandler, Runnable{

	private MuxDemuxSimple myMuxDemux = null;
	private HelloMessage hello;
	public PeerTable pt;
	
	public HelloSender(PeerTable pt) {
		this.pt = pt;
		new Thread(this).start();
	}
    
    public void handleMessage(String m, String IPAddress) { }
    
    public void setMuxDemux(MuxDemuxSimple md) {
    	myMuxDemux = md;
    }
    
    public void run(){
    	while(true){
    		try{
    			String id = myMuxDemux.getID();
    			HelloMessage hello = new HelloMessage(id,Integer.parseInt(pt.readSeqNum(id)),10,pt.getPeer(id).size(),pt.getPeer(id));
    			myMuxDemux.send(hello.getHelloMessageAsEncodedString());
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }

}
