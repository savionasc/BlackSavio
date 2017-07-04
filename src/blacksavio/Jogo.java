package blacksavio;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Jogo implements Runnable{
    Socket Client1;
    Socket Client2;
    int player;
    public Jogo(Socket Client1, Socket Client2, int player) throws Exception {
        this.Client1 = Client1;
        this.Client2 = Client2;
        this.player = player;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos1 = new ObjectOutputStream(Client1.getOutputStream());
            ObjectInputStream ois1 = new ObjectInputStream(Client1.getInputStream());                
            oos1.writeObject(player);
            oos1.writeObject("5000");
            
            ObjectOutputStream oos2 = new ObjectOutputStream(Client2.getOutputStream());
            ObjectInputStream ois2 = new ObjectInputStream(Client2.getInputStream());                
            oos2.writeObject(player+1);
            oos2.writeObject("5000");
            
            if(ois1.readObject().toString().equals("cards")){
                Random gerador = new Random();
                //imprime sequência de 10 números inteiros aleatórios entre 0 e 25
                for (int i = 0; i < 2; i++) {
                    Carta c = new Carta(gerador.nextInt(13), gerador.nextInt(4));
                    oos1.writeObject(c);
                }
            }
            
            if(ois2.readObject().toString().equals("cards")){
                Random gerador = new Random();
                //imprime sequência de 10 números inteiros aleatórios entre 0 e 25
                for (int i = 0; i < 2; i++) {
                    Carta c = new Carta(gerador.nextInt(13)+1, gerador.nextInt(4));
                    oos2.writeObject(c);
                }
            }
            
            System.out.println("O usuario 1: "+ (String) ois1.readObject());
            System.out.println("O usuario 2: "+ (String) ois2.readObject());
        } catch (Exception ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
