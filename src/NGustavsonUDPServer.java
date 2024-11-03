import java.net.*;
import java.io.*;
import java.util.Arrays;

public class NGustavsonUDPServer {
    
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9999);
            
            byte[] receiveData = new byte[1024];
            byte[] sendData;
            
            while (true) {
                System.out.println("Waiting for client on port 9999...");
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                //print the received message
                String receivedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received sentence: " + receivedSentence);
                
                // Echo message back to client
                sendData = receivedSentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
                System.out.println("");
                
                // Clear the buffer for the next message
                Arrays.fill(receiveData, (byte) 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
