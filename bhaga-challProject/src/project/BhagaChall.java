package project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BhagaChall extends UnicastRemoteObject implements BhagaChallInterface {

		private static final long serialVersionUID = -513804057617910473L;
		

		private int jogador = 0;
		private String[] jogadores;
		
		public class Tabuleiro{

		}

		public BhagaChall() throws RemoteException{			
			jogadores = new String[2];
		}

		/* 1)registraJogador
	 	* Recebe: string com o nome do usuário/jogador
	 	* Retorna: id (valor inteiro) do usuário (que corresponde a um número de identificação único para
	 	* este usuário durante uma partida), ­1 se este usuário já está  cadastrado ou ­2 se o número
	 	* máximo de jogadores tiver sido atingido 
	 	*/
		@Override
		public int registraJogador(String nome) throws RemoteException {
			if(jogador < 2){
				jogadores[jogador++] = nome;
				return 1;
			}else{
				return 2;
			}	
		}
		/* 2) encerraPartida
		 * Recebe: id do usuário (obtido através da chamada registraJogador)
		 * Retorna: ­1 (erro), 0 (ok) 
		 */
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
		public String moveTigre(int id, int direcao) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String posicionaCabra(int id, int x, int y) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String moveCabra(int id, int cabra, int direcao) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String obtemOponente(int id) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
		
}

