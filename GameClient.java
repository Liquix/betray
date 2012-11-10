import java.net.*;
import java.io.IOException;

public class GameClient extends Thread{
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Game game;

	public GameClient(Game game, String ipAddress){
		this.game = game;
		try{
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
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

			System.out.println("Client said: " + new String(packet.getData()));

		}
	}

	public void sendData(byte[] data){
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try{
			socket.send(packet);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}