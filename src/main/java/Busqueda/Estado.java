
package Busqueda;

/**
 *
 * @author Roberto
 */
public class Estado {
    int Matriz[][];
    int tam = 0;
    int[] blanca = new int[2];
    //int[] ultimaPos = new int[2];
    int fichasCorrectas = 0;
    int fichasIncorrectas = 0;
    
    public Estado(int tam, int Valores[]){
        this.tam = tam;        
        this.Matriz = new int[tam][tam];
        
        int v = 0;
        while( v < Valores.length ){
            for(int i = 0; i < tam; i++){
                for(int j = 0; j < tam; j++){
                    if(Valores[v] == 0){
                        this.blanca[0] = i;
                        this.blanca[1] = j;
                    }
                    this.Matriz[i][j] = Valores[v];
                    v++;
                }
            }
        }
    }
    
    public void Imprimir(){
        for (int i = 0; i < this.tam; i++) {
            for (int j = 0; j < this.tam; j++) {
                System.out.print(this.Matriz[i][j]+" ");
            }
            System.out.println("");
        }
        //System.out.println("Blanca: "+this.blanca[0]+", "+this.blanca[1]);
    }

    public int[][] ObtenerVecinos(int Vecinos[][]){ //Modificar
        Vecinos = new int[4][2];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                Vecinos[i][j] = -1;
            }
        }
        
        int caso = 0;
        
        if(this.blanca[0] == 1 && this.blanca[1] == 1){ // Caso blanca en medio
            Vecinos[0][0] = 0;
            Vecinos[0][1] = 1;
            
            Vecinos[1][0] = 1;
            Vecinos[1][1] = 2;
            
            Vecinos[2][0] = 2;
            Vecinos[2][1] = 1;
            
            Vecinos[3][0] = 1;
            Vecinos[3][1] = 0;
        }else{
            caso = this.blanca[0] - this.blanca[1];
            if( caso == -2){
                caso = 2;
            }else{
                if(caso == -1){
                    caso = 1;
                }
            }
            //System.out.println("Caso:"+caso);
            switch(caso){
                case 0://Caso ezquinas superior izquierda e inferior derecha
                    if(this.blanca[0] == 0){
                        Vecinos[0][0] = 0;
                        Vecinos[0][1] = 1;
                        
                        Vecinos[1][0] = 1;
                        Vecinos[1][1] = 0;
                    }else{
                        Vecinos[0][0] = 2;
                        Vecinos[0][1] = 1;
                        
                        Vecinos[1][0] = 1;
                        Vecinos[1][1] = 2;
                    }
                break;
                
                case 2: //Caso ezquina inferior izquierda y superior derecha
                    if(this.blanca[0] == 2){
                        Vecinos[0][0] = 1;
                        Vecinos[0][1] = 0;
                        
                        Vecinos[1][0] = 2;
                        Vecinos[1][1] = 1;
                    }else{
                        Vecinos[0][0] = 0;
                        Vecinos[0][1] = 1;
                        
                        Vecinos[1][0] = 1;
                        Vecinos[1][1] = 2;
                    }
                break;
                    
                case 1: //Caso entre las ezquinas 
                    if(this.blanca[0] == 1){
                        if(this.blanca[1] == 0){
                            Vecinos[0][0] = 0;
                            Vecinos[0][1] = 0;
                            
                            Vecinos[1][0] = 1;
                            Vecinos[1][1] = 1;
                            
                            Vecinos[2][0] = 2;
                            Vecinos[2][1] = 0;
                            
                        }else{
                            Vecinos[0][0] = 0;
                            Vecinos[0][1] = 2;
                            
                            Vecinos[1][0] = 1;
                            Vecinos[1][1] = 1;
                            
                            Vecinos[2][0] = 2;
                            Vecinos[2][1] = 2;
                            
                        }
                    }else{
                        if(this.blanca[0] == 0){
                            Vecinos[0][0] = 0;
                            Vecinos[0][1] = 0;
                            
                            Vecinos[1][0] = 1;
                            Vecinos[1][1] = 1;
                            
                            Vecinos[2][0] = 0;
                            Vecinos[2][1] = 2;
                            
                        }else{
                            Vecinos[0][0] = 2;
                            Vecinos[0][1] = 0;
                            
                            Vecinos[1][0] = 1;
                            Vecinos[1][1] = 1;
                            
                            Vecinos[2][0] = 2;
                            Vecinos[2][1] = 2;
                        }
                    }
                break;
                
                default:
                    System.out.println("ERROR - Switch resta vecinos");
                break;
            }
        }
        int i = 0;
        /*while(i < Vecinos.length){
            if(Vecinos[i][0] != -1){
                System.out.println("Vecinos ["+Vecinos[i][0]+"," + Vecinos[i][1]+"]");   
            }
            i++;
        }*/
        
        return Vecinos;
    }
    
    public int[][] ObtenerVecinosNew(int[][] Vecinos){
        Vecinos = new int[4][2];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2; j++){
                Vecinos[i][j] = -1;
            }
        }
        
        //Asignación de vecinos
        
        if(this.blanca[0] != 0){ //Vecino Up
            Vecinos[0][0] = this.blanca[0] - 1;
            Vecinos[0][1] = this.blanca[1];
        }
        if(this.blanca[1] != this.tam-1){ //Vecino Right
            Vecinos[1][0] = this.blanca[0];
            Vecinos[1][1] = this.blanca[1] + 1;
        }
        if(this.blanca[0] != this.tam-1){ //Vecino Down
            Vecinos[2][0] = this.blanca[0] + 1;
            Vecinos[2][1] = this.blanca[1];
        }
        if(this.blanca[1] != 0){ //Vecino Left
            Vecinos[3][0] = this.blanca[0];
            Vecinos[3][1] = this.blanca[1] - 1;
        }
        
        return Vecinos;
    }
    
    public int[] ObtenerCorrectas_Vecinos(int a, int b, Estado Meta){
        int n = 0, m = 0, auxi = 0;
        int[][] aux = new int[this.tam][this.tam];
        int[] correctas = new int[2];
        for(int i = 0; i < this.tam; i++){
            for(int j = 0; j < this.tam; j++){
                aux[i][j] = this.Matriz[i][j];
                //System.out.print(aux[i][j]+" ");
            }
            //System.out.println("");
        }
        auxi = this.Matriz[this.blanca[0]][this.blanca[1]];
        aux[this.blanca[0]][this.blanca[1]] = aux[a][b];
        aux[a][b] = auxi;

        for(int i = 0; i < this.tam; i++){
            for(int j = 0; j < this.tam; j++){
                //System.out.println(aux[i][j]+" == "+Meta.Matriz[i][j]);
                if(aux[i][j] == Meta.Matriz[i][j]){
                    n++;
                }else{
                    m++;
                }
            }
        }
        correctas[0] = n;
        correctas[1] = m;
        
        return correctas;
    }
    
    public int ObtenerCorrectas_Actual(Estado Meta){
        int n = 0, m = 0;
        for(int i = 0; i < this.tam; i++){
            for(int j = 0; j < this.tam; j++){
                if(this.Matriz[i][j] == Meta.Matriz[i][j]){
                    n++;
                }else{
                    m++;
                }
            }
        }
        return n;
    }
    
    public double ObtenerEuclidea(int a, int b, int actual[][], Estado Meta){
        int auxi = -1, auxj = -1;
        double Distancia = -1;
        for (int i = 0; i < actual.length; i++){
            for(int j = 0; j < actual.length; j++){
                if(actual[a][b] == Meta.Matriz[i][j]){
                    auxi = i;
                    auxj = j;
                }
            }
        }
        Distancia = Math.sqrt(Math.pow((a - auxi), 2) + Math.pow((b - auxj), 2));
        
        return Distancia;
    }
    
    public double ObtenerTotalEuclidea(int a, int b, Estado actual, Estado Meta){
        int auxi;
        int[][] aux = new int[actual.tam][actual.tam];
        double N = 0;
        for(int i = 0; i < actual.tam; i++){ //Rellena la matriz auxiliar con los datos de la actual
            for(int j = 0; j < actual.tam; j++){
                aux[i][j] = actual.Matriz[i][j];
                //System.out.print(aux[i][j]+" ");
            }
            //System.out.println("");
        }
        
        auxi = aux[actual.blanca[0]][actual.blanca[1]]; //Realiza el cambio de la blanca
        aux[actual.blanca[0]][actual.blanca[1]] = aux[a][b];
        aux[a][b] = auxi;

        for (int i = 0; i < actual.tam; i++) {
            for (int j = 0; j < actual.tam; j++) {
                N  = N + ObtenerEuclidea(i, j, aux, Meta);
            }
        }
        return N;
    }
    
    public void Mover(int a, int b){
        
        int aux = 0;
        aux = this.Matriz[this.blanca[0]][this.blanca[1]];
        
        this.Matriz[this.blanca[0]][this.blanca[1]] = this.Matriz[a][b];
        this.Matriz[a][b] = aux;

        if(this.blanca[0] == a){
            if(this.blanca[1] > b){
                System.out.print("L,");
            }else{
                System.out.print("R,");
            }
        }else{
            if(this.blanca[1] == b){
                if(this.blanca[0] > a){
                    System.out.print("U,");
                }else{
                    System.out.print("D,");
                }
            }
        }
        //this.ultimaPos[0] = this.blanca[0];
       // this.ultimaPos[1] = this.blanca[1];
        
        this.blanca[0]=a;
        this.blanca[1]=b;

    }
    
} ///end of Estado
