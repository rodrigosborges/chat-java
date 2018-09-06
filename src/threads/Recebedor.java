package threads;

import java.io.InputStream;
import java.util.Scanner;

public class Recebedor implements Runnable {

    private InputStream servidor;
    private ClienteThread thread;
    
    public Recebedor(InputStream servidor, ClienteThread thread) {
        this.servidor = servidor;
        this.thread = thread;
    }

    @Override
    public void run() {
        // Recebe as mensagens
        Scanner s = new Scanner(this.servidor);

        while (s.hasNextLine()) {
            thread.controleMensagem(s.nextLine());
        }
    }
}