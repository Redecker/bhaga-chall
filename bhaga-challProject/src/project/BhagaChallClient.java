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
			BhagaChallInterface bhagaChall = (BhagaChallInterface)Naming.lookup("//"+args[0]+"/BhagaChall");
			//Logica do jogo
			bhagaChall.registraJogador(args[1]);
			
		} catch (Exception e) {
			System.out.println ("BhagaChallClient failed.");
			e.printStackTrace();
		}
	}
}
