import java.net.DatagramSocket;

public class Main {
	
	// TODO Auto-generated method stub
	public static void main(String[] args){
		try{
		DatagramSocket socket = new DatagramSocket(4242);
		
		SimpleMessageHandler[] handlers = new SimpleMessageHandler[3];
		for(int i = 0; i < 3; i++){
			handlers[i]= new HelloHandler();
		}
		
		PeerTable table = new PeerTable();
		
		MuxDemuxSimple dm = new MuxDemuxSimple(handlers, socket, table);
		
		new Thread(dm).start();		
		
	} catch (Exception e){
		e.printStackTrace();
	}

}
}
