/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import threads.Servidor;
/**
 *
 * @author 21609
 */
public class MainTeste {
    public static void main(String ARGS[]){
        new Servidor(6666).start();
    }
}
