package aed;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DobleHeap<T> {
    private Comparator<T> comparador1;
    private Comparator<T> comparador2;
    private List<Handle> heap1;
    private List<Handle> heap2;
    private int cantidad;

    private class Handle {
        T valor;
        int indice;
        Handle otro;
        Handle(T value, int indice) {
            this.valor = value;
            this.indice = indice;
        }
    }

    public DobleHeap(Comparator<T> comparador1, Comparator<T> comparador2) {
        this.comparador1 = comparador1;
        this.comparador2 = comparador2;
        this.cantidad = 0;

        heap1 = new ArrayList<>();
        heap2 = new ArrayList<>();
    }
    
    public void heapify(T[] elementos) {
        for (int i = 0; i < elementos.length; i++) {
            T elemento = elementos[i];
            Handle handle1 = new Handle(elemento, i);
            Handle handle2 = new Handle(elemento, i);
            handle1.otro = handle2;
            handle2.otro = handle1;
            heap1.add(handle1);
            heap2.add(handle2);
        }
        cantidad = elementos.length;
        heapifyUnHeap(heap1, comparador1);
        heapifyUnHeap(heap2, comparador2);
    }

    private void heapifyUnHeap(List<Handle> heap, Comparator<T> comparador){ // O(n) por ser el algoritmo de floyd :p
        for (int i = heap.size() - 1; i >= 0; i--) {
            bajar(heap, i, comparador);
        }
    }

    public boolean esVacio() {
        return cantidad == 0;
    }

    public T obtenerPosicion(int numeroHeap, int posicion) {
        if (numeroHeap == 0) {
            return heap1.get(posicion).valor;
        } else {
            return heap2.get(posicion).valor;
        }
    }

    public void modificarPosicion(int numeroHeap, int posicion, T valor) {
        if (numeroHeap == 0) {
            Handle handle = heap1.get(posicion);
            handle.valor = valor;
            Handle otro = handle.otro;
            otro.valor = valor;

            reordenarNodo(heap1, posicion, comparador1);
            reordenarNodo(heap2, otro.indice, comparador2);
        } else {
            Handle handle = heap2.get(posicion);
            handle.valor = valor;
            Handle otro = handle.otro;
            otro.valor = valor;

            reordenarNodo(heap2, posicion, comparador2);
            reordenarNodo(heap1, otro.indice, comparador1);
        }
    }

    private void reordenarNodo(List<Handle> heap, int posicion, Comparator<T> comparador) {
        if (hayHijoConMasPrioridad(heap, posicion, comparador)) {
            bajar(heap, posicion, comparador);
        } else if (esMenor(heap.get(indicePadre(posicion)), heap.get(posicion), comparador)) {
            subir(heap, posicion, comparador);
        }
    }

    private boolean hayHijoConMasPrioridad(List<Handle> heap, int posicion, Comparator<T> comparador) {
        return (enRango(indiceIzquierdo(posicion)) && esMenor(heap.get(posicion), heap.get(indiceIzquierdo(posicion)), comparador)) ||
               (enRango(indiceDerecho(posicion)) && esMenor(heap.get(posicion), heap.get(indiceDerecho(posicion)), comparador));
    }

    public T tope(int indiceHeap) {
        if (indiceHeap == 0) {
            return heap1.get(0).valor;
        }
        else {
            return heap2.get(1).valor;
        }
    }

    public void encolar(T elemento) {
        Handle handle1 = new Handle(elemento, cantidad);
        Handle handle2 = new Handle(elemento, cantidad);
        handle1.otro = handle2;
        handle2.otro = handle1;
        if (cantidad == heap1.size()) {
            heap1.add(handle1);
            heap2.add(handle2);
        }
        else {
            heap1.set(cantidad,handle1);
            heap2.set(cantidad,handle2);
        }
        subir(heap1,cantidad,comparador1);
        subir(heap2,cantidad,comparador2);
        cantidad++;
    }

    public T desencolarMax(int indiceComparador) {
        if (indiceComparador == 0) { 
            T res = heap1.get(0).valor;
            sacarSincronizado(heap1,heap2,0,comparador1,comparador2); 
            return res;
        }
        else { 
            T res = heap2.get(0).valor ;
            sacarSincronizado(heap2,heap1,0,comparador2,comparador1); 
            return res;
        }
    }

    private void sacarSincronizado(List<Handle> heapPrincipal, List<Handle> heapSincronizado, 
            int indice, Comparator<T> comparador1, Comparator<T> comparador2) {

        cantidad--;
        int otroIndice = heapPrincipal.get(indice).otro.indice;
        sacar(heapPrincipal,indice,comparador1);
        sacar(heapSincronizado,otroIndice,comparador2);
    }

    private void sacar(List<Handle> heap, int indice, Comparator<T> comparador) {
        intercambiar(heap,indice,cantidad);
        bajar(heap,indice,comparador);
    }

    private void subir(List<Handle> heap, int indice, Comparator<T> comparador) {
        if (indice == 0) return;
        int indicePadre = indicePadre(indice);
        Handle elemento = heap.get(indice);
        Handle padre = heap.get(indicePadre);
        if (esMenor(padre,elemento,comparador)) {
            intercambiar(heap,indice,indicePadre);
            subir(heap,indicePadre,comparador);
        }
    }

    private void bajar(List<Handle> heap, int indice, Comparator<T> comparador) {
        /* if (hayHijoConMasPrioridad(heap, indice, comparador)) {
            Handle hijoAIntercambiar = hijoConMasPrioridad(heap, indice, comparador);
            int indiceHijoAIntercambiar = hijoAIntercambiar.indice;
            intercambiar(heap,indice,hijoAIntercambiar.indice);
            bajar(heap,indiceHijoAIntercambiar,comparador);
        }
        */
        
        int indiceIzquierdo = indiceIzquierdo(indice);
        int indiceDerecho = indiceDerecho(indice);

        if (fueraDeRango(indiceIzquierdo)) return;
        
        Handle elemento = heap.get(indice);
        Handle hijoIzquierdo = heap.get(indiceIzquierdo);

        if (enRango(indiceDerecho)) {
            Handle hijoDerecho = heap.get(indiceDerecho);
            if (esMenor(hijoIzquierdo,hijoDerecho,comparador) && esMenor(elemento,hijoDerecho,comparador)) {
                intercambiar(heap,indice,indiceDerecho);
                bajar(heap,indiceDerecho,comparador);
            } else if (esMenor(elemento,hijoIzquierdo,comparador)) {
                intercambiar(heap,indice,indiceIzquierdo);
                bajar(heap,indiceIzquierdo,comparador);

            }
        } else if (esMenor(elemento,hijoIzquierdo,comparador)) {
            intercambiar(heap,indice,indiceIzquierdo);
            bajar(heap,indiceIzquierdo,comparador);
        }
        

    }

    private Handle hijoConMasPrioridad(List<Handle> heap, int indice, Comparator<T> comparador) {
        int der = indiceDerecho(indice);
        int izq = indiceIzquierdo(indice);
        if (enRango(der) && enRango(izq)) {
            if (esMenor(heap.get(der),heap.get(izq),comparador)) {
                return heap.get(izq);
            } else {
                return heap.get(der);
            }
        } else {
            return heap.get(izq);
        }
    }
    private boolean esMenor(Handle t1, Handle t2, Comparator<T> comparador) {
        return comparador.compare(t1.valor,t2.valor) < 0;
    }

    private boolean fueraDeRango(int indice) { return !enRango(indice); }

    private boolean enRango(int indice) { return indice < cantidad; }

    private int indicePadre(int indice) { return indice / 2; }

    private int indiceIzquierdo(int indice) { return (2 * indice) + 1; }

    private int indiceDerecho(int indice) { return (2 * indice) + 2; }

    private void intercambiar(List<Handle> heap,int indice1, int indice2) {
        Handle handle1 = heap.get(indice1);
        Handle handle2 = heap.get(indice2);
        handle1.indice = indice2;
        handle2.indice = indice1;
        heap.set(indice1, handle2);
        heap.set(indice2, handle1);
    }
}
