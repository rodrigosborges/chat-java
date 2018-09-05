package model;

import java.io.InputStream;
import java.io.PrintStream;

public class Cliente {
    private String nome = null;
    private InputStream in;
    private PrintStream out;

    public Cliente(String nome, InputStream in, PrintStream out) {
        this.nome = nome;
        this.in = in;
        this.out = out;
    }
    
    public Cliente(InputStream in,PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public PrintStream getOut() {
        return out;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }
    
    
}
