import java.net.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread{

	private DatagramSocket socket;
	private Game game;
	private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

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

			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port){
		String message = new String(data).trim();
		Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));

		switch(type){

		default:
			break;
		case INVALID:
			break;
		case LOGIN:
			Packet00Login packet = new Packet00Login(data);
			System.out.println("Player connected: " + packet.getUsername() + " @ " + address.getHostAddress() + ":" + port);
			PlayerMP player = null;
			if(address.getHostAddress().equalsIgnoreCase("127.0.0.1"))
				player = new PlayerMP(game.level, 100, 100, game.input, packet.getUsername(), address, port);
			else
				player = new PlayerMP(game.level, 100, 100, packet.getUsername(), address, port);

			if(player != null){
				this.connectedPlayers.add(player);
				game.level.addEntity(player);
				game.player = player;
			}

			break;
		case DISCONNECT:
			break;
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

	public void sendDataToAllClients(byte[] data){
		for (PlayerMP p : connectedPlayers){
			sendData(data, p.ipAddress, p.port);
		}
	}
}