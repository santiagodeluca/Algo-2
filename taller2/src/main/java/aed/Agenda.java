package aed;

public class Agenda {
    private Fecha f;
    private ArregloRedimensionableDeRecordatorios a;

    public Agenda(Fecha fechaActual) {
        f = fechaActual;
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        if (a == null) {
            a = new ArregloRedimensionableDeRecordatorios();
        }
        a.agregarAtras(recordatorio);
    }

    @Override
    public String toString() {
        String s = f + "\n=====\n";
        for (int i = 0; i < a.longitud(); i ++) {
            if (a.obtener(i).fecha().equals(f)) {
                s = s.concat(a.obtener(i).toString() + "\n");
            }
        }
        return s;
    }

    public void incrementarDia() {
        f.incrementarDia();
    }

    public Fecha fechaActual() {
        return f;
    }

}
