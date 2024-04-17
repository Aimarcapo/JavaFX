import java.util.Scanner;
public class Pruebaa {



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el numerador: ");
        int numerador = scanner.nextInt();

        System.out.print("Ingrese el divisor: ");
        int divisor = scanner.nextInt();

        // Verificar si el divisor es cero
        if (divisor == 0) {
            System.out.println("Error: No se puede dividir por cero.");
            return;
        }

        int cociente = 0;
        int signo = 1; // Signo del resultado

        // Manejar signos
        if ((numerador < 0 && divisor > 0) || (numerador > 0 && divisor < 0)) {
            signo = -1;
        }

        numerador = Math.abs(numerador);
        divisor = Math.abs(divisor);

        // Realizar la división
        while (numerador >= divisor) {
            numerador -= divisor;
            cociente++;
        }

        System.out.println("Resultado: " + cociente * signo);
    }
}


