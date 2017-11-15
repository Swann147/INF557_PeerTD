import java.util.concurrent.SynchronousQueue;

public class SenderHandler implements SimpleMessageHandler, Runnable{

	private SynchronousQueue<String> incoming = new SynchronousQueue<String>();
	private MuxDemuxSimple myMuxDemux = null;
	
	public SenderHandler() {
		new Thread(this).start();
	}
    
    public void handleMessage(String m, String IPAddress){
        try {
        	incoming.put(m);
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
    public void setMuxDemux(MuxDemuxSimple md) {
    	myMuxDemux = md;
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
