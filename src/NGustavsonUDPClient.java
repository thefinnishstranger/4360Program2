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

            // Calculate RTT in milliseconds without rounding
            double actualRTT = (endTime - startTime) / 1_000_000.0;

            // Get the received message correctly
            String receivedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

            // Validate response
            if (!receivedSentence.equals(sentence)) {
                System.out.println("Error: Mismatched response from server.");
            } else {
                int lengthBits = sentence.length() * 8;  // Sentence length in bits
                double throughput = lengthBits / ((actualRTT / 2) * 1_000);  // Throughput in Mbps using actual RTT

                // Print rounded output
                System.out.printf("Length of sentence: %d bits\n", lengthBits);
                System.out.printf("RTT: %.2f ms\n", actualRTT); // Display RTT rounded to two decimals
                System.out.printf("Throughput: %.1f Mbps\n", throughput); // Display throughput rounded to one decimal
            }

            clientSocket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
