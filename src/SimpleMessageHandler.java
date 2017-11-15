
public interface SimpleMessageHandler {
	
	public void handleMessage(String m, String IPAddress);
	public void setMuxDemux(MuxDemuxSimple md);
	public void run();
	
}
