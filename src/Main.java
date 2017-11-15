import java.net.DatagramSocket;

public class Main {
	
	// TODO Auto-generated method stub
	public static void main(String[] args){
		try{
		System.out.print("bonjour");
		DatagramSocket socket = new DatagramSocket(4242);
		
		PeerTable table = new PeerTable();
		
		SimpleMessageHandler[] handlers = new SimpleMessageHandler[3];
		for(int i = 0; i < 3; i++){
			handlers[i]= new HelloHandler(table);
		}
		
		MuxDemuxSimple dm = new MuxDemuxSimple(handlers, socket, table);
		
		new Thread(dm).start();		
		System.out.print("bonjour2");
	} catch (Exception e){
		e.printStackTrace();
	}

}
}
