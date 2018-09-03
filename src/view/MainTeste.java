/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import threads.Cliente;
import threads.Servidor;
import java.io.IOException;

/**
 *
 * @author a1700651
 */
public class MainTeste {
    
    public static void main(String[] args) throws IOException{
        Servidor servidor = new Servidor(6666);
        servidor.start();
        
//        Cliente cliente = new Cliente("127.0.0.1");
//        cliente.start();
//        
//        Cliente cliente2 = new Cliente("127.0.0.1");
//        cliente2.start();
    }
}
