import java.util.concurrent.SynchronousQueue;


public class DebugReceiver implements SimpleMessageHandler, Runnable{

	private SynchronousQueue<String> incoming = new SynchronousQueue<String>();
	private MuxDemuxSimple myMuxDemux = null;
	private PeerTable pt;
	
	public DebugReceiver(PeerTable pt){
		this.pt = pt;
		new Thread(this).start();
	}
	
    public void setMuxDemux(MuxDemuxSimple md){
        myMuxDemux = md;
    }
    
    public void handleMessage(String m, String IPAddress){
        try {
        	System.out.println("DebugReceiver : " + m);
        	incoming.put(m);
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
    public void run(){
    	while(true){
    		try{
    		String raw = incoming.take();
    		String[] part = raw.split(";");
    		System.out.println("DebugReceiver detail : ");
    		for(int i = 0; i < part.length; i++){
    			System.out.println(part[i]);
    		}
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }
}
