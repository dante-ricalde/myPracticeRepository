package test.org.spring.websocket.api.heartbeat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatListener implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatListener.class);

    // multicast socket
    private Socket mcastSocket = null;

    private InetAddress mcastAddress = null;

    private String mcastDestination = "239.1.2.3";

    // destination port
    private int mcastPort = 8123;

    private boolean isClosed = false;

    private Thread thread = null;

    // net interface name
    private String interfaceName = null;

    /**
     * Construct a heartbeat consumer with default address and port.
     *
     * @throws IOException
     */
    public HeartbeatListener() throws IOException {
        this.init();
    }

    /**
     * Construct a heartbeat listener with the specified network interface name.
     * 
     * @param netInterfaceName
     * @throws IOException
     */
    public HeartbeatListener(String netInterfaceName) throws IOException {

        this.interfaceName = netInterfaceName;

        this.init();
    }

    public HeartbeatListener(String address, int port) throws IOException {

        this.mcastDestination = address;

        this.mcastPort = port;

        this.init();
    }

    private void init() throws IOException {

        // network interface
        NetworkInterface ni = null;

        if (this.interfaceName != null) {
            ni = NetworkInterface.getByName(interfaceName);
        } else {
            //Oni = NetUtil.findMulticastNetworkInterface();
        }

        // my multicast listening address
        mcastAddress = InetAddress.getByName(mcastDestination);

        // msocket
        //mcastSocket = new MulticastSocket(mcastPort);
        mcastSocket = new Socket(mcastAddress, mcastPort);

        // only set it if we are allowed to search
        if (ni != null) {
        	LOGGER.info("using network interface: " + ni.getDisplayName());
           // mcastSocket.setNetworkInterface(ni);
        }

        // join the m group
        //this.mcastSocket.joinGroup(mcastAddress);

        // listen in the background
        this.thread = new Thread(this);

        thread.start();
    }

    public void close() throws IOException {
        this.isClosed = true;
        this.mcastSocket.close();
    }

    @Override
    public void run() {

    	LOGGER.info("Heart beat listener is ready on address: "
                + this.mcastDestination + ":" + this.mcastPort);

        while (isClosed == false) {

            try {
                byte[] data = new byte[64 * 1024];

                DatagramPacket packet = new DatagramPacket(data, data.length);

//                this.mcastSocket.receive(packet);
                this.mcastSocket.getInputStream().read(data);
                
                // deliver
                onMessage(packet.getData());

            } catch (Exception e) {
            	LOGGER.warn(e.getMessage(), e);
            }
        }

    }

    /**
     * Applications override this method to receive heart beat messages.
     *
     * @param data
     *            UTF8 encoded heart beat message.
     */
    public void onMessage(byte[] data) {

        try {
        	LOGGER.info("received heart beat: " + new String(data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
        	LOGGER.warn(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        @SuppressWarnings("unused")
        HeartbeatListener listener = new HeartbeatListener("localhost", 8080);
    }
}