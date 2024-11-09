package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    //Completar atributos privados
    private DobleHeap<Ciudad> ciudades;
    private DobleHeap<Traslado> t;

    private ArrayList<Integer> gananciasMaximas; 
    private ArrayList<Integer> perdidasMaximas; 
    private int trasladosDespachados;
    private int gananciasGlobales;

    public BestEffort(int cantCiudades, Traslado[] traslados){ // O(|C| + |T|)
        trasladosDespachados = 0; 
        gananciasGlobales = 0;
        
        gananciasMaximas = new ArrayList<Integer>();
        perdidasMaximas = new ArrayList<Integer>();
        
        Ciudad[] ciudadesTemp = new Ciudad[cantCiudades];

        for (int i = 0; i < cantCiudades; i++) { //O(|C|)
            gananciasMaximas.add(i); // O(1)
            perdidasMaximas.add(i); // O(1)
            Ciudad c = new Ciudad(i,0,0); // O(1)
            ciudadesTemp[i] = c; // O(1)
        }

        Comparator<Ciudad> compSuperavit = new Comparadores().comparadorSuperavit; 

        Comparator<Ciudad> compId = new Comparadores().comparadorId; 
        
        ciudades = new DobleHeap<Ciudad>(compSuperavit, compId); // O(1)
        ciudades.heapify(ciudadesTemp); // O(|C|)

        Comparator<Traslado> compRedituable = new Comparadores().comparadorRedituables; 

        Comparator<Traslado> compAntiguo = new Comparadores().comparadoraAntiguo; 


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
        ArrayList<Integer> temp = new ArrayList<Integer>();

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
    
    public int[] despacharMasAntiguos(int n){ //O(n (log(|T|) + log(|C|)))
        ArrayList<Integer> temp = new ArrayList<Integer>();
    
        for (int i = 0; i < n && !t.esVacio(); i++) { // O(n)
            Traslado tras = t.desencolarMax(1); // O(log|T|)
            temp.add(tras.id); // O(1)
    
            actualizaCiudadesDespuesDeTraslado(tras); // O(log |C|)
        }
        int[] res = new int[temp.size()]; // O(n)
        for (int i = temp.size() - 1; i >= 0; i--) { // O(n)
            res[i] = temp.get(i); //O(1)
        }
        return res;
    }
    
    private void actualizaCiudadesDespuesDeTraslado(Traslado tras) { // O(log|C|)
        Ciudad ori = ciudades.obtenerPosicion(1, tras.origen); // O(log|C|)
        Ciudad des = ciudades.obtenerPosicion(1, tras.destino);// O(log|C|)
    
        Ciudad origenActualizado = new Ciudad(ori.getId(), ori.getGanancia() + tras.gananciaNeta, ori.getPerdida());
        Ciudad destinoActualizado = new Ciudad(des.getId(), des.getGanancia(), des.getPerdida() + tras.gananciaNeta);
    
        ciudades.modificarPosicion(1, ori.getId(), origenActualizado); // O (log|C|)
        ciudades.modificarPosicion(1, des.getId(), destinoActualizado); // O (log|C|)
    
        trasladosDespachados += 1;
        gananciasGlobales += tras.gananciaNeta;

        Ciudad ciudadMayorGananciaPrevia = ciudades.obtenerPosicion(1, gananciasMaximas.get(0));
        if (origenActualizado.getId() == ciudadMayorGananciaPrevia.getId() || origenActualizado.getGanancia() > ciudadMayorGananciaPrevia.getGanancia()) { // O(log |C|)
            gananciasMaximas = new ArrayList<Integer>();
            gananciasMaximas.add(origenActualizado.getId());
        } else if (origenActualizado.getGanancia() == ciudadMayorGananciaPrevia.getGanancia()) { // O(log |C|)
            gananciasMaximas.add(origenActualizado.getId());
        }

        Ciudad ciudadMayorPerdidaPrevia = ciudades.obtenerPosicion(1, perdidasMaximas.get(0));
        if (destinoActualizado.getId() == ciudadMayorPerdidaPrevia.getId() || destinoActualizado.getPerdida() > ciudadMayorPerdidaPrevia.getPerdida()) { // O(log |C|)
            perdidasMaximas = new ArrayList<Integer>();
            perdidasMaximas.add(destinoActualizado.getId());
        } else if (destinoActualizado.getPerdida() == ciudadMayorPerdidaPrevia.getPerdida()) { // O(log |C|)
            perdidasMaximas.add(destinoActualizado.getId());
        }
    }
    public int ciudadConMayorSuperavit(){
        return ciudades.tope(0).superavit(); // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return gananciasMaximas;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return perdidasMaximas;
    }

    public int gananciaPromedioPorTraslado(){
        return (gananciasGlobales / trasladosDespachados);
    }
    
}
