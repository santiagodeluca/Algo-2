package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo primero;
    private Nodo ultimo;

    private class Nodo {
        T valor;
        Nodo sig;
        Nodo ant;

        Nodo(T v) {
            valor = v;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
    }

    public int longitud() {
        Nodo actual = primero;
        int l = 0;
        while(actual != null) {
            actual = actual.sig;
            l += 1;
        }
        return l;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        nuevo.sig = primero;
        nuevo.ant = null;
        primero = nuevo;
        if (nuevo.sig == null){
            ultimo = nuevo;
        } else {
            nuevo.sig.ant = nuevo;
        }
    }
    
    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        nuevo.ant = ultimo;
        nuevo.sig = null;
        ultimo = nuevo;
        if(nuevo.ant == null){
            primero = nuevo;
        } else {
            nuevo.ant.sig = nuevo;
        }
    }

    public T obtener(int i) {
        Nodo actual = primero;
        for(int j = 0; j < i; j++) {
            actual = actual.sig;
        }
        return actual.valor;
    }

    public void eliminar(int i) {
        Nodo actual = primero;
        for(int j = 0; j < i; j++){
            actual = actual.sig;
        }
        if(this.longitud() == 1){
            primero = null;
            ultimo = null;
        } else if(actual.sig == null){
            actual.ant.sig = null;
            ultimo = actual.ant;
        } else if(actual.ant == null){
            actual.sig.ant = null;
            primero = actual.sig;
        } else{            
            actual.ant.sig = actual.sig;
            actual.sig.ant = actual.ant;
        }
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        for (int j = 0; j < indice; j++) {
            actual = actual.sig;
        }
        actual.valor = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        for (int i = 0; i < lista.longitud(); i++){
            this.agregarAtras(lista.obtener(i));
        }
    }
    
    @Override
    public String toString() {
        String res = "[" + this.obtener(0) + ',';
        for (int i = 1; i < this.longitud() - 1; i++){
            res = res + " " + this.obtener(i) + ",";
        }
        res = res + ' ' + this.obtener(this.longitud() - 1) + ']';
        return res;
    }

    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados
        private int indice;
        ListaIterador(){
            indice = 0;
        }
        
        public boolean haySiguiente() {
            return indice != longitud();
        }
        
        public boolean hayAnterior() {
	        return indice != 0;
        }

        public T siguiente() {
            if(indice != longitud()){
                indice++;
                return obtener(indice-1);
            } else {
                return obtener(indice);
            }
        }
        

        public T anterior() {
            if(indice != 0){
                indice--;
                return obtener(indice);
            } else {
                return obtener(0);
            }
        }
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }

}
