import java.net.*;
import java.io.*;
import java.util.Arrays;

public class UDPServer 
{

	public static void main(String[] args) 
	{
		
		try 
		{
			DatagramSocket serverSocket = new DatagramSocket(10999); //creates a datagram socket and binds it to port 10999
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			
			while (true) 
			{
			
				System.out.println("UDP Server waiting for client on port " + serverSocket.getLocalPort() + "...");
				
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					
				serverSocket.receive(receivePacket);
				
				InetAddress IPAddress = receivePacket.getAddress();
				
				int port = receivePacket.getPort();
				
				String receivedSentence = new String(receivePacket.getData());
							
				System.out.println("RECEIVED: from IPAddress " + 
				IPAddress + " and from port " + port + " the data: " + receivedSentence);
				
				sendData = receivedSentence.toUpperCase().getBytes();
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				
				serverSocket.send(sendPacket);

				Arrays.fill(receiveData, (byte)0); 

				Arrays.fill(sendData, (byte)0);
				
				System.out.println();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
