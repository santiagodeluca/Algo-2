package aed;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
        
    private Nodo raiz;
    private int cardinal;
    private int altura;
        
        private class Nodo {
            // Agregar atributos privados del Nodo
            T valor;
            Nodo izq;
            Nodo der;
            Nodo padre;

            Nodo(T v) {
                valor = v;
                izq = null;
                der = null;
                padre = null;
            }
        // Crear Constructor del nodo
    }

    public ABB() {
        raiz = null;
        cardinal = 0;
        altura = 0;
    }

    public int cardinal() {
        return cardinal;
    }

    public T minimo(){
        Nodo actual = raiz;
        while (actual.izq != null) {
            actual = actual.izq;
        }
        return actual.valor;
    }

    public T maximo(){
        Nodo actual = raiz;
        while (actual.der != null) {
            actual = actual.der;
        }
        return actual.valor;
    }

    public void insertar(T elem){
        Nodo nuevo = new Nodo(elem);
        
        if (raiz == null){
            raiz = nuevo;
            cardinal += 1;
            altura += 1;
        } else if (!pertenece(elem)){
            Nodo cercano = masCercano(elem);
            nuevo.padre = cercano;
            if (elem.compareTo(cercano.valor) > 0){
                cercano.der = nuevo;
                altura += 1;
            } else if (elem.compareTo(cercano.valor) < 0){
                cercano.izq = nuevo;
                altura += 1;
            }
            cardinal += 1;
        }
    }

    private Nodo masCercano(T elem){
        Nodo actual = raiz;
        while (true) {
            if (elem.compareTo(actual.valor) > 0 && actual.der == null){
                return actual;
            } else if (elem.compareTo(actual.valor) < 0 && actual.izq == null) {
                return actual;
            } else if (elem.compareTo(actual.valor) == 0) {
                return actual;
            }else if (elem.compareTo(actual.valor) > 0){
                actual = actual.der;
            } else if (elem.compareTo(actual.valor) < 0){
                actual = actual.izq;
            }
        }
    }

    public boolean pertenece(T elem){
        Nodo actual = raiz;
        Boolean res = false;
        while (actual != null) {
            if (actual.valor.compareTo(elem) == 0){
                res = true;
                actual = null;
            } else if (elem.compareTo(actual.valor) > 0){
                actual = actual.der;
            } else {
                actual = actual.izq;
            }
        }
        return res;
    }

    public void eliminar(T elem){
        if (pertenece(elem) && cardinal > 1){
            Nodo objetivo = masCercano(elem);
            
            if (objetivo.der == null && objetivo.izq == null){
                eliminaHoja(objetivo);
            } else if (objetivo.der == null && objetivo.izq != null){
                eliminaNodoUnicoHijo(objetivo, -1);
            } else if (objetivo.izq == null && objetivo.der != null){
                eliminaNodoUnicoHijo(objetivo, 1);
            } else {
                eliminaNodoConDosHijos(objetivo);
            }
        } else if (pertenece(elem) && cardinal == 1){
            cardinal -= 1;
            raiz = null; 
            altura = 0;
        }
    }
    
    private void eliminaHoja(Nodo objetivo){
        if (objetivo.valor.compareTo(objetivo.padre.valor) > 0){
            objetivo.padre.der = null;
        } else if (objetivo.valor.compareTo(objetivo.padre.valor) < 0){
            objetivo.padre.izq = null;
        }

        cardinal -= 1;
        
    }

    private void eliminaNodoUnicoHijo(Nodo objetivo, int lado){
        if (objetivo.padre != null){
            if (objetivo.valor.compareTo(objetivo.padre.valor) > 0 && lado == 1){
                objetivo.padre.der = objetivo.der;
                objetivo.der.padre = objetivo.padre;
            } else if (objetivo.valor.compareTo(objetivo.padre.valor) > 0 && lado == -1){
                objetivo.padre.der = objetivo.izq;
                objetivo.izq.padre = objetivo.padre;
            } else if (objetivo.valor.compareTo(objetivo.padre.valor) < 0 && lado == 1){
                objetivo.padre.izq = objetivo.der;
                objetivo.der.padre = objetivo.padre;
            } else if (objetivo.valor.compareTo(objetivo.padre.valor) < 0 && lado == -1){
                objetivo.padre.izq = objetivo.izq;
                objetivo.izq.padre = objetivo.padre;
            }
        } else {
            if (lado == 1){
                raiz = objetivo.der;
            } else {
                raiz = objetivo.izq;
            }
        }

        cardinal -= 1;
        

    }

    private Nodo sucesor(Nodo nodo){
        Nodo res;
        if (nodo.der != null){
            res = nodo.der;
            while (res.izq != null){
                res = res.izq;
            }
        } else {
        res = nodo.padre;
        Nodo hijo = nodo;
        while (res.der.valor == hijo.valor) {
            hijo = res;
            res = res.padre;
        }
        }
    return res;
    }

    private void eliminaNodoConDosHijos(Nodo objetivo){
        Nodo suc = sucesor(objetivo);
        T valorSucesor = suc.valor;
        eliminar(valorSucesor);
        objetivo.valor = valorSucesor;
    }

    public String toString(){
        if (raiz != null){
            String res = "{";
            Nodo primero = masCercano(minimo());
            T max = maximo();
            while (primero.valor.compareTo(max) != 0){
                res = res + primero.valor + ",";
                primero = sucesor(primero);
            }
            res = res + max + "}";
            return res;
        } else {
            return "{}";
        }

    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
