package aed;

public class Ciudad {
    int id;
    int ganancia;
    int perdida;

    public void agregarGanancia(int gan) { // O(1)
        ganancia = ganancia + gan;
    }

    public void agregarPerdida(int per) { // O(1)
        perdida = perdida + per;
    }

    public int superavit(){ // O(1)
        return ganancia - perdida;
    }

    public Ciudad(int identificacion, int gan, int per){ // O(1)
        id = identificacion;
        ganancia = gan;
        perdida = per;
    }

    public int getId() { // O(1)
        return id;
    }
    
    public int getGanancia() { // O(1)
        return ganancia;
    }

    public int getPerdida() { // O(1)
        return perdida;
    }
}
