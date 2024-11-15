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
        cantCiudades = 7;
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

        // al no haber traslados despachados, las ciudades estan todas empatadas en ganancia y perdida
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());
        
        sis.despacharMasAntiguos(100);
        sis.despacharMasRedituables(100);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());
        
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
    void empatan_redituables(){
        Traslado[] redituableEmpatado = new Traslado[] {
            new Traslado(1, 1, 0, 1000, 5),
            new Traslado(2, 0, 1, 1000, 2),
        };

        BestEffort sis = new BestEffort(this.cantCiudades, redituableEmpatado);

        sis.despacharMasRedituables(1);
        assertEquals(1, sis.ciudadConMayorSuperavit());
    }

    @Test
    void empatan_antiguos(){
        Traslado[] antiguoEmpatado = new Traslado[] {
            new Traslado(1, 1, 0, 2000, 2),
            new Traslado(2, 0, 1, 1000, 2),
        };

        BestEffort sis = new BestEffort(this.cantCiudades, antiguoEmpatado);

        sis.despacharMasAntiguos(1);
        assertEquals(1, sis.ciudadConMayorSuperavit());
    }

    @Test
    void despachar_devolviendo_ID_correcto(){
        BestEffort sis = new BestEffort(this.cantCiudades, trasladosDesordenados);

        assertArrayListYArrayEquals(new ArrayList<>(Arrays.asList(4)), sis.despacharMasRedituables(1));
        assertArrayListYArrayEquals(new ArrayList<>(Arrays.asList(3,2,5)), sis.despacharMasAntiguos(3));
        assertArrayListYArrayEquals(new ArrayList<>(Arrays.asList(1,7,6)), sis.despacharMasRedituables(100));
    }
    
    @Test
    void registrar_nuevos_traslados_mas_antiguos_y_redituables(){

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
    void empate_en_superavit(){
        Traslado[] superavitEmpatados = new Traslado[] {
            new Traslado(1, 1, 0, 1000, 5),
            new Traslado(2, 0, 1, 1000, 2),
        };

        BestEffort sis = new BestEffort(2, superavitEmpatados);

        sis.despacharMasRedituables(2);

        assertEquals(0, sis.ciudadConMayorSuperavit());
    }

    @Test
    void stress(){
        int cantCiudades = 100;
        Traslado[] traslados = new Traslado[100];
        for (int i = 0; i < traslados.length; i++) {
            traslados[i] = new Traslado(i, 0, 99, 100000 - i, 10000 - i);
        }
        BestEffort sis = new BestEffort(cantCiudades, traslados);

        int[] despachados;
        ArrayList<Integer> esperados;

        despachados = sis.despacharMasRedituables(25);
        esperados = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            esperados.add(i);
        }
        
        assertArrayListYArrayEquals(esperados, despachados);
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(99)), sis.ciudadesConMayorPerdida());

        despachados = sis.despacharMasAntiguos(50);
        esperados = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            esperados.add(100 - i - 1);
        }

        assertArrayListYArrayEquals(esperados, despachados);
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(99)), sis.ciudadesConMayorPerdida());
    }
}
