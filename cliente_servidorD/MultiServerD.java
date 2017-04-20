package cliente_servidor;
// echo server

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServerD {

    public static void main(String args[]) {

        Socket s = null;
        ServerSocket ss2 = null;
        try {
            ss2 = new ServerSocket(12345);
            System.out.println("Servidor escutando na porta 12345.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro de servidor.");

        }

        while (true) {
            try {
                s = ss2.accept();
                System.out.println("Conectado.");
                ServerThread st = new ServerThread(s);
                st.start();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erro de conexão.");

            }
        }

    }

}

class ServerThread extends Thread {

    String line = null;
    BufferedReader is = null;
    PrintWriter os = null;
    Socket s = null;

    public ServerThread(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());

        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }

        try {
            line = is.readLine();
            while (line.compareTo("QUIT") != 0) {

                os.println(line);
                os.flush();
                System.out.println("Response to Client : " + line);
                line = is.readLine();
            }
        } catch (IOException e) {

            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        } finally {
            try {
                System.out.println("Fechando conexão.");
                if (is != null) {
                    is.close();
                    System.out.println("Socket Input Stream Closed");
                }

                if (os != null) {
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s != null) {
                    s.close();
                    System.out.println("Socket Closed");
                }

            } catch (IOException ie) {
                System.out.println("Socket Close Error");
            }
        }//end finally
    }
}
/* Essa classe lê o socket (is), armazena temporariamente em uma string (line), e guarda em uma outra variável (os), escreve a resposta do cliente,
		e lê a próxima requisição, até fechar a conexão*/