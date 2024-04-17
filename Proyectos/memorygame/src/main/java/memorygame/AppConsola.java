package memorygame;

import java.util.*;

public class AppConsola {
    Juego j;
   
    public AppConsola(int dim){
        j = new Juego(dim);
    }
    private void mostrarTablero(){
        int[][] t = j.getTablero();
        int[][] c = j.getCartas();
        for(int i = 0; i< t.length; i++){
            for(int j = 0; j< t[i].length; j++){
                if(t[i][j]== 1){
                    System.out.print(c[i][j] + " ");
                }else{
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
    public void jugar(){
        Scanner teclado = new Scanner(System.in);
        mostrarTablero();
        while(true){
            //preguntar que carta quiere levantar y comprobar que la posición es correcta
            System.out.println("Que fila?");
            int fila = teclado.nextInt();
            System.out.println("Que columna?");
            int col = teclado.nextInt();
           

            boolean res;
            if(j.esPrimera()){
                 res = j.levantarCarta(fila, col);
                mostrarTablero();
            }else{
                 res = j.levantarCarta(fila, col);
                 mostrarTablero();

                if(res){
                        //mensaje
                        System.out.println("Son pareja");
                }else{
                    j.bajarCarta(fila, col);
                    j.bajarCarta(j.getPRimeraFila(), j.getPRimeraColumna());
                    System.out.println("No son pareja");
                        //mensaje no pareja
                }
                j.resetearJugada();
                if(j.isGameOver()){
                    break;
                }
            }
        }
    }
   
    
    public static void main(String[] args) {

        
        AppConsola app = new AppConsola(4);
        app.jugar();
    }
}
