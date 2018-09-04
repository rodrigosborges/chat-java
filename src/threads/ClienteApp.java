
package threads;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClienteApp {
    public static void main(String[] args) throws UnknownHostException,IOException {
        // dispara cliente
        new ClienteTeste("127.0.0.1", 6666).executa();
    }
}
