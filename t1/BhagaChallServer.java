import java.rmi.Naming;
import java.rmi.RemoteException;

public class BhagaChallServer {
	public static void main (String[] args) {
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI registry ready.");			
		} catch (RemoteException e) {
			System.out.println("RMI registry already running.");			
		}
		try {
			Naming.rebind ("BhagaChall", new BhagaChallImpl(50));
			System.out.println ("BhagaChallServer is ready.");
		} catch (Exception e) {
			System.out.println ("BhagaChallServer failed:");
			e.printStackTrace();
		}
	}
	
}

