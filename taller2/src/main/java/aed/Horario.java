package aed;

public class Horario {
    private int h;
    private int min;

    public Horario(int hora, int minutos) {
        h = hora;
        min = minutos; 
    }

    public int hora() {
        return h;
    }

    public int minutos() {
        return min;
    }

    @Override
    public String toString() {
        return h + ":" + min;
    }

    @Override
    public boolean equals(Object otro) {
        boolean otroEsNull = (otro == null);
        boolean claseDistinta = otro.getClass() != this.getClass();

        if (otroEsNull || claseDistinta) {
        return false;
        }

        Horario otroHorario = (Horario) otro;
        return otroHorario.h == h && otroHorario.min == min;
    }

}
