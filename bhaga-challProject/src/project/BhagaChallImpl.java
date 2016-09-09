package project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BhagaChallImpl extends UnicastRemoteObject{

		private static final long serialVersionUID = -513804057617910473L;
		
		private BhagaChall[] partidas;

		public BhagaChallImpl(int nroPartidas) throws RemoteException{
			
			partidas = new BhagaChall[nroPartidas];
			for(int i = 0; i < nroPartidas ; i++){
				partidas[i] = new BhagaChall();
			}
		}		
}
