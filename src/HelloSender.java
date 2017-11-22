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
    			HelloMessage hello = new HelloMessage(id,Integer.parseInt(pt.readSeqNum(id)),10,pt.getNbPeers(),pt.getAllPeersName());
    			System.out.println(pt.readToString(id));
    			myMuxDemux.send(hello.getHelloMessageAsEncodedString());
    			Thread.sleep(10000);
    		} catch (Exception e){
    		}
    	}
    }

}
