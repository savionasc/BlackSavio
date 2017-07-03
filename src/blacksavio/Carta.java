package blacksavio;

import java.io.Serializable;

/**
 *
 * @author savio
 */
public class Carta implements Serializable{
    private int numero;
    public enum Naipe{COPAS, ESPADAS, OUROS, PAUS};
    private Naipe naipe;
    
    public Carta(int numero, Naipe naipe) {
        this.numero = numero;
        this.naipe = naipe;
    }
    
    public Carta(int numero, int naipe) {
        this.numero = numero;
        switch (naipe) {
            case 0:
                this.naipe =Naipe.COPAS;
                break;
            case 1:
                this.naipe =Naipe.ESPADAS;
                break;
            case 2:
                this.naipe =Naipe.OUROS;
                break;
            default:
                this.naipe =Naipe.PAUS;
                break;
        }
        
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    public void setNaipe(Naipe naipe) {
        this.naipe = naipe;
    }
}
