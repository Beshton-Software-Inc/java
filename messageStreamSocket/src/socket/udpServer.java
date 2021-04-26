package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class udpServer {
    public static void main(String[] args) throws Exception {

        // build new socket
        DatagramSocket datagramSocket = new DatagramSocket(6324);

        // receive data
        while(true) {
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);

            datagramSocket.receive(datagramPacket);
            byte[] data = datagramPacket.getData();
            String msg = new String(data);
            String flag = new String(data, 0, 4);
            System.out.println(msg);
            if(flag.equals("exit") && data[4] == 0) break;

        }

        // Close
        datagramSocket.close();
    }
}
