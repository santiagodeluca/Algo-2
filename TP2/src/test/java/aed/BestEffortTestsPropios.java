package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BestEffortTestsPropios {

    int cantCiudades;
    Traslado[] listaTraslados;
    Traslado[] trasladosDesordenados;
    ArrayList<Integer> actual;


    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 1, 100, 10),
                                            new Traslado(2, 0, 1, 400, 20),
                                            new Traslado(3, 3, 4, 500, 50),
                                            new Traslado(4, 4, 3, 500, 11),
                                            new Traslado(5, 1, 0, 1000, 40),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 2000, 42)
                                        };
        trasladosDesordenados = new Traslado[] {
                                            new Traslado(1, 6, 1, 4500, 10),
                                            new Traslado(2, 0, 1, 4000, 5),
                                            new Traslado(3, 0, 6, 1500, 4),
                                            new Traslado(4, 4, 3, 5000, 3),
                                            new Traslado(5, 1, 0, 100, 9),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 4500, 11)
                                        };
                                    }
                                    
        void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
            assertEquals(s1.size(), s2.size());
            for (int e1 : s1) {
            boolean encontrado = false;
                for (int e2 : s2) {
                    if (e1 == e2) encontrado = true;
                }
                assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
            }
        }
        void assertArrayListYArrayEquals(ArrayList<Integer> s1, int[] s2) {
            assertEquals(s1.size(), s2.length);
            for (int e1 : s1) {
            boolean encontrado = false;
                for (int e2 : s2) {
                    if (e1 == e2) encontrado = true;
                }
                assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
            }
        }
    
    @Test
    void nuevo_sistema_sin_traslados(){
        Traslado[] trasladosVacios = new Traslado[] {};
        
        BestEffort sis = new BestEffort(this.cantCiudades, trasladosVacios);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertEquals(0,sis.gananciaPromedioPorTraslado());
        
        sis.despacharMasAntiguos(100);
        sis.despacharMasRedituables(100);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertEquals(0,sis.gananciaPromedioPorTraslado());
        
        // probamos si el sistema funciona por mucho de que fue inicializado sin traslados
        Traslado[] nuevosTraslados = new Traslado[] {
            new Traslado(1, 5, 1, 7000, 10),
            new Traslado(2, 0, 1, 4000, 1),
        };
        
        sis.registrarTraslados(nuevosTraslados);
        
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());
        assertEquals(5, sis.ciudadConMayorSuperavit());
        assertEquals((7000 + 4000)/2,sis.gananciaPromedioPorTraslado());
    }

    @Test
    void despachar_cero(){        
        BestEffort sis = new BestEffort(this.cantCiudades, trasladosDesordenados);

        sis.despacharMasAntiguos(0);
        sis.despacharMasRedituables(0);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertEquals(0,sis.gananciaPromedioPorTraslado());
    }

    @Test
    void despachar_desordenado(){

        BestEffort sis = new BestEffort(this.cantCiudades, trasladosDesordenados);

        sis.despacharMasRedituables(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
        sis.despacharMasAntiguos(6);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_devolviendo_ID_correcto(){
        BestEffort sis = new BestEffort(this.cantCiudades, trasladosDesordenados);

        assertArrayListYArrayEquals(new ArrayList<>(Arrays.asList(4)), sis.despacharMasRedituables(1));
        assertArrayListYArrayEquals(new ArrayList<>(Arrays.asList(3,2,5)), sis.despacharMasAntiguos(3));
        assertArrayListYArrayEquals(new ArrayList<>(Arrays.asList(1,7,6)), sis.despacharMasRedituables(100));

    }
    
    @Test
    void registrar_traslados_mas_antiguos_y_redituables(){

        Traslado[] nuevosTraslados = new Traslado[] {
            new Traslado(8, 5, 1, 7000, 10),
            new Traslado(9, 0, 1, 4000, 1),
        };
        
        BestEffort sis = new BestEffort(this.cantCiudades, trasladosDesordenados);
        
        sis.registrarTraslados(nuevosTraslados);
        
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());
    }
    @Test
    void registrar_traslados_vacios(){
                
        Traslado[] trasladosVacios = new Traslado[] {};
        
        BestEffort sis = new BestEffort(this.cantCiudades, trasladosDesordenados);
        
        sis.registrarTraslados(trasladosVacios);
        
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(2);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void promedio_por_traslado(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 1, 2, 1452, 5),
            new Traslado(9, 1, 2, 334, 2),
            new Traslado(10, 1, 2, 24, 3),
            new Traslado(11, 1, 2, 333, 4),
            new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());
        

    }

    @Test
    void mayor_superavit(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 1, 2, 30, 2),
            new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(400);
        assertEquals(2, sis.ciudadConMayorSuperavit());

    }
}
