/**
 * prueba
 */
public class prueba {

    public static void main(String args[]) {
        int primero = 8;
        int segundo = 3;
        int contador = 0;
        int resto = 0;
        do {
            primero = primero - segundo;
            contador++;
        } while (primero >= segundo);
        resto = primero;
        System.out.println("Resultado" + contador);
        System.out.println("Sobra" + resto);
    }
}
