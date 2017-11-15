import java.util.concurrent.SynchronousQueue;

public class HelloSender implements SimpleMessageHandler{

	private SynchronousQueue<String> incoming = new SynchronousQueue<String>();
	private MuxDemuxSimple myMuxDemux = null;
	private HelloMessage hello;
    
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
    			hello = new HelloMessage(msg);
    			myMuxDemux.send(hello.getHelloMessageAsEncodedString());
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }

}
