
package Busqueda;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Roberto
 */

public class Algoritmo {
    
    public static int TamañoTablero () throws FileNotFoundException, IOException{
        int tam = 0, caracter;
        FileReader fr;
        fr = new FileReader("entrada.txt");
        caracter = fr.read();
        if(caracter != -1){
            tam = Character.getNumericValue(caracter);
        }else{
            System.out.println("Error al cargar el tamaño del tablero.");
        }
       
        return tam;
    }
    
    public static void LecturaDeArchivo( int ValoresInicio[], int ValoresMeta[] ) throws FileNotFoundException, IOException{
        FileReader fr;
        int caracter, i = 0, j = 0, tam = 0, aux = 0;
        String auxi = "";
        fr = new FileReader("entrada.txt");
        caracter = fr.read();
        if(caracter != -1){ //Lee el tamaño del tablero
            tam = Character.getNumericValue(caracter);
        }
        
        caracter = fr.read();
        
        while (i < (tam*tam) ) { // Rellena el array con los valores de inicio
            auxi = ""; 
            if(caracter != 10 && caracter != 13 && caracter != 44){ //Rellena el array de inicio
                auxi = auxi + (char)caracter;  //Se convierte el caracter en String
                caracter = fr.read();
                if (caracter != 10 && caracter != 13 && caracter != 44) {                   
                    auxi = auxi + (char) caracter; //Se concatena los dos caracteres para formar un número entero de 2 dígitos                    
                    aux = Integer.parseInt(auxi);                   
                    ValoresInicio[i] = aux;
                    caracter = fr.read();
                }else{                    
                    aux = Integer.parseInt(auxi);
                    ValoresInicio[i] = aux;
                }
                aux = 0;
                i++;
            }else{             
              caracter = fr.read();  
            }
        }
        
        i = 0;
        while (i < (tam*tam) ) { // Rellena el array con los valores meta
            auxi = ""; 
            if(caracter != 10 && caracter != 13 && caracter != 44){ //Rellena el array de inicio
                auxi = auxi + (char)caracter;  //Se convierte el caracter en String
                caracter = fr.read();
                if (caracter != 10 && caracter != 13 && caracter != 44 && caracter != -1) {                   
                    auxi = auxi + (char) caracter; //Se concatena los dos caracteres para formar un número entero de 2 dígitos                    
                    aux = Integer.parseInt(auxi);                   
                    ValoresMeta[i] = aux;
                    caracter = fr.read();
                }else{                    
                    aux = Integer.parseInt(auxi);
                    ValoresMeta[i] = aux;
                }
                aux = 0;
                i++;
            }else{             
              caracter = fr.read();  
            }
        }
                
        /*i = 0;
        while( i < 9){
            System.out.println(ValoresInicio[i] + "  " + ValoresMeta[i]);
            i++;
        }*/

    }
    
    public static int EVALUACION(int a, int b, Estado Actual, Estado Meta){ //Función de evaluación
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
        
        int tam = 0, evaluacion, sigevaluacion, i = 0, ban = 0;
        tam = TamañoTablero();
        System.out.println("Tamanio: "+tam);
        
        int[] ValoresInicio = new int[(tam*tam)]; //Array que contiene los valores de inicio
        int[] ValoresMeta = new int[(tam*tam)]; //Array que contiene los valores meta
        int[][] Vecinos = new int [1][4]; //Sólo pueden haber 4 vecinos en todos los casos
        int[] MovSiguiente = new int[2];
        
        LecturaDeArchivo(ValoresInicio, ValoresMeta);
        
        Estado Inicio = new Estado(tam, ValoresInicio);
        Estado Actual = new Estado(tam, ValoresInicio);
        Estado Meta = new Estado(tam, ValoresMeta);
        Actual.Imprimir();
        //System.out.println("");
        //Meta.Imprimir();
        
        //Inicia algoritmo Hill Climbing
        
        Actual.fichasCorrectas = Actual.ObtenerCorrectas_Actual(Meta);
        System.out.println("");
        while(Actual.fichasCorrectas < (tam*tam) && ban != 7){ // ban representa el límite de movimientos a realizar, sin importar si se resolvió o no.          
            i = 0;
            Vecinos = Actual.ObtenerVecinosNew(Vecinos); //Obtiene los vecinos de la posición actual

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
