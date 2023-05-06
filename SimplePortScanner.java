import java.io.IOException;
import java.net.Socket;

public class SimplePortScanner {

    public static void main(String[] args) {
        String ipAddress = "127.0.0.1"; //edit IP address
        int startPort = 1;
        int endPort = 65535;

        scanPorts(ipAddress, startPort, endPort);
    }

    //A method that scans ports given an IP address, a starting Port and an ending Port
    public static void scanPorts(String ipAddress, int startPort, int endPort) {
        for (int port = startPort; port <= endPort; port++) {
            try (Socket socket = new Socket()) {
                socket.connect(new java.net.InetSocketAddress(ipAddress, port), 200);
                System.out.printf("Port %d is open%n", port);
            } catch (IOException ignored) {
                // Port is closed or unreachable
                //System.out.printf("Port %d is closed or unreachable", port);
            }
        }
    }
}

