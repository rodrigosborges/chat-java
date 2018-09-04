package threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


    public class Servidor {

        private int porta;
        private List<PrintStream> clientes;

        public Servidor(int porta) {
            this.porta = porta;
            this.clientes = new ArrayList<PrintStream>();
        }

        public void executa() throws IOException {
            // abertura de porta
            ServerSocket servidor = new ServerSocket(this.porta);
            System.out.println("Porta " + this.porta + " aberta!");

            while (true) {
                // aceita cliente e imprime cliente conectado
                Socket cliente = servidor.accept();
                System.out.println("Nova conexão com cliente "
                        + cliente.getInetAddress().getHostAddress());

                // adiciona saida do cliente a lista
                PrintStream ps = new PrintStream(cliente.getOutputStream());
                this.clientes.add(ps);

                // cria tratador de cliente numa nova thread
                TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
                new Thread(tc).start();
            }
        }

        public void distribuiMensagem(String msg, InputStream remetente) {
            for (PrintStream cliente : this.clientes){
                if(!cliente.equals(remetente))
                    cliente.println(msg);
            }
        }
    }