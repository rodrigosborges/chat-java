/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Cliente;
import controller.Servidor;
import java.io.IOException;

/**
 *
 * @author a1700651
 */
public class MainTeste {
    
    public static void main(String[] args) throws IOException{
        Cliente cliente = new Cliente("127.0.0.1");
        Servidor servidor = new Servidor(6666);
        
        servidor.run();
        cliente.run();
    }
}
