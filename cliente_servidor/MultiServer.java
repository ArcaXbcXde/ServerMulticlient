package cliente_servidor;
// echo server

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {

    public static void main(String args[]) {

        Socket s = null;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(12345);
            System.out.println("Servidor escutando na porta 12345.");

        } catch (IOException e) {
            System.out.println("Erro de servidor.");

        }

        while (true) {
            try {
                s = ss.accept();
                System.out.println("Cliente se conectou.");
                ServerThread st = new ServerThread(s);
                st.start();

            } catch (Exception e) {
                System.out.println("Erro de conexão.");

            }
        }

    }

}

class ServerThread extends Thread {

    String line = null;
    Socket socket = null;
    BufferedReader msgClient = null;
    DataOutputStream toClient = null;

    public ServerThread(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {

            msgClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toClient = new DataOutputStream(socket.getOutputStream());

            while (true) {

                line = msgClient.readLine();

                toClient.writeBytes(line + "\n");

                System.out.println(line);
                line = msgClient.readLine();
            }

        } catch (IOException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.printf(line + " fechou a conexão. ");
        } catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.printf("Cliente " + line + " Fechou. ");
        } finally {
            try {

                System.out.printf("Fechando socket: ");
                if (msgClient != null) {
                    msgClient.close();
                    System.out.printf("Input Stream; ");
                }
                if (toClient != null) {
                    toClient.close();
                    System.out.printf("Out; ");
                }
                if (socket != null) {
                    socket.close();
                    System.out.printf("Closed; ");
                }
                System.out.println("");
            } catch (IOException ie) {
                System.out.printf("Close Error (" + ie + ");\n");
            }
        }
    }
}
