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
        throw new UnsupportedOperationException("No implementada aun");
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        throw new UnsupportedOperationException("No implementada aun");
    }
    
    @Override
    public String toString() {
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados

        public boolean haySiguiente() {
	        throw new UnsupportedOperationException("No implementada aun");
        }
        
        public boolean hayAnterior() {
	        throw new UnsupportedOperationException("No implementada aun");
        }

        public T siguiente() {
	        throw new UnsupportedOperationException("No implementada aun");
        }
        

        public T anterior() {
	        throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
	    throw new UnsupportedOperationException("No implementada aun");
    }

}
