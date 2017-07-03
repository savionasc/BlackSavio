package blacksavio;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jogo implements Runnable{
    Socket fromClientSocket;
    public Jogo(Socket fromClientSocket) throws Exception {
        this.fromClientSocket = fromClientSocket;
    }

    @Override
    public void run() {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(fromClientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(fromClientSocket.getInputStream());                
            oos.writeObject("1");

            /*Random gerador = new Random();
            //imprime sequência de 10 números inteiros aleatórios entre 0 e 25
            for (int i = 0; i < 2; i++) {
                Carta c = new Carta(gerador.nextInt(13), gerador.nextInt(4));
                oos.writeObject(c);
            }*/
        } catch (Exception ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
