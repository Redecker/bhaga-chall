import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BhagaChall extends UnicastRemoteObject implements BhagaChallInterface {

		private static final long serialVersionUID = -513804057617910473L;
		
		public BhagaChall() throws RemoteException{
			
		}

		@Override
		public int registraJogador(String nome) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
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
