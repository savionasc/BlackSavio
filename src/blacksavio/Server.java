/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacksavio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author savio
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket servSocket;
        Socket fromClientSocket;
        int cTosPortNumber = 1777;
        
        //Esperando o primeiro cliente
        servSocket = new ServerSocket(cTosPortNumber);
        System.out.println("Esperando o primeiro player pela porta " + cTosPortNumber);

        fromClientSocket = servSocket.accept();
        
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
