package aed;

class Funciones {
    int cuadrado(int x) {
        return x * x;
    }

    double distancia(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }

    boolean esPar(int n) {
        return n % 2 == 0;
    }

    boolean esBisiesto(int n) {
        return ((n % 4 == 0) && (n % 100 != 0)) || n % 400 == 0;
    }

    int factorialIterativo(int n) {
        int res = 1;
        for (int i = n; i > 1; i--) {
            res *= i;
        }
        return res;
    }

    int factorialRecursivo(int n) {
        if (n == 1 || n == 0) {
            return 1;
        }
        else {
            return n * factorialRecursivo(n - 1);
        }
    }

    boolean esPrimo(int n) {
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
        int res = 0;
        for (int x : numeros){
            res += x;
        }
        return res;
    }

    int busqueda(int[] numeros, int buscado) {
        for (int i = numeros.length - 1; i >= 0; i--){
            if (numeros[i] == buscado){
                return i;
            }
        }
        //La especificación no aclara qué hacer en el caso que incumple el requiere (lo cual está bien), pero como java exige algún return elijo -1.
        return -1;
    }

    boolean tienePrimo(int[] numeros) {
        for (int x : numeros){
            if (esPrimo(x)){
                return true;
            }
        }
        return false;
    }

    boolean todosPares(int[] numeros) {
        for (int x : numeros){
            if (!esPar(x)){
                return false;
            }
        }
        return true;
    }

    boolean esPrefijo(String s1, String s2) {
        for (int i = s1.length() - 1; i >= 0; i--){
            if (s1.length() > s2.length() || s1.charAt(i) != s2.charAt(i)){
                return false;
            }
        }
        return true;
    }
    
    boolean esSufijo(String s1, String s2) {
        for (int i = s1.length() - 1; i >= 0; i--){
            if (s1.length() > s2.length() || s1.charAt(i) != s2.charAt((s2.length() - s1.length()) + i)){
                return false;
            }
        }
        return true;
    }
}
