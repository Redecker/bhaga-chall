/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import java.util.ArrayList;
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
			
			private ArrayList<Integer> IDs;
			private BhagaChall jogo;
			private ArrayList<Boolean> encerra;
			public Partida(){
				IDs = new ArrayList<>();
				jogo = new BhagaChall();
                encerra = new ArrayList<>();
			}
                        
            public int encerraPartida(int id){
                int retorno = jogo.encerraPartida(id);
                encerra.add(Boolean.TRUE);
                           
                if(encerra.size() == 2){
	           		IDs = new ArrayList<>();
				    jogo = new BhagaChall();
                    encerra = new ArrayList<>();
                }
                return retorno;                            
            }
                        
			public int setID(String nome,int id){
				if(IDs.size() < 2){
                    int retornoID = jogo.registraJogador(nome, id);
                    IDs.add(retornoID);
                    return retornoID;
				}
				return -10;
			}
			
			public boolean contaisID(int id){
				return IDs.contains(id);
			}
			
            public boolean ninguemCadastrado(){
                return IDs.isEmpty();
            }
                        
			public boolean temPartida(){
				return IDs.size() == 2;
			}
		}

    public class PreRegistro{
      private String jogador1;
      private String jogador2;
      private int id1;
      private int id2;
      
      public PreRegistro(String j1, int id1, String j2, int id2){
          jogador1 = j1;
          jogador2 = j2;
          this.id1 = id1;
          this.id2 = id2;
      }
        public String getJogador1() {
            return jogador1;
        }

        public String getJogador2() {
            return jogador2;
        }

        public int getId1() {
            return id1;
        }

        public int getId2() {
            return id2;
        }
    }
    
    private Partida[] partidas;
    private ArrayList<PreRegistro> preRegistro; 
    public BhagaChallWebService(){
	partidas = new Partida[50];
        preRegistro = new ArrayList<>();
	for(int i = 0; i < 50 ; i++){
		partidas[i] = new Partida();
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
    public int preRegistro(@WebParam(name = "jogador1") String jogador1, @WebParam(name = "identificador1") int identificador1, @WebParam(name = "jogador2") String jogador2, @WebParam(name = "identificador2") int identificador2) {
        preRegistro.add(new PreRegistro(jogador1, identificador1, jogador2, identificador2));
        return 0;
    }
    
    @WebMethod(operationName = "registraJogador")
    public int registraJogador(@WebParam(name = "nome") String nome) {  
        int idmeu = -1;
        int idoutro = -1;
        for(int i = 0; i < preRegistro.size(); i++){
            PreRegistro pre = preRegistro.get(i);
            if(pre.getJogador1().equals(nome)){
                idmeu = pre.getId1();
                idoutro = pre.getId2();
                break;
            }else if(pre.getJogador2().equals(nome)){
                idmeu = pre.getId2();
                idoutro = pre.getId1();
                break;
            }
        }
               
        for(int i = 0; i < partidas.length; i++){
		if(partidas[i].contaisID(idoutro)){
                    return partidas[i].setID(nome, idmeu);
		}
	}
        
        for(int i = 0; i < partidas.length; i++){
            if(partidas[i].ninguemCadastrado()){
		return partidas[i].setID(nome, idmeu); 
            }
        }
        return -10;
    }
    
    @WebMethod(operationName = "encerraPartida")
    public int encerraPartida(@WebParam(name = "identificador") int identificador) {
        Partida partida = findPartidaID(identificador);
	   //não tem partida com esse id
	   if(partida == null){
            return -1;
	   }
	   return partida.encerraPartida(identificador);
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
