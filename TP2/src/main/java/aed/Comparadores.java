package aed;

import java.util.Comparator;

public class Comparadores {

    public Comparator<Ciudad> comparadorSuperavit = Comparator
        .comparing(Ciudad::superavit)
        .thenComparing(Ciudad::getId, Comparator.reverseOrder());

    public Comparator<Ciudad> comparadorId = Comparator
        .comparing(Ciudad::getId)
        .reversed();

    public Comparator<Traslado> comparadorRedituables = Comparator
        .comparing(Traslado::getGananciaNeta) 
        .thenComparing(Traslado::getId, Comparator.reverseOrder());       
            
    public Comparator<Traslado> comparadoraAntiguo = Comparator
        .comparing(Traslado::getTimestamp)
        .reversed();
}