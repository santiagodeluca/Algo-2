package aed;

public class Fecha {
    private Integer d;
    private Integer m;
    
    public Fecha(int dia, int mes) {
        m = (Integer) mes;
        d = (Integer) dia;
    }

    public Fecha(Fecha fecha) {
        d = fecha.d;
        m = fecha.m;
    }

    public Integer dia() {
        return d;
    }

    public Integer mes() {
        return m;
    }

    @Override
    public String toString() {
        return d + "/" + m;
    }

    @Override
    public boolean equals(Object otra) {
        boolean otroEsNull = (otra == null);
        boolean claseDistinta = otra.getClass() != this.getClass();

        if (otroEsNull || claseDistinta) {
        return false;
        }

        Fecha otraFecha = (Fecha) otra;
        return otraFecha.d == d && otraFecha.m == m;
    }

    public void incrementarDia() {
        if (d + 1 > diasEnMes(m) && m == 12) {
            d = 1;
            m = 1;
        } else if (d + 1 > diasEnMes(m) && m != 12) {
            d = 1;
            m += 1;
        } else {
            d += 1;
        }
    }

    private int diasEnMes(int mes) {
        int dias[] = {
                // ene, feb, mar, abr, may, jun
                31, 28, 31, 30, 31, 30,
                // jul, ago, sep, oct, nov, dic
                31, 31, 30, 31, 30, 31
        };
        return dias[mes - 1];
    }

}
