import java.net.*;
import java.io.*;

public class NGustavsonUDPClient {
    
    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 9999;

        // Ensure a sentence is provided as an argument
        if (args.length < 1) {
            System.out.println("Please provide a sentence as a command line argument.");
            return;
        }

        String sentence = args[0];

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(serverName);

            System.out.println("UDP Client started...");

            // Start the timer
            long startTime = System.nanoTime();
            
            byte[] sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            // Stop the timer
            long endTime = System.nanoTime();

            // Calculate RTT in milliseconds
            double RTT = (endTime - startTime) / 1_000_000.0;

            // Get the received message correctly
            String receivedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Validate response
            if (!receivedSentence.equals(sentence)) {
                System.out.println("Error: Mismatched response from server.");
            } else {
                int lengthBits = sentence.length() * 8;
                double throughput = lengthBits / (RTT / 2 * 1_000);  // Throughput in Mbps

                System.out.printf("Sentence: \"%s\"\n", sentence);
                System.out.printf("RTT: %.2f ms\n", RTT);
                System.out.printf("Throughput: %.1f Mbps\n", throughput);
            }

            System.out.println("UDP Server says: " + receivedSentence);

            clientSocket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
