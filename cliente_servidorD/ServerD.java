package cliente_servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerD {

    public static void main(String args[]) {
        try {
            ServerSocket server = new ServerSocket(12345);
            System.out.println("Servidor iniciado na porta 12345");

            Socket cliente = server.accept();
            System.out.println("Cliente conectado do IP " + cliente.getInetAddress().
                    getHostAddress());
            Scanner entrada = new Scanner(cliente.getInputStream());
            while (entrada.hasNextLine()) {
                System.out.println(entrada.nextLine());
            }

            System.out.println("Cliente conectado do IP " + cliente.getInetAddress().
                    getHostAddress() + " finalizou conexão");
            entrada.close();
            server.close();

        } catch (IOException ex) {
            Logger.getLogger(ServerD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
