import java.util.concurrent.SynchronousQueue;
import java.net.*;
import java.io.*;

public class MuxDemuxSimple implements Runnable{
	
	/*Possibilité de faire une implémentation symétrique entre thread qui reçoivent ou qui envoient un message*/

    private DatagramSocket mySocket = null;
    private BufferedReader in;
    private SimpleMessageHandler[] myMessageHandlers;
    private SynchronousQueue<String> outgoing = new SynchronousQueue<String>();
    private String id = null ;
    
    MuxDemuxSimple (SimpleMessageHandler[] h, DatagramSocket s){
        mySocket = s;
        id = "para";
        try{
        	mySocket.setBroadcast(true);
        } catch (Exception e){
        	e.printStackTrace();
        }
        myMessageHandlers = h;
    }
    
    public void run(){
        for (int i=0; i<myMessageHandlers.length; i++){
            myMessageHandlers[i].setMuxDemux(this);
        }
        try{
        	while(true){
        		byte[] buf = new byte[8192];
        		DatagramPacket messageReçu = new DatagramPacket(buf, 8192);
        		mySocket.receive(messageReçu);
        		String message = new String(messageReçu.getData());
        		for (int i=0; i<myMessageHandlers.length; i++){
                    myMessageHandlers[i].handleMessage(message);
                }
        	}
        }catch (IOException e){ 
        	e.printStackTrace();
        }		
        try{
            in.close(); mySocket.close();
        }catch(IOException e){ 
        	e.printStackTrace();
        }
    }
	
    public void send(String s){
    	outgoing.add(s);
    }
    
    public class throwMessage implements Runnable{
    	
    	
    	public void run(){
    		try{
    			while(true){
    				String message = outgoing.remove();
    				DatagramPacket messageToSend = new DatagramPacket(message.getBytes(), message.getBytes().length);
    				mySocket.send(messageToSend);
    				this.wait(10000);
    			}
    		} catch (Exception e){
    			e.printStackTrace();
    		}
    	}
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
		try{
			DatagramSocket s = new DatagramSocket(4242);
			
			HelloHandler[] handlers = new HelloHandler[1];
			handlers[0]= new HelloHandler();
			
			MuxDemuxSimple dm = new MuxDemuxSimple(handlers, s);
			throwMessage tm = dm.new throwMessage();
			
			new Thread(handlers[0]).start();
			new Thread(dm).start();
			new Thread(tm).start();
			
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
