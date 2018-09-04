    package view;

    import threads.ClienteTeste;
    import threads.Servidor;
    import java.io.IOException;



    public class MainTeste {
    
    public static void main(String[] args) throws IOException{
        new Servidor(6666).executa();
       
    }
}
