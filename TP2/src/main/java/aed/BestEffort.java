package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    private DobleHeap<Ciudad> ciudades;
    private DobleHeap<Traslado> t;

    private ArrayList<Integer> gananciasMaximas; 
    private ArrayList<Integer> perdidasMaximas; 
    private int trasladosDespachados;
    private int gananciasGlobales;

    public BestEffort(int cantCiudades, Traslado[] traslados){ // O(|C| + |T|)
        trasladosDespachados = 0; // O(1)
        gananciasGlobales = 0; // O(1)
        
        gananciasMaximas = new ArrayList<Integer>(); // O(1)
        perdidasMaximas = new ArrayList<Integer>(); // O(1)
        
        Ciudad[] ciudadesTemp = new Ciudad[cantCiudades]; // O(|C|)

        for (int i = 0; i < cantCiudades; i++) { // O(|C|)
            gananciasMaximas.add(i); // O(1)
            perdidasMaximas.add(i); // O(1)
            Ciudad c = new Ciudad(i,0,0); // O(1)
            ciudadesTemp[i] = c; // O(1)
        }

        Comparator<Ciudad> compSuperavit = new Comparadores().comparadorSuperavit; // O(1)
        Comparator<Ciudad> compId = new Comparadores().comparadorId; // O(1)
        ciudades = new DobleHeap<Ciudad>(compSuperavit, compId); // O(1)
        ciudades.heapify(ciudadesTemp); // O(|C|)

        Comparator<Traslado> compRedituable = new Comparadores().comparadorRedituables; // O(1)
        Comparator<Traslado> compAntiguo = new Comparadores().comparadoraAntiguo; // O(1)
        t = new DobleHeap<Traslado>(compRedituable, compAntiguo); // O(1)
        t.heapify(traslados); // O(|T|)
    }

    public void registrarTraslados(Traslado[] traslados){ // O(|traslados| * log(|T|))
        for (int i = 0; i < traslados.length; i++){ // O(|traslados|)
            Traslado tras = traslados[i]; // O(1)
            t.encolar(tras); // O(log|T|)
        }
    }

    public int[] despacharMasRedituables(int n){ // O(n * (log(|T|) + log(|C|)))
        ArrayList<Integer> temp = new ArrayList<Integer>(); // O(1) usamos un arraylist porque la funcion devuelve un array de ints(que no se redimensiona en O(1))

        for (int i = 0; i < n && !t.esVacio(); i++) { // O(n)
            Traslado tras = t.desencolarMax(0); // O(log|T|)
            temp.add(tras.id); // O(1)

            actualizaCiudadesDespuesDeTraslado(tras); // O(log |C|)
        }
        int[] res = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) { // O(n)
            res[i] = temp.get(i); //O(1)
        }
        return res;
    }
    
    public int[] despacharMasAntiguos(int n){ //O(n * (log(|T|) + log(|C|)))
        ArrayList<Integer> temp = new ArrayList<Integer>(); // O(1)
    
        for (int i = 0; i < n && !t.esVacio(); i++) { // O(n)
            Traslado tras = t.desencolarMax(1); // O(log|T|)
            temp.add(tras.id); // O(1)
    
            actualizaCiudadesDespuesDeTraslado(tras); // O(log |C|)
        }
        int[] res = new int[temp.size()]; // O(n)
        for (int i = 0; i < temp.size(); i++) { // O(n)
            res[i] = temp.get(i); //O(1)
        }
        return res;
    }
    
    private void actualizaCiudadesDespuesDeTraslado(Traslado tras) { // O(log|C|)
        Ciudad ori = ciudades.obtenerPosicion(1, tras.origen); // O(1)
        Ciudad des = ciudades.obtenerPosicion(1, tras.destino);// O(1)
    
        Ciudad origenActualizado = new Ciudad(ori.getId(), ori.getGanancia() + tras.gananciaNeta, ori.getPerdida()); // O(1)
        Ciudad destinoActualizado = new Ciudad(des.getId(), des.getGanancia(), des.getPerdida() + tras.gananciaNeta); // O(1)
    
        ciudades.modificarPosicion(1, ori.getId(), origenActualizado); // O (log|C|)
        ciudades.modificarPosicion(1, des.getId(), destinoActualizado); // O (log|C|)
    
        trasladosDespachados += 1; // O(1)
        gananciasGlobales += tras.gananciaNeta; // O(1)

        Ciudad ciudadMayorGananciaPrevia = ciudades.obtenerPosicion(1, gananciasMaximas.get(0)); // O(1)
        if (origenActualizado.getId() == ciudadMayorGananciaPrevia.getId() || origenActualizado.getGanancia() > ciudadMayorGananciaPrevia.getGanancia()) {
            gananciasMaximas = new ArrayList<Integer>(); // O(1)
            gananciasMaximas.add(origenActualizado.getId()); // O(1)
        } else if (origenActualizado.getGanancia() == ciudadMayorGananciaPrevia.getGanancia()) {
            gananciasMaximas.add(origenActualizado.getId()); // O(1)
        }

        Ciudad ciudadMayorPerdidaPrevia = ciudades.obtenerPosicion(1, perdidasMaximas.get(0)); // O(1)
        if (destinoActualizado.getId() == ciudadMayorPerdidaPrevia.getId() || destinoActualizado.getPerdida() > ciudadMayorPerdidaPrevia.getPerdida()) {
            perdidasMaximas = new ArrayList<Integer>(); // O(1)
            perdidasMaximas.add(destinoActualizado.getId()); // O(1)
        } else if (destinoActualizado.getPerdida() == ciudadMayorPerdidaPrevia.getPerdida()) {
            perdidasMaximas.add(destinoActualizado.getId()); // O(1)
        }
    }

    public int ciudadConMayorSuperavit(){ // O(1)
        return ciudades.tope(0).getId(); // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){ // O(1)
        return gananciasMaximas;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){ // O(1)
        return perdidasMaximas;
    }

    public int gananciaPromedioPorTraslado(){ // O(1)
        return (gananciasGlobales / trasladosDespachados);
    }
}
