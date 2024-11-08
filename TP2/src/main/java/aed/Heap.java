package aed;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<T> datos;
    private Comparator<T> comparador;
    private int cantidad;

    public Heap(Comparator<T> comparador) {
        datos = new ArrayList<>();
        this.comparador = comparador;
    }

    public Heap(T[] datos, Comparator<T> comparador) {
        this.datos = heapify(datos, comparador);
        this.comparador = comparador;
    }

    private ArrayList<T> heapify(T[] datos, Comparator<T> comparador) {
        throw new NotImplementedException();
    }

    public void agregar(T elemento) {
        if (cantidad >= datos.size()) {
            datos.add(elemento);
        }
        else {
            datos.set(cantidad,elemento);
        }
        subir(cantidad);
        cantidad++;
    }

    public T primero() {
        return datos.get(0);
    }

    public void eliminar() {
        throw new NotImplementedException();
    }

    private void subir(int indice) {
        if (indice == 0) return;
        int indicePadre = indicePadre(indice);
        T elemento = datos.get(indice);
        T padre = datos.get(indicePadre);
        if (comparador.compare(elemento,padre) > 0 ) {
            intercambiar(indice,indicePadre);
        }
    }

    private void bajar(int indice) {
        int indiceIzquierdo = indiceIzquierdo(indice);
        int indiceDerecho = indiceDerecho(indice);

        if (!enRango(indiceIzquierdo)) return;

        T elemento = datos.get(indice);
        T hijoIzquierdo = datos.get(indiceIzquierdo);
        T hijoDerecho = datos.get(indiceDerecho);

        if (enRango(indiceDerecho) && esMenor(hijoIzquierdo,hijoDerecho) && esMenor(elemento,hijoDerecho)) {
            intercambiar(indice,indiceDerecho);
            bajar(indiceDerecho);
            return;
        }
        if (esMenor(elemento,hijoIzquierdo)) {
            intercambiar(indice,indiceIzquierdo);
            bajar(indiceIzquierdo);
        }
    }

    private boolean enRango(int indice) {
        return indice < cantidad;
    }

    private boolean esMenor(T t1, T t2) {
        return comparador.compare(t1,t2) < 0;
    }

    private int indicePadre(int index) {
        return index / 2;
    }

    private int indiceIzquierdo(int index) {
        return (2 * index) + 1;
    }

    private int indiceDerecho(int index) {
        return (2 * index) + 2;
    }

    private void intercambiar(int index1, int index2) {
        T temp = datos.get(index1);
        datos.set(index1, datos.get(index2));
        datos.set(index2,temp);
    }
}
