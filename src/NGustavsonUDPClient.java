import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class NGustavsonUDPClient 
{

	public static void main(String[] args) 
	{
		String serverName = "localhost";
		//String serverName = "192.168.1.152";
		int port = 9999;
		String sentence = args[0];
		
		try 
		{
			DatagramSocket clientSocket = new DatagramSocket();
			
			InetAddress IPAddress = InetAddress.getByName(serverName);

			System.out.println("UDP Client started...");

			long startTime = System.nanoTime();
			
			byte[] sendData = new byte[1024];
			
			sendData = sentence.getBytes();
			
			System.out.println("UDP Client says: " + sentence);
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			
			clientSocket.send(sendPacket);
			
			byte[] receiveData = new byte[1024];
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			clientSocket.receive(receivePacket);

			long endTime = System.nanoTime();

			//RTT is in milliseconds
			double RTT = (endTime - startTime) / 1000000;

			String receivedSentence = new String(receivePacket.getData());

			if (!receivedSentence.equals(sentence)) {
				System.out.println("Error: Mismatched response from server");
			} else {
				int lengthBits = sentence.length() * 8;
				double throughput = lengthBits / (RTT / 2 * 1000)

				System.out.println("Sentence: " + sentence);
				System.out.println("RTT: " + RTT);
				System.out.println("Throughput: " + throughput);
			}
			
			System.out.println("UDP Server says: " + receivedSentence);
			
			clientSocket.close();
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 

	} 

}
