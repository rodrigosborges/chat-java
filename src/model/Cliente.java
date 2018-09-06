package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Cliente {
    private String nome = null;
    private Socket socket;
    
    public Cliente(Socket socket) {
        this.socket = socket;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InputStream getIn() throws IOException {
        return socket.getInputStream();
    }

    public PrintStream getOut() throws IOException {
        return new PrintStream(socket.getOutputStream());
    }
    
    public Socket getSocket(){
        return this.socket;
    }
     
}
