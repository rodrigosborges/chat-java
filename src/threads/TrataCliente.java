
package threads;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;

public class TrataCliente implements Runnable{
    private Cliente cliente;
    private Servidor servidor;

    public TrataCliente(Cliente cliente, Servidor servidor){
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        Scanner s;
        try {
            s = new Scanner(this.cliente.getIn());
            while(s.hasNextLine()) {
                servidor.controleMensagem(s.nextLine(), cliente);
            }
            servidor.remove_usuario(cliente);
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(TrataCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}