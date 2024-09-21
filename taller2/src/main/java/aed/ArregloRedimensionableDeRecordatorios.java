package aed;

class ArregloRedimensionableDeRecordatorios {
    private Recordatorio[] a; 

    public ArregloRedimensionableDeRecordatorios() {
        a = new Recordatorio[0];
    }

    public int longitud() {
        return a.length;
    }

    public void agregarAtras(Recordatorio i) {
        agregaAtras(i);
    }

    private void agregaAtras(Recordatorio i) {
        Recordatorio[] r = new Recordatorio[a.length + 1];
        r[a.length] = i;

        for (int j = 0; j < a.length; j++) {
            r[j] = a[j];
        } 

        a = r;
    }

    public Recordatorio obtener(int i) {
        return a[i];
    }

    public void quitarAtras() {
        Recordatorio[] r = new Recordatorio[a.length - 1];
        for (int j = 0; j < a.length - 1; j++) {
            r[j] = a[j];
        }
        a = r;
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        a[indice] = valor;
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        Recordatorio[] r = new Recordatorio[vector.longitud()];
        for (int j = 0; j < vector.longitud(); j ++) {
            r[j] = vector.obtener(j);
        }
        a = r;
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        ArregloRedimensionableDeRecordatorios r = new ArregloRedimensionableDeRecordatorios();
        for (int j = 0; j < a.length; j ++) {
            r.agregaAtras(a[j]);
        }
        return r;
    }
}
