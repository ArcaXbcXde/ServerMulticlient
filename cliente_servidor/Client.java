package cliente_servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("127.0.0.1", 12345);
            Scanner leia = new Scanner(System.in);
            String msg;
            String nome;
            String socketStr;

            System.out.printf("Digite seu nome de usuário: ");
            nome = leia.nextLine();

            System.out.println("Cliente se conectou ao servidor no " + socket + ".\n");

            //BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
                msg = teclado.readLine();

                DataOutputStream paraServidor = new DataOutputStream(socket.getOutputStream());
                BufferedReader doServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                paraServidor.writeBytes(nome + ": " + msg + "\n");

                if (msg.equals("exit")) {
                    socket.close();
                    System.out.println("\nCliente saiu do servidor.");
                }

                socketStr = doServidor.readLine();

                System.out.println(socketStr);
            }

            // teclado.hasNextLine
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
