package project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BhagaChallImpl extends UnicastRemoteObject implements BhagaChallInterface{

		private static final long serialVersionUID = -513804057617910473L;
		
		//o identificador deve ser unico, guardar em uma tabela a partida e os identificadores
		
		private BhagaChall[] partidas;
		private boolean[] partidasAcontecendo;
		
		public BhagaChallImpl(int nroPartidas) throws RemoteException{
			
			partidas = new BhagaChall[nroPartidas];
			partidasAcontecendo = new boolean[nroPartidas];
			for(int i = 0; i < nroPartidas ; i++){
				partidas[i] = new BhagaChall();
				partidasAcontecendo[i] = false;
			}
		}
		
		private int getPartidaLivre(){
			for(int i = 0; i < partidasAcontecendo.length; i++){
				if(partidasAcontecendo[i] == false){
					partidasAcontecendo[i] = true;
					return i;
				}
			}
			return -1;
		}
		
		@Override
		public int registraJogador(String nome) throws RemoteException {
			int partida = getPartidaLivre();
			//se não tem lugar disponivel diz sinto muito
			if(partida == -1){
				
			}
			partidas[partida].registraJogador(nome);
		}

		@Override
		public int encerraPartida(int id) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int temPartida(int id) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int ehMinhaVez(int id) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String obtemGrade(int id) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int moveTigre(int id, int tigre, int direcao) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int posicionaCabra(int id, int x, int y) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int moveCabra(int id, int cabra, int direcao) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String obtemOponente(int id) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}		
}
