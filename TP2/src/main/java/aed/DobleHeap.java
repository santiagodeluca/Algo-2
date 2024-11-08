package aed;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    public DobleHeap(T[] elementos, Comparator<T> comparador1, Comparator<T> comparador2) {
        this.comparador1 = comparador1;
        this.comparador2 = comparador2;
        this.cantidad = elementos.length;

        heap1 = new ArrayList<>();
        heap2 = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            T elemento = elementos[i];
            Handle handle1 = new Handle(elemento, i);
            Handle handle2 = new Handle(elemento, i);
            handle1.otro = handle2;
            handle2.otro = handle1;
            heap1.add(handle1);
            heap2.add(handle2);
        }

        heapify(heap1);
        heapify(heap2);
    }

    private void heapify(List<Handle> heap) {
        throw new NotImplementedException();
    }

    public void encolar(T elemento) {
        cantidad++; // agregar if y sincronizar handles
        heap1.add(new Handle(elemento, cantidad));
        heap2.add(new Handle(elemento, cantidad));
        subir(heap1,cantidad,comparador1);
        subir(heap2,cantidad,comparador2);
    }

    public void desencolarMax(int indiceComparador) {
        if (indiceComparador == 0) { sacarSincronizado(heap1,heap2,0,comparador1); }
        else { sacarSincronizado(heap2,heap1,0,comparador2); } // devolver el max
    }

    private void sacarSincronizado(List<Handle> heapPrincipal, List<Handle> heapSincronizado, int indice, Comparator<T> comparador) {
        cantidad--;
        int otroIndice = heapPrincipal.get(indice).otro.indice;
        sacar(heapPrincipal,indice,comparador);
        sacar(heapSincronizado,otroIndice,comparador); // agregar otro comparador
    }

    private void sacar(List<Handle> heap, int indice, Comparator<T> comparador) {
        intercambiar(heap,indice,cantidad + 1);
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
        int indiceIzquierdo = indiceIzquierdo(indice);
        int indiceDerecho = indiceDerecho(indice);
        
        if (fueraDeRango(indiceIzquierdo)) return;

        Handle elemento = heap.get(indice);
        Handle hijoIzquierdo = heap.get(indiceIzquierdo);
        Handle hijoDerecho = heap.get(indiceDerecho);
        
        if (enRango(indiceDerecho) && esMenor(hijoIzquierdo,hijoDerecho,comparador) && esMenor(elemento,hijoDerecho,comparador)) {
            intercambiar(heap,indice,indiceDerecho);
            bajar(heap,indiceDerecho,comparador);
        } else if (esMenor(elemento,hijoIzquierdo,comparador)) {
            intercambiar(heap,indice,indiceIzquierdo);
            bajar(heap,indiceIzquierdo,comparador);
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
