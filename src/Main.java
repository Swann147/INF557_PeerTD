import java.net.DatagramSocket;

public class Main {
	
	// TODO Auto-generated method stub
	public static void main(String[] args){
		try{
		
		DatagramSocket socket = new DatagramSocket(4242);
		
		PeerTable table = new PeerTable();
		
		SimpleMessageHandler[] handlers = new SimpleMessageHandler[4];
		
		MuxDemuxSimple dm = new MuxDemuxSimple(handlers, socket, table);
		
		handlers[0] = new HelloHandler(table);
		handlers[1] = new HelloReceiver(table);
		handlers[2] = new HelloSender(table);
		handlers[3] = new DebugReceiver(table);
		
		new Thread(dm).start();
		
		//System.out.println("bonjour2");
		
		
	} catch (Exception e){
		e.printStackTrace();
	}

}
}
