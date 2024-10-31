import java.net.*;
import java.io.*;
import java.util.Arrays;

public class NGustavsonUDPServer {

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9999);
            System.out.println("Waiting for client on port 9999...");

            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                // Read and print the received sentence
                String receivedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("RECEIVED: from IPAddress " + IPAddress + " and from port " + port + " the data: " + receivedSentence);

                // Send the same sentence back (as a "pong" response)
                sendData = receivedSentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);

                Arrays.fill(receiveData, (byte) 0);
                Arrays.fill(sendData, (byte) 0);
                
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
