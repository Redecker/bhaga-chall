package project;

import java.rmi.Naming;
import java.util.Scanner;

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
			
			Scanner leitor = new Scanner(System.in);
			int id = bhagaChall.registraJogador(args[1]);
			int jogador = -1;
			int turno = 1;
			int cabras = 0;
			while(true){
				//verifica se tem partida
				switch(bhagaChall.temPartida(id)){
					//se ainda não tem partida
					case 0:
						continue;
					//sou as cabras
					case 1:
						jogador = 0;
						break;
					//sou os tigres
					case 2:
						jogador = 1;
						break;	
					case -1:
						System.out.println("<<ERRO | temPartida>>");
						return;
				}
				
				if(jogador == 0) System.out.println("Você está jogando com as cabras.");
				else if(jogador == 1) System.out.println("Você está jogando com os tigres.");
				else System.out.println("<<ERRO | jogador>>");
				
				System.out.println("O seu adversario é: " + bhagaChall.obtemOponente(id));
				
				while(true){
					switch(bhagaChall.ehMinhaVez(id)){
						//se não for minha vez
						case 0:
							continue;
						//se for minha vez
						case 1:
							//se sou cabra
							if(jogador == 0){
								System.out.println("Turno: " + turno++ + " " + args[1] + " vS" + bhagaChall.obtemOponente(id));
								System.out.println("Tabuleiro: \n" + bhagaChall.obtemGrade(id));
								
								//se todas as cabras já foram posicionadas
								if(bhagaChall.posicionaCabra(id, 2, 2) == -5){
									System.out.println("Movimente uma cabra, informando a cabra e a direção, nesse formato: Cabra-Direção");
									String coordenada = leitor.nextLine();
									String[] xy = coordenada.split("-");
									
									//testar o que acontece se não colocar um número provavelmente uma exeção dai tratar ela
									switch(bhagaChall.moveCabra(id, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]))){
										case 1:
											turno++;
											break;
										case 0:
											System.out.println("Movimento Invalido! Tente novamente.");
											break;
										case -6:
											System.out.println("Direção Invalida! Tente novamente.");
											break;
										case -1:
											System.out.println("<<ERRO | moveCabra>>");
											return;
									}
									break;
								}
								System.out.println("Posicione uma cabra, informando x e y, nesse formato: X-Y");
								String coordenada = leitor.nextLine();
								String[] xy = coordenada.split("-");
								
								//testar o que acontece se não colocar um número provavelmente uma exeção dai tratar ela
								switch(bhagaChall.posicionaCabra(id, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]))){
									case 1:
										turno ++;
										break;
									case 0:
										System.out.println("Posição Invalida! Tente novamente.");
										break;
									case -1:
										System.out.println("<<ERRO | posicionaCabra>>");
										return;
								}
							}
							//se sou tigre
							else if(jogador == 1){
								System.out.println("Movimente um tigre, informando o tigre e a direção, nesse formato: Tigre-Direção");
								String coordenada = leitor.nextLine();
								String[] xy = coordenada.split("-");
								
								switch(bhagaChall.posicionaCabra(id, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]))){
									case 1:
										turno++;
										break;
									case 0:
										System.out.println("Movimento Invalido! Tente novamente.");
										break;
									case -5:
										System.out.println("Direção Invalida! Tente novamente.");
										break;
									case -1:
										System.out.println("<<ERRO | moveCabra>>");
										return;
								}
							}
							break;
						//se sou o vencedor
						case 2:	
							bhagaChall.encerraPartida(id);
							System.out.println("Você venceu! Parabéns.");
							return;
						//se eu perdi
						case 3:
							bhagaChall.encerraPartida(id);
							System.out.println("Você perdeu! Tente outra vez.");
							return;
						case 5:
							bhagaChall.encerraPartida(id);
							System.out.println("Você venceu por WO! Parabéns.");
							return;
						case 6:
							bhagaChall.encerraPartida(id);
							System.out.println("Você perdeu por WO! Tente outra vez.");
							return;
					}
					
				}
			}
			
		} catch (Exception e) {
			System.out.println ("BhagaChallClient failed.");
			e.printStackTrace();
		}
	}
}
