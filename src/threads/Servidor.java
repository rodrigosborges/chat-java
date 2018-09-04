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
import model.Cliente;


    public class Servidor {

        private int porta;
        private List<Cliente> clientes;

        public Servidor(int porta) {
            this.porta = porta;
            this.clientes = new ArrayList<Cliente>();
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
                Cliente cl = new Cliente(cliente.getInputStream(),new PrintStream(cliente.getOutputStream()));
                this.clientes.add(cl);

                // cria tratador de cliente numa nova thread
                TrataCliente tc = new TrataCliente(cl, this);
                new Thread(tc).start();
            }
        }
        
        public void controleMensagem(String msg, Cliente remetente){
            String funcao = msg.split(":")[0];
            switch(funcao){
                case "login": 
                    login(msg.split(":")[1]);
                case "mensagem": 
                    transmitir(msg.split(":")[1], msg.split(":")[2], remetente);
            }
        }
        
        public void login(String msg){
            
        }
        
        public void lista_usuarios(){
            
        }
        
        public void mensagem(){
            
        }

        public void transmitir(String destinatarios,String msg, Cliente remetente) {
            for (Cliente cliente : this.clientes){
                if(remetente != cliente)
                    cliente.getOut().println(msg);
            }
        }
    }