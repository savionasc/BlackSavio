package blacksavio;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jogo implements Runnable{
    int soma1 = 0, soma2 = 0;
    int etapa = 0;
    int valores1 = 0, valores2 = 0;
    
    class EscutarPerdeu implements Runnable{
        ObjectOutputStream oos;
        ObjectInputStream ois;
        Socket s;
        public EscutarPerdeu(ObjectOutputStream oos1, ObjectInputStream ois1, Socket cliente) {
            this.oos = oos1;
            this.ois = ois1;
            this.s = cliente;
        }

        @Override
        public void run() {
            try {
                if((int) ois.readObject() <= 0){
                    oos.writeObject("Você perdeu!!");
                    System.out.println("Pass2");
                }else{
                    oos.writeObject("Você ganhou!!");
                    System.out.println("Pass2");
                }
                
                s.close();
            } catch (Exception e) {
            }
        }
    }
    class RetornarValores implements Runnable{
        ObjectOutputStream oos;
        ObjectInputStream ois;
        int valor;
        public RetornarValores(ObjectOutputStream oos1, ObjectInputStream ois1, int player, int ganhador, int valor) {
            this.oos = oos1;
            this.ois = ois1;
            this.valor = valor;
        }
        
        @Override
        public void run() {
            try {
                oos.writeObject(valor);
            } catch (Exception e) {
            }
        }
    
    }
    class ReceberValores implements Runnable{
        ObjectOutputStream oos;
        ObjectInputStream ois;
        public ReceberValores(ObjectOutputStream oos1, ObjectInputStream ois1) {
            this.oos = oos1;
            this.ois = ois1;
        }
        
        @Override
        public void run() {
            try {
                if(ois.readObject().toString().equals("Valores")){
                    int play = (int) ois.readObject();
                    if((play%2) == 1){
                        valores1 = (int) ois.readObject();
                    }else{
                        valores2 = (int) ois.readObject();
                    }
                    etapa++;
                }                
            } catch (Exception e) {
            }
        }
    
    }
    
    class ReceberResposta implements Runnable{
        ObjectOutputStream oos;
        ObjectInputStream ois;
        public ReceberResposta(ObjectOutputStream oos1, ObjectInputStream ois1) {
            this.oos = oos1;
            this.ois = ois1;
        }
        @Override
        public void run() {
            try {
                if(ois.readObject().toString().equals("Arriscar")){
                    int player = (int) ois.readObject();
                    if((player%2) == 1){
                        soma1 = (int) ois.readObject();
                        System.out.println("O player "+player+" arriscou com: "+soma1);
                    }else{
                        soma2 = (int) ois.readObject();
                        System.out.println("O player "+player+" arriscou com: "+soma2);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    class PassarCartas implements Runnable{
        ObjectOutputStream oos;
        ObjectInputStream ois;
        public PassarCartas(ObjectOutputStream oos1, ObjectInputStream ois1) {
            this.oos = oos1;
            this.ois = ois1;
        }
        @Override
        public void run() {
            try {
                if(ois.readObject().toString().equals("cards")){
                    Random gerador = new Random();
                    for (int i = 0; i < 2; i++) {
                        Carta c = new Carta(gerador.nextInt(10)+1, gerador.nextInt(4));
                        oos.writeObject(c);
                    }
                    System.out.println("Etapa");
                    etapa++;
                }
            } catch (Exception ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    class finalizar implements Runnable{

        @Override
        public void run() {
            try {
                System.out.println("O usuario 1: "+ (String) ois1.readObject());
                System.out.println("O usuario 2: "+ (String) ois2.readObject());
            } catch (Exception ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    ObjectOutputStream oos1;
    ObjectInputStream ois1;
    ObjectOutputStream oos2;
    ObjectInputStream ois2;
    Socket Client1;
    Socket Client2;
    int player;
    
    public Jogo(Socket Client1, Socket Client2, int player) throws Exception {
        this.Client1 = Client1;
        this.Client2 = Client2;
        this.player = player;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        try {
            oos1 = new ObjectOutputStream(Client1.getOutputStream());
            ois1 = new ObjectInputStream(Client1.getInputStream());                
            oos1.writeObject(player);
            oos1.writeObject("5000");
            List<Carta> cartas1 = new ArrayList<>();

            oos2 = new ObjectOutputStream(Client2.getOutputStream());
            ois2 = new ObjectInputStream(Client2.getInputStream());                
            oos2.writeObject(player+1);
            oos2.writeObject("5000");
            List<Carta> cartas2 = new ArrayList<>();

            Thread tReceberValores1 = new Thread(new ReceberValores(oos1, ois1));
            tReceberValores1.start();
            Thread tReceberValores2 = new Thread(new ReceberValores(oos2, ois2));
            tReceberValores2.start();
            while (etapa < 2) {
                System.out.println("x: "+valores1+" y: "+valores2);
            }

            Thread tPassarCartas1 = new Thread(new PassarCartas(oos1, ois1));
            tPassarCartas1.start();
            Thread tPassarCartas2 = new Thread(new PassarCartas(oos2, ois2));
            tPassarCartas2.start();

            /*
            if(ois1.readObject().toString().equals("cards")){
                Random gerador = new Random();
                //imprime sequência de 10 números inteiros aleatórios entre 0 e 25
                for (int i = 0; i < 2; i++) {
                    Carta c = new Carta(gerador.nextInt(13), gerador.nextInt(4));
                    oos1.writeObject(c);
                    cartas1.add(c);
                }
            }
            
            if(ois2.readObject().toString().equals("cards")){
                Random gerador = new Random();
                //imprime sequência de 10 números inteiros aleatórios entre 0 e 25
                for (int i = 0; i < 2; i++) {
                    Carta c = new Carta(gerador.nextInt(13)+1, gerador.nextInt(4));
                    oos2.writeObject(c);
                    cartas2.add(c);
                }
            }*/
            while (etapa < 4){
                System.out.println("a"+etapa);
            }
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            Thread tReceberResposta1 = new Thread(new ReceberResposta(oos1, ois1));
            tReceberResposta1.start();
            Thread tReceberResposta2 = new Thread(new ReceberResposta(oos2, ois2));
            tReceberResposta2.start();
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            
            while (soma1 == 0 || soma2 == 0){
                System.out.println("1: "+soma1 + " 2: "+soma2);
            }
            System.err.println("Passou 1");
            oos1.writeObject(""+soma2);
            oos2.writeObject(""+soma1);
            if(soma1 > soma2){
                System.out.println("O ganhador foi o player 1 com "+soma1+" pontos e valor: "+(valores1+valores2));
                oos1.writeObject((valores1+valores2));
                oos2.writeObject(0);
                etapa++;
            }else if(soma2 > soma1){
                System.out.println("O ganhador foi o player 2 com "+soma2+" pontos e valor: "+(valores1+valores2));
                oos2.writeObject((valores1+valores2));
                oos1.writeObject(0);
                etapa++;
            }else{
                System.err.println("Empate!!");
                oos2.writeObject(valores2);
                oos1.writeObject(valores1);
                etapa++;
            }
            while (etapa < 3){
                System.out.println("a"+etapa);
            }
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            Thread tEscutarPerdeu1 = new Thread(new EscutarPerdeu(oos1, ois1, Client1));
            tEscutarPerdeu1.start();
            Thread tEscutarPerdeu2 = new Thread(new EscutarPerdeu(oos2, ois2, Client2));
            tEscutarPerdeu2.start();
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            System.out.println("Etapa 2");
            
        } catch (Exception ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
