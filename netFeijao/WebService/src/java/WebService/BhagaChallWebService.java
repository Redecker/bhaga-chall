/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import logica.*;

/**
 *
 * @author 13104489
 */
@WebService(serviceName = "BhagaChallWebService")
public class BhagaChallWebService {
    
    public class Partida{
			
			private int[] IDs;
			private BhagaChall jogo;		
			private int idArray;
			
			public Partida(int id){
				idArray = id;
				IDs = new int[2];
				IDs[0] = -1;
				IDs[1] = -1;
				jogo = new BhagaChall();
			}
			
			public int setID(String nome){
				if(IDs[0] == -1){
					IDs[0] = jogo.registraJogador(nome);
					if(IDs[0] != -1) System.out.println("Jogador: " + nome + " Jogando na partida: " + this.idArray + " Com ID: " + IDs[0]);
					return IDs[0];
				}else if(IDs[1] == -1){
					IDs[1] = jogo.registraJogador(nome);
					if(IDs[1] != -1) System.out.println("Jogador: " + nome + " Jogando na partida: " + this.idArray + " Com ID: " + IDs[1]);
					return IDs[1];
				}
				return -1;
			}
			
			public int getIDArray(){
				return idArray;
			}
			
			public boolean contaisID(int id){
				if(IDs[0] == id || IDs[1] == id){
					return true;
				}
				return false;
			}
			
			public boolean temPartida(){
				if(IDs[0] == -1 || IDs[1] == -1){
					return false;
				}
				return true;
			}
		}
				
    private Partida[] partidas;
		
    public BhagaChallWebService(){
	partidas = new Partida[50];
	for(int i = 0; i < 50 ; i++){
		partidas[i] = new Partida(i);
	}
    }
		
    private Partida findPartidaID(int id){
	for(int i = 0; i < partidas.length; i++){
		if(partidas[i].contaisID(id)){
                    return partidas[i];
		}
	}
	return null;
    }
	
    
    @WebMethod(operationName = "preRegistro")
    public String preRegistro(@WebParam(name = "jogador1") String jogador1, @WebParam(name = "identificador1") int identificador1, @WebParam(name = "jogador2") String jogador2, @WebParam(name = "identificador2") int identificador2) {
        //TODO write your implementation code here:
        return null;
    }
    
    //AQUI VAI SABER QUAL É O JOGADOR PORQUE PRECISA DE UMA HASH DO PREREGISTRO!!!!!!!!!
    @WebMethod(operationName = "registraJogador")
    public int registraJogador(@WebParam(name = "nome") String nome) {  
        Partida partida = null;
	//se não tem lugar disponivel diz sinto muito
	if(partida == null){
            return -2;
	}	
	return partida.setID(nome); 
    }
    
    //VER ISSO AQUI TAH UMA GAMBIARRA FORTE!!!!!
    @WebMethod(operationName = "encerraPartida")
    public int encerraPartida(@WebParam(name = "identificador") int identificador) {
        Partida partida = findPartidaID(identificador);
	//não tem partida com esse id
	if(partida == null){
            return -1;
	}
	partida.jogo.encerraPartida(identificador);
	if(partida.IDs[0] == identificador){
            partida.IDs[0] = -2;
	}else if(partida.IDs[1] == identificador){
            partida.IDs[1] = -2;
	}
	if(partida.IDs[0] == -2 && partida.IDs[1] == -2) {
            partidas[partida.getIDArray()] = new Partida(partida.getIDArray());
        }
        System.out.println("Id: " + identificador + " desconectou.");
        return 0;
    }

    @WebMethod(operationName = "temPartida")
    public int temPartida(@WebParam(name = "identificador") int identificador) {
        Partida partida = findPartidaID(identificador);      
        return partida.jogo.temPartida(identificador);
    }

    @WebMethod(operationName = "ehMinhaVez")
    public int ehMinhaVez(@WebParam(name = "identificador") int identificador) {
        Partida partida = findPartidaID(identificador);   
        return partida.jogo.ehMinhaVez(identificador);
    }

    @WebMethod(operationName = "obtemGrade")
    public String obtemGrade(@WebParam(name = "identificador") int identificador) {
        Partida partida = findPartidaID(identificador);   
        return partida.jogo.obtemGrade(identificador);
    }

    @WebMethod(operationName = "moveTigre")
    public int moveTigre(@WebParam(name = "identificador") int identificador, @WebParam(name = "tigre") int tigre, @WebParam(name = "direcao") int direcao) {
        Partida partida = findPartidaID(identificador);   
        return partida.jogo.moveTigre(identificador, tigre, direcao);
    }

    @WebMethod(operationName = "posicionaCabra")
    public int posicionaCabra(@WebParam(name = "identificador") int identificador, @WebParam(name = "x") int x, @WebParam(name = "y") int y) {
        Partida partida = findPartidaID(identificador);   
        return partida.jogo.posicionaCabra(identificador, x, y);
    }

    @WebMethod(operationName = "moveCabra")
    public int moveCabra(@WebParam(name = "identificador") int identificador, @WebParam(name = "cabra") int cabra, @WebParam(name = "direcao") int direcao) {
        Partida partida = findPartidaID(identificador);   
        return partida.jogo.moveCabra(identificador, cabra, direcao);
    }

    @WebMethod(operationName = "obtemOponente")
    public String obtemOponente(@WebParam(name = "identificador") int identificador) {
        Partida partida = findPartidaID(identificador);   
        return partida.jogo.obtemOponente(identificador);
    }
}
