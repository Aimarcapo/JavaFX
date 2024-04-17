package memorygame;

import java.util.*;

public class Juego {

    private int[][] cartas;
    private int[][] tablero;
    private int dimension;
    private int numParejas;
    private int contadorAciertos;
    private int primeraCartaPos;

    public Juego(int dimension) {
        this.dimension = dimension;
        contadorAciertos = 0;
        primeraCartaPos = -1;
        numParejas = dimension * dimension / 2;
        tablero = new int[dimension][dimension];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                tablero[i][j] = 0;
            }
        }
        barajarCartas();
    }
    public int getDimension(){
        return dimension;
    }
    public int getAciertos(){
        return contadorAciertos;
    }
    public int[][] getTablero(){
        return tablero;
    }
    public int[][] getCartas(){
        return cartas;
    }
    private void barajarCartas() {
        cartas = new int[dimension][dimension];

        int pos = 0;
        int i, j;
        for (int n = 1; n <= numParejas; n++) {

            i = pos / dimension;
            j = pos - i * dimension;
            
            cartas[i][j] = n;
            pos++;
            i = pos / dimension;
            j = pos - i * dimension;
            cartas[i][j] = n;
            pos++;
        }

        Random random = new Random();
        // Barajar las cartas
        for (i = 0; i < cartas.length; i++) {
            for (j = 0; j < cartas[i].length; j++) {
                int randomI = random.nextInt(cartas.length);
                int randomJ = random.nextInt(cartas[i].length);
                int temp = cartas[i][j];
                cartas[i][j] = cartas[randomI][randomJ];
                cartas[randomI][randomJ] = temp;
            }

        }
    }

    private void mostrarCarta(int i, int j) {
        tablero[i][j] = 1;
    }

    public void bajarCarta(int i, int j) {
        tablero[i][j] = 0;
    }

    public void resetearJugada(){
        primeraCartaPos = -1;
        
    }


    public boolean sonPareja(int i1, int j1, int i2, int j2) {

        if (cartas[i1][j1] == cartas[i2][j2]) {
            contadorAciertos++;
        }
        return cartas[i1][j1] == cartas[i2][j2];

        // comprobar si son pareja
        // si lo son aciertos +++
    }

    public boolean isGameOver() {
        return contadorAciertos == numParejas;
    }
    public boolean esPrimera(){
        return primeraCartaPos ==-1;
    }
    public int getPRimeraFila(){
        return primeraCartaPos/dimension;
    }
    public int getPRimeraColumna(){
        return primeraCartaPos - getPRimeraFila()*dimension;
    }
    public boolean levantarCarta(int i, int j) {
        // mostrarCarta
        mostrarCarta(i, j);
        if(primeraCartaPos==-1){
            primeraCartaPos=i*dimension+j;
            return false;
        }else{
            int i1 = primeraCartaPos/dimension;
            int j1 = primeraCartaPos-i1*dimension;
           
            if(sonPareja(i1, j1, i, j)){
                return true;
            }else{
                
                return false;
            }
        }
        // comprobar si es la primera vez que levanta, si lo es, guardar la carta
        // levantada
        // si no lo es, comprobar que son pareja

        // si no pareja; doy la vuelta a las cartas y reseteo primeraCarta;

    }

}
