
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BhagaChallImpl extends UnicastRemoteObject implements BhagaChallInterface{

		private static final long serialVersionUID = -513804057617910473L;
		
		
		public class Partida{
			
			private int[] IDs;
			private BhagaChall jogo;		
			private int idArray;
			private int IDquitou;
			
			public Partida(int id) throws RemoteException{
				idArray = id;
				IDs = new int[2];
				IDs[0] = -1;
				IDs[1] = -1;
				jogo = new BhagaChall();
				IDquitou = -1;
			}
			
			public int setID(String nome) throws RemoteException{
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
			
			public int getIDquitou(){
				return IDquitou;
			}
			
			public void setIDquitou(int id){
				IDquitou = id;
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
		
		//o identificador deve ser unico, guardar em uma tabela a partida e os identificadores
		
		private Partida[] partidas;
		
		public BhagaChallImpl(int nroPartidas) throws RemoteException{
			partidas = new Partida[nroPartidas];
			for(int i = 0; i < nroPartidas ; i++){
				partidas[i] = new Partida(i);
			}
		}
		
		private Partida getPartidaLivre(){
			for(int i = 0; i < partidas.length; i++){
				if(!partidas[i].temPartida()){
					return partidas[i];
				}
			}
			return null;
		}
		
		private Partida findPartidaID(int id){
			for(int i = 0; i < partidas.length; i++){
				if(partidas[i].contaisID(id)){
					return partidas[i];
				}
			}
			return null;
		}
		
		@Override
		public int registraJogador(String nome) throws RemoteException {
			Partida partida = getPartidaLivre();
			//se não tem lugar disponivel diz sinto muito
			if(partida == null){
				return -2;
			}	
			return partida.setID(nome); 
		}

		@Override
		public int encerraPartida(int id) throws RemoteException {
			Partida partida = findPartidaID(id);
			//não tem partida com esse id
			if(partida == null){
				return -1;
			}
			partida.jogo.encerraPartida(id);
			//zerar aqui o jogo e anunciar para o outro jogador que ele foi vitorioso
			partida.setIDquitou(id);
			return 0;
		}

		@Override
		public int temPartida(int id) throws RemoteException {
			Partida partida = findPartidaID(id);
			return partida.jogo.temPartida(id);
		}

		@Override
		public int ehMinhaVez(int id) throws RemoteException {
			Partida partida = findPartidaID(id);
			if(partida.getIDquitou() != -1){
				return 5;
			}
			return partida.jogo.ehMinhaVez(id);
		}

		@Override
		public String obtemGrade(int id) throws RemoteException {
			Partida partida = findPartidaID(id);
			return partida.jogo.obtemGrade(id);
		}

		@Override
		public int moveTigre(int id, int tigre, int direcao) throws RemoteException {
			Partida partida = findPartidaID(id);
			return partida.jogo.moveTigre(id, tigre, direcao);
		}

		@Override
		public int posicionaCabra(int id, int x, int y) throws RemoteException {
			Partida partida = findPartidaID(id);
			return partida.jogo.posicionaCabra(id, x, y);
		}

		@Override
		public int moveCabra(int id, int cabra, int direcao) throws RemoteException {
			Partida partida = findPartidaID(id);
			return partida.jogo.moveCabra(id, cabra, direcao);
		}

		@Override
		public String obtemOponente(int id) throws RemoteException {
			Partida partida = findPartidaID(id);
			return partida.jogo.obtemOponente(id);
		}		
}
