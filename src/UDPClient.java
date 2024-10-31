import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class UDPClient 
{

	public static void main(String[] args) 
	{
		String serverName = "localhost";
		//String serverName = "192.168.1.152";
		int port = 9999;
		
		try 
		{
			DatagramSocket clientSocket = new DatagramSocket();
			
			InetAddress IPAddress = InetAddress.getByName(serverName);
			
			byte[] sendData = new byte[1024];
			
			String sentence = JOptionPane.showInputDialog("Enter Client Message: ");
			
			//String sentence = "Hello";
			
			sendData = sentence.getBytes();
			
			System.out.println("UDP Client says: " + sentence);
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			
			clientSocket.send(sendPacket);
			
			byte[] receiveData = new byte[1024];
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			clientSocket.receive(receivePacket);
			
			String receivedSentence = new String(receivePacket.getData());
			
			System.out.println("UDP Server says: " + receivedSentence);
			
			clientSocket.close();
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 

	} 

}
