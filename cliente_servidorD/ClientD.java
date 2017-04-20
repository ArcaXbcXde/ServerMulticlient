package cliente_servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientD {

    public static void main(String[] args) {

        try {
            Socket cliente = new Socket("127.0.0.1", 12345);
            System.out.println(" Sou o cliente ");

            System.out.println("O cliente se conectou ao servidor!");

            try (Scanner teclado = new Scanner(System.in);
                    PrintStream saida = new PrintStream(cliente.getOutputStream())) {

                while (teclado.hasNextLine()) {
                    String msg = teclado.nextLine();
                    saida.println(msg);
                    if (msg.equals("exit")) {
                        cliente.close();
                    }
                }
                // teclado.hasNextLine
            }

        } catch (UnknownHostException e) {
        } catch (IOException e) {
        }
    }
}