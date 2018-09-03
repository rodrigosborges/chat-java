package exemplosockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTeste {
    public static void main(String[] args) throws UnknownHostException, IOException {
     
        Socket cliente = new Socket("127.0.0.1", 6666);
     
        System.out.println("O cliente se conectou ao servidor!");
     
        Scanner teclado = new Scanner(System.in);
     
        PrintStream saida = new PrintStream(cliente.getOutputStream());
        
        Scanner entrada = new Scanner(cliente.getInputStream());
     
        String mensagem = "";
        while (true) {
            System.out.print("Digite algo a ser enviado ao servidor: ");
            mensagem = teclado.nextLine();
            saida.println(mensagem);
            if(mensagem.equals("sair"))
                break;
        }
   }
}
