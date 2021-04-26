package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class udpClient {
    public static void main(String[] args) throws Exception {
        // build a socket
        DatagramSocket datagramSocket = new DatagramSocket();

        // Claim target address
        InetAddress localhost = InetAddress.getByName("localhost");
        int port = 6324;

        // new data packet
        Scanner scanner = new Scanner(System.in);
        while(true){
            String msg = scanner.next();
            byte[] bytes = msg.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length, localhost, port);
            // send
            datagramSocket.send(datagramPacket);
            if(msg.equals("exit")) break;
        }




        // Close
        datagramSocket.close();
    }
}
