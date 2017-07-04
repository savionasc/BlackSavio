package blacksavio;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author savio
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket servidor;
        int cTosPortNumber = 1777;
        //Esperando o primeiro cliente
        servidor = new ServerSocket(cTosPortNumber);
        System.out.println("Esperando o primeiro player pela porta " + cTosPortNumber);


        /*
        ObjectOutputStream oos = new ObjectOutputStream(fromClientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(fromClientSocket.getInputStream());                
        oos.writeObject("1");

        Random gerador = new Random();
        //imprime sequência de 10 números inteiros aleatórios entre 0 e 25
        for (int i = 0; i < 2; i++) {
            Carta c = new Carta(gerador.nextInt(13), gerador.nextInt(4));
            oos.writeObject(c);
        }*/
        List<Thread> clientes = new ArrayList<>();
        int i = 1;
        //Sala
        while(true){
            Socket cliente1 = servidor.accept();
            Socket cliente2 = servidor.accept();
            Jogo tratamento = new Jogo(cliente1, cliente2, i);
            // cria a thread em cima deste objeto
            Thread t = new Thread(tratamento);

            // inicia a thread
            t.start();
            i += 2;            
        }
    }
}
