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
        // Implementar
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        // Implementar
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        // Implementar
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        // Implementar
        return null;
    }
}
