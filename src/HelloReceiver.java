import java.util.concurrent.SynchronousQueue;

public class HelloReceiver implements SimpleMessageHandler, Runnable{
	
	private SynchronousQueue<String> incoming = new SynchronousQueue<String>();
	private MuxDemuxSimple myMuxDemux = null;
	private HelloMessage hello;
	private PeerTable pt;
	
	public HelloReceiver() {
		this.pt = pt;
		new Thread(this).start();
	}
    
    public void handleMessage(String m, String IPAddress){
        try {
        	incoming.put(m);
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
    // pour cette classe, cette méthode est inutile mais on est obligé de la laissé car elle est forcément appelée
    // dans MuxDemuxSimple sur l'ensemble de l'interface
    public void setMuxDemux(MuxDemuxSimple md) {
    	myMuxDemux = md;
    }
    
    public void run(){
    	while(true){
    		try{
    			String msg = incoming.take();
    			hello = new HelloMessage(msg);
    			System.out.println(msg);
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }

}
