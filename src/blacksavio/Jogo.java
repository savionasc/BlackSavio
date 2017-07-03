package blacksavio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Jogo {
    private int x;
    public static void main(String[] args) throws Exception {
        Socket fromClientSocket = null;
            int a = x;
        ObjectOutputStream oos = new ObjectOutputStream(fromClientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(fromClientSocket.getInputStream());                
        oos.writeObject("1");

        Random gerador = new Random();
            //imprime sequência de 10 números inteiros aleatórios entre 0 e 25
	    for (int i = 0; i < 2; i++) {
                Carta c = new Carta(gerador.nextInt(13), gerador.nextInt(4));
                oos.writeObject(c);
            }

    }
    
}
