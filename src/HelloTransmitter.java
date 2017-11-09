import java.util.concurrent.SynchronousQueue;

public class HelloTransmitter implements SimpleMessageHandler, Runnable {

	private String messageToSend;
	private MuxDemuxSimple myMuxDemux = null;
	
    public void setMuxDemux(MuxDemuxSimple md){
        myMuxDemux = md;
    }
    
    public void handleMessage(String m){
        try {
        	messageToSend = m;
        } catch (Exception e){
        }
    }
	
	public void run(){
		
	}
		/**
	}
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
