package aed;

public class Ciudad {
    int id;
    int ganancia;
    int perdida;

    public void agregarGanancia(int gan) {
        ganancia = ganancia + gan;
    }

    public void agregarPerdida(int per) {
        perdida = perdida + per;
    }

    public int superavit(){
        return ganancia - perdida;
    }

    public Ciudad(int identificacion, int gan, int per){
        id = identificacion;
        ganancia = gan;
        perdida = per;
    }

    public int getId() {
        return id;
    }
    
    public int getGanacia() {
        return ganancia;
    }

    public int getPerdida() {
        return perdida;
    }
}
