import java.net.*;
import java.io.IOException;

public class GameServer extends Thread{

	private DatagramSocket socket;
	private Game game;

	public GameServer(Game game){
		this.game = game;
		try{
			this.socket = new DatagramSocket(1331);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try{
				socket.receive(packet);
			}
			catch(IOException e){
				e.printStackTrace();
			}
			String message = new String(packet.getData());
			if(message.trim().equalsIgnoreCase("ping")){
				System.out.println("Server said: " + new String(packet.getData()));
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try{
			socket.send(packet);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}