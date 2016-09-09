package project;

import java.rmi.Naming;

public class BhagaChallClient {
	public static void main (String[] args) {
		//COMO USAR
		if	(args.length != 2)  {
			System.out.println("Uso: java BhagaChallClient <maquina> <nome>");
			System.exit(1);
		}
		try {
			//Logica do jogo
			BhagaChallInterface bhagaChall = (BhagaChallInterface)Naming.lookup("//"+args[0]+"/BhagaChall");
			
		} catch (Exception e) {
			System.out.println ("BhagaChallClient failed.");
			e.printStackTrace();
		}
	}
}
