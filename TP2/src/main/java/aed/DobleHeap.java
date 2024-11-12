package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class DobleHeap<T> {
    private Comparator<T> comparador1;
    private Comparator<T> comparador2;
    private ArrayList<Handle> heap1;
    private ArrayList<Handle> heap2;
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

    public DobleHeap(Comparator<T> comparador1, Comparator<T> comparador2) { // O(1)
        this.comparador1 = comparador1;
        this.comparador2 = comparador2;
        this.cantidad = 0;

        heap1 = new ArrayList<>();
        heap2 = new ArrayList<>();
    }
    
    public void heapify(T[] elementos) { // O(n)
        for (int i = 0; i < elementos.length; i++) { // O(n)
            T elemento = elementos[i];
            Handle handle1 = new Handle(elemento, i);
            Handle handle2 = new Handle(elemento, i);
            handle1.otro = handle2;
            handle2.otro = handle1;
            heap1.add(handle1);
            heap2.add(handle2);
        }
        cantidad = elementos.length;
        heapifyUnHeap(heap1, comparador1); // O(n)
        heapifyUnHeap(heap2, comparador2); // O(n)
    }

    // heapifyUnHeap O(n) por ser el algoritmo de floyd :p
    private void heapifyUnHeap(ArrayList<Handle> heap, Comparator<T> comparador){ 
        for (int i = (int) Math.pow(2, (int) (Math.log(heap.size()) / Math.log(2))) - 2; i >= 0; i--) { // es la cuenta que hacemos para obtener i es para encontrar el ultimo elemento del penultimo nivel del heap
            bajar(heap, i, comparador);
        }
    }

    public boolean esVacio() { // O(1)
        return cantidad == 0;
    }

    public T obtenerPosicion(int numeroHeap, int posicion) { // O(1)
        T res;
        if (numeroHeap == 0) {
            res = heap1.get(posicion).valor;
        } else {
            res = heap2.get(posicion).valor;
        }
        return res;
    }

    public void modificarPosicion(int numeroHeap, int posicion, T valor) { // O(log n)
        if (numeroHeap == 0) {
            Handle handle = heap1.get(posicion);
            handle.valor = valor;
            Handle otro = handle.otro;
            otro.valor = valor;

            reordenarNodo(heap1, posicion, comparador1); // O(log n)
            reordenarNodo(heap2, otro.indice, comparador2); // O(log n)
        } else {
            Handle handle = heap2.get(posicion);
            handle.valor = valor;
            Handle otro = handle.otro;
            otro.valor = valor;

            reordenarNodo(heap2, posicion, comparador2); // O(log n)
            reordenarNodo(heap1, otro.indice, comparador1); // O(log n)
        }
    }

    private void reordenarNodo(ArrayList<Handle> heap, int posicion, Comparator<T> comparador) {  // O(log n)
        if (hayHijoConMasPrioridad(heap, posicion, comparador)) {
            bajar(heap, posicion, comparador); // O(log n)
        } else if (esMenor(heap.get(indicePadre(posicion)), heap.get(posicion), comparador)) {
            subir(heap, posicion, comparador); // O(log n)
        }
    }

    private boolean hayHijoConMasPrioridad(ArrayList<Handle> heap, int posicion, Comparator<T> comparador) { // O(1)
        return (enRango(indiceIzquierdo(posicion)) && esMenor(heap.get(posicion), heap.get(indiceIzquierdo(posicion)), comparador)) ||
               (enRango(indiceDerecho(posicion)) && esMenor(heap.get(posicion), heap.get(indiceDerecho(posicion)), comparador));
    }

    public T tope(int indiceHeap) { // O(1)
        T res;
        if (indiceHeap == 0) {
            res = heap1.get(0).valor; // O(1)
        }
        else {
            res = heap2.get(1).valor; // O(1)
        }
        return res;
    }

    public void encolar(T elemento) { // O(log n)
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
        subir(heap1,cantidad,comparador1); // O(log n)
        subir(heap2,cantidad,comparador2); // O(log n)
        cantidad++; 
    }

    public T desencolarMax(int indiceComparador) { // O(log n)
        T res;
        if (indiceComparador == 0) { 
            res = heap1.get(0).valor; // O(1)
            sacarSincronizado(heap1,heap2,0,comparador1,comparador2);  // O(log n)
        }
        else { 
            res = heap2.get(0).valor; // O(1)
            sacarSincronizado(heap2,heap1,0,comparador2,comparador1);  // O(log n)
        }
        return res;
    }

    private void sacarSincronizado(ArrayList<Handle> heapPrincipal, ArrayList<Handle> heapSincronizado, 
            int indice, Comparator<T> comparador1, Comparator<T> comparador2) { // O(log n)

        cantidad--; // O(1)
        int otroIndice = heapPrincipal.get(indice).otro.indice; // O(1)
        sacar(heapPrincipal,indice,comparador1); // O(log n)
        sacar(heapSincronizado,otroIndice,comparador2); // O(log n)
    }

    private void sacar(ArrayList<Handle> heap, int indice, Comparator<T> comparador) { // O(log n)
        intercambiar(heap,indice,cantidad); // O(1)
        bajar(heap,indice,comparador); // O(log n)
    }

    // subir es O(log n) porque a lo sumo hay que moverse una hoja hasta la raiz, eso depende de la altura del heap que es log(n)
    private void subir(ArrayList<Handle> heap, int indice, Comparator<T> comparador) {
        int indicePadre = indicePadre(indice);
        Handle elemento = heap.get(indice);
        Handle padre = heap.get(indicePadre);
        if (indice != 0 && esMenor(padre,elemento,comparador)) {
            intercambiar(heap,indice,indicePadre);
            subir(heap,indicePadre,comparador);
        }
    }

    // bajar es O(log n) porque a lo sumo hay que moverse desde la raiz hasta una hoja, eso depende de la altura del heap que es log(n)
    private void bajar(ArrayList<Handle> heap, int indice, Comparator<T> comparador) {  
         if (hayHijoConMasPrioridad(heap, indice, comparador)) {
            Handle hijoAIntercambiar = hijoConMasPrioridad(heap, indice, comparador);
            int indiceHijoAIntercambiar = hijoAIntercambiar.indice;

            intercambiar(heap,indice,hijoAIntercambiar.indice);
            bajar(heap,indiceHijoAIntercambiar,comparador);
        }
    }

    // tiene como requiere que hay un hijo con mas prioridad
    private Handle hijoConMasPrioridad(ArrayList<Handle> heap, int indice, Comparator<T> comparador) { // O(1)
        Handle res;
        int der = indiceDerecho(indice);
        int izq = indiceIzquierdo(indice);
        if (enRango(der) && enRango(izq)) {
            if (esMenor(heap.get(der),heap.get(izq),comparador)) {
                res = heap.get(izq);
            } else {
                res = heap.get(der);
            }
        } else {
            res = heap.get(izq);
        }
        return res;
    }
    
    private boolean esMenor(Handle t1, Handle t2, Comparator<T> comparador) { // O(1)
        return comparador.compare(t1.valor,t2.valor) < 0;
    }

    private boolean fueraDeRango(int indice) { return !enRango(indice); } // O(1)

    private boolean enRango(int indice) { return indice < cantidad; } // O(1)

    private int indicePadre(int indice) { return indice / 2; } // O(1)

    private int indiceIzquierdo(int indice) { return (2 * indice) + 1; } // O(1)

    private int indiceDerecho(int indice) { return (2 * indice) + 2; } // O(1)

    private void intercambiar(ArrayList<Handle> heap,int indice1, int indice2) { // O(1)
        Handle handle1 = heap.get(indice1);
        Handle handle2 = heap.get(indice2);
        handle1.indice = indice2;
        handle2.indice = indice1;
        heap.set(indice1, handle2);
        heap.set(indice2, handle1);
    }
}
