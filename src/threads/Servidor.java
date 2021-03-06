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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Cliente;


    public class Servidor extends Thread{

        private int porta;
        private ArrayList<Cliente> clientes;

        public Servidor(int porta) {
            this.porta = porta;
            this.clientes = new ArrayList<Cliente>();
        }
        
        public void run(){
            try {
                this.executa();
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void executa() throws IOException {
            // abertura de porta
            try{
                ServerSocket servidor = new ServerSocket(this.porta);

                while (true) {
                    // aceita cliente e imprime cliente conectado
                    Socket cliente = servidor.accept();

                    // adiciona saida do cliente a lista
                    Cliente cl = new Cliente(cliente);
                    this.clientes.add(cl);

                    // cria tratador de cliente numa nova thread
                    TrataCliente tc = new TrataCliente(cl, this);
                    new Thread(tc).start();
                }
            }catch(Exception e){}
        }
        
        public void controleMensagem(String msg, Cliente remetente) throws IOException{
            String funcao = msg.split(":")[0];
            switch(funcao){
                case "login": 
                    login(msg.split(":",2)[1],remetente);
                    break;
                case "mensagem":
                    transmitir(msg.split(":")[1], msg.split(":",3)[2],remetente);
                    break;
                case "sair":
                    clientes.remove(remetente);
                    break;
                default:
                    break;
            }
        }
        
        public void login(String nome,Cliente remetente) throws IOException{
            Boolean livre = true;
            for (Cliente cliente : this.clientes){
                if(nome.equals(cliente.getNome()))
                    livre = false;
            }
            
            Boolean resultado = livre && nome.matches("[a-zA-Z0-9 ]*") && !nome.equals("");
                        
            remetente.getOut().println("login:"+resultado.toString());
            remetente.getOut().flush();
            
            if(resultado){
                remetente.setNome(nome);
                lista_usuarios();
            }
        }
        
        public void remove_usuario(Cliente cliente) throws IOException{
            clientes.remove(cliente);
            lista_usuarios();
        }
        
        public void lista_usuarios() throws IOException{
            String mensagem = "lista_usuarios:";
            for (Cliente cliente : this.clientes){
                if(cliente.getNome() != null){
                    mensagem += (mensagem.substring(mensagem.length() - 1).equals(":") ? "" : ";")+cliente.getNome();
                }
            }
            for (Cliente cliente : this.clientes){
                if(cliente.getNome() != null){
                    cliente.getOut().println(mensagem);
                    cliente.getOut().flush();
                }
            }    
        }

        public void transmitir(String destinatarios,String msg,Cliente remetente) throws IOException {
            if(remetente.getNome() != null){
                if(destinatarios.equals("*")){
                    for (Cliente cliente : this.clientes){
                        if(cliente.getNome() != null && !cliente.getNome().equals(remetente.getNome())){
                            cliente.getOut().println("transmitir:"+remetente.getNome()+":*:"+msg);
                            cliente.getOut().flush();
                        }
                    }                    
                }else{
                    String[] destinatariosarray = destinatarios.split(";");
                    for (Cliente cliente : this.clientes){
                        for (String destinatario : destinatariosarray){
                            if(cliente.getNome() != null && cliente.getNome().equals(destinatario) && !remetente.getNome().equals(destinatario)){
                                cliente.getOut().println("transmitir:"+remetente.getNome()+":"+destinatarios+":"+msg);
                                cliente.getOut().flush();
                            }
                        }
                    }
                }
            }
        }
    }