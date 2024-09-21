package aed;

public class Recordatorio {
    private Fecha f;
    private Horario h; 
    private String e;

    public Recordatorio(String mensaje, Fecha fecha, Horario horario) {
        e = mensaje;
        f = new Fecha(fecha);
        h = horario;
    }

    public Horario horario() {
        return h;
    }

    public Fecha fecha() {
        return new Fecha(f);
    }

    public String mensaje() {
        return e;
    }

    @Override
    public String toString() {
        return e + " " + "@" + " " + f + " " + h;
    }

    @Override
    public boolean equals(Object otro) {
        boolean otroEsNull = (otro == null);
        boolean claseDistinta = otro.getClass() != this.getClass();

        if (otroEsNull || claseDistinta) {
        return false;
        }

        Recordatorio otroRecordatorio = (Recordatorio) otro;
        return otroRecordatorio.fecha().equals(f) && otroRecordatorio.horario().equals(h) && otroRecordatorio.mensaje().equals(e);
    }

}
