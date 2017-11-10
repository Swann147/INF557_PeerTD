import java.util.concurrent.SynchronousQueue;

public class HelloSender implements SimpleMessageHandler{

	private SynchronousQueue<String> incoming = new SynchronousQueue<String>();
	private MuxDemuxSimple myMuxDemux = null;
	private HelloMessage hello;
    
    public void handleMessage(String m){
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
    			hello = new HelloMessage(myMuxDemux.getID(), 42, 10);
    			myMuxDemux.send(hello.getHelloMessageAsEncodedString());
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }

}
