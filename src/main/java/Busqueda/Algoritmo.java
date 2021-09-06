
package Busqueda;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Roberto
 */
public class Algoritmo {
    
    public static int LecturaDeArchivo( int ValoresInicio[], int ValoresMeta[] ) throws FileNotFoundException, IOException{
        FileReader fr;
        int caracter, i = 0, j = 0, tam = 0;
        fr = new FileReader("entrada.txt");
        caracter = fr.read();
        if(caracter != -1){
            tam = Character.getNumericValue(caracter);
        }
        caracter = fr.read();
        while (caracter != -1) {
            if(caracter != 10 && caracter != 13 && caracter != 44 && i < 9){
                ValoresInicio[i] = Character.getNumericValue(caracter);
                i++;
            }else{
                if(caracter != 10 && caracter != 13 && caracter != 44 && i >= 9 && j < 9){
                    ValoresMeta[j] = Character.getNumericValue(caracter);
                    j++;
                }
            }  
            caracter = fr.read();
        }
        /*i = 0;
        while( i < 9){
            System.out.println(ValoresInicio[i] + "  " + ValoresMeta[i]);
            i++;
        }*/
        return tam;
    }
    
    public static int EVALUACION(int a, int b, Estado Actual, Estado Meta){
        int F = 0;
        double disEuclidea = 0, disTotalEuclidea = 0, disBlanca;
        int[] PiezasCorrectas = new int[2]; // Correctas en posición [0], Incorrectas en posición [1]
        
        // Heuristicas 
        PiezasCorrectas = Actual.ObtenerCorrectas_Vecinos(a, b, Meta);
        disEuclidea = Actual.ObtenerEuclidea(a, b, Actual.Matriz, Meta);       
        disTotalEuclidea = Actual.ObtenerTotalEuclidea(a, b, Actual, Meta);
        disBlanca = Actual.ObtenerEuclidea(Actual.blanca[0], Actual.blanca[1],Actual.Matriz, Meta);
        //
        F = (PiezasCorrectas[0]*(int)(5)) - (PiezasCorrectas[1]*((int)(1.5))) - ((int)disEuclidea*((int)(1.0))) - ((int)disTotalEuclidea*((int)(2.0))) - ((int)disBlanca*((int)(0.5))); //Funcion de evaluacion
        
        if( F < 0){
            F = 0;
        }
        return F;
    }
    
    public static void main(String args[]) throws IOException{
        
        System.out.println("Inicio");
        int[] ValoresInicio = new int[9];
        int[] ValoresMeta = new int[9];
        int[][] Vecinos = new int [1][4];
        int[] MovSiguiente = new int[2];
        int tam = 0, evaluacion, sigevaluacion, i = 0, ban = 0;
        tam = LecturaDeArchivo(ValoresInicio, ValoresMeta);
        
        Estado Inicio = new Estado(tam, ValoresInicio);
        Estado Actual = new Estado(tam, ValoresInicio);
        Estado Meta = new Estado(tam, ValoresMeta);
        Actual.Imprimir();
        //Meta.Imprimir();
        
        //Inicia algoritmo Hill Climbing
        
        Actual.fichasCorrectas = Actual.ObtenerCorrectas_Actual(Meta);
        System.out.println("");
        while(Actual.fichasCorrectas < 9 && ban != 7){            
            i = 0;
            Vecinos = Actual.ObtenerVecinos(Vecinos); //Obtiene los vecinos de la posición actual

            sigevaluacion = -1;
            MovSiguiente[0] = -1; // i  
            MovSiguiente[1] = -1; // j
            
            for(i = 0; i < Vecinos.length; i++){
                if(Vecinos[i][0] != -1 && Vecinos[i][1] != -1){
                    evaluacion = EVALUACION(Vecinos[i][0], Vecinos[i][1], Actual, Meta);
                    if( evaluacion > sigevaluacion){         /// < o > Significa maximizando o minimizando. > es maximizando
                            MovSiguiente[0] = Vecinos[i][0];
                            MovSiguiente[1] = Vecinos[i][1];
                            sigevaluacion = evaluacion;
                    }
                }
            }
            Actual.Mover(MovSiguiente[0], MovSiguiente[1]);                //Se mueve la ficha blanca
            Actual.fichasCorrectas = Actual.ObtenerCorrectas_Actual(Meta); //Actualizamos el número de fichas correctas
            if(ban == 6){
                System.out.println("");
                System.out.println("Proceso terminado por ciclamiento. ");
            }
            ban++;
        }
        System.out.println("");
        //System.out.println("La matriz resultante es: ");
       // Actual.Imprimir();
    }
}
