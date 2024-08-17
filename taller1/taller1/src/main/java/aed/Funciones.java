package aed;

class Funciones {
    int cuadrado(int x) {
        // COMPLETAR
        return x * x;
    }

    double distancia(double x, double y) {
        // COMPLETAR
        return Math.sqrt(x*x + y*y);
    }

    boolean esPar(int n) {
        // COMPLETAR
        return n % 2 == 0;
    }

    boolean esBisiesto(int n) {
        // COMPLETAR
        return ((n % 4 == 0) && (n % 100 != 0)) || n % 400 == 0;
    }

    int factorialIterativo(int n) {
        // COMPLETAR
        int res = 1;
        for (int i = n; i > 1; i--) {
            res *= i;
        }
        return res;
    }

    int factorialRecursivo(int n) {
        // COMPLETAR
        if (n == 1 || n == 0) {
            return 1;
        }
        else {
            return n * factorialRecursivo(n - 1);
        }
    }

    boolean esPrimo(int n) {
        // COMPLETAR
        if (n == 1 || n == 0) {
            return false;
        }
        for (int i = n-1; i > 1; i--) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    int sumatoria(int[] numeros) {
        // COMPLETAR
        int res = 0;
        for (int x : numeros){
            res += x;
        }
        return res;
    }

    int busqueda(int[] numeros, int buscado) {
        // COMPLETAR
        for (int i = numeros.length - 1; i >= 0; i--){
            if (numeros[i] == buscado){
                return i;
            }
        }
        //?
        return -1;
    }

    boolean tienePrimo(int[] numeros) {
        // COMPLETAR
        for (int x : numeros){
            if (esPrimo(x)){
                return true;
            }
        }
        return false;
    }

    boolean todosPares(int[] numeros) {
        // COMPLETAR
        for (int x : numeros){
            if (!esPar(x)){
                return false;
            }
        }
        return true;
    }

    boolean esPrefijo(String s1, String s2) {
        // COMPLETAR
        for (int i = s1.length() - 1; i >= 0; i--){
            if (s1.length() > s2.length() || s1.charAt(i) != s2.charAt(i)){
                return false;
            }
        }
        return true;
    }
    
    boolean esSufijo(String s1, String s2) {
        // COMPLETAR
        for (int i = s1.length() - 1; i >= 0; i--){
            if (s1.length() > s2.length() || s1.charAt(i) != s2.charAt((s2.length() - s1.length()) + i)){
                return false;
            }
        }
        return true;
    }
}
