
package threads;

import java.io.InputStream;
import java.util.Scanner;
import model.Cliente;

public class TrataCliente implements Runnable{
    private Cliente cliente;
    private Servidor servidor;

    public TrataCliente(Cliente cliente, Servidor servidor){
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        Scanner s = new Scanner(this.cliente.getIn());
        while(s.hasNextLine()) {
            servidor.controleMensagem(s.nextLine(), cliente);
        }
        s.close();
    }
}