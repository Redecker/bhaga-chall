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
			if(id == -1){
				System.out.println("Usuario já está jogando no momento.");
				return;
			}else if(id == -2){
				System.out.println("Limite de partidas atingido.");
				return;
			}
			int jogador = -1;
			int turno = 1;
			int cabras = 20;
			
			System.out.println("Aguardando partida " + args[1] + " do servidor " + args[0]);

			//processo de iniciar a partida
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
					case -2:
						bhagaChall.encerraPartida(id);
						System.out.println("Tempo de espera esgotado, tente jogar novamente.");
						return;
				}
				
				if(jogador == 0){
					System.out.println("Você está jogando com as cabras.");
					System.out.println("As cabras serão identificadas de A até T");
					System.out.println("Ao total são 20 cabras que serão posicionadas no jogo");
					System.out.println("Após o número de cabras ser alcançado, é possivel movimentar com as cabras pelo tabuleiro, escolhendo uma cabra e uma direção");
					System.out.println("As direções varias de 0 a 7");
					System.out.println("O tabuleiro é composto de 0 até 4 em x, e 0 até 4 em y");
					System.out.println("Para mais informações, porfavor, ver o enunciado do trabalho");
					System.out.println("Você é o primeiro a jogar.");
				}else if(jogador == 1){
					System.out.println("Você está jogando com os tigres.");
					System.out.println("Os tigres são identificados de 1 a 4");
					System.out.println("Você deve tentar capturar as cabras, pulando sobre elas, escolhendo um tigre e uma direção");
					System.out.println("As direções varias de 0 a 7");
					System.out.println("O tabuleiro é composto de 0 até 4 em x, e 0 até 4 em y");
					System.out.println("Para mais informações, porfavor, ver o enunciado do trabalho");
					System.out.println("Aguarde você é o segundo a jogar.");
				}else{
					bhagaChall.encerraPartida(id);
					System.out.println("<<ERRO | jogador>> Reinicie o jogo.");
				}
				
				System.out.println("O seu adversario é: " + bhagaChall.obtemOponente(id));
				break;
			}
			
			long start = System.nanoTime();    
			long elapsedTime = System.nanoTime() - start;

			
			//processo de jogar o jogo
			while(true){
				switch(bhagaChall.ehMinhaVez(id)){
					//se não for minha vez
					case 0:
						continue;
					//se for minha vez
					case 1:
						//se sou cabra
						if(jogador == 0){
							try{
								System.out.println("Turno: " + turno + " " + args[1] + " vS " + bhagaChall.obtemOponente(id));
								printTabuleiro(bhagaChall.obtemGrade(id));
							
								//se todas as cabras já foram posicionadas
								if(cabras == 0){
									System.out.println("Movimente uma cabra, informando a cabra e a direção, nesse formato: Cabra-Direção");
									String coordenada = leitor.nextLine();
									
									if(!coordenada.contains("-")){
										System.out.println("Você digitou alguma coisa errada, porfavor tente novamente.");
										continue;
									}
									String[] xy = coordenada.split("-");							
									
									switch(bhagaChall.moveCabra(id, (int)xy[0].charAt(0), Integer.parseInt(xy[1]))){
										case 1:
											turno++;
											printTabuleiro(bhagaChall.obtemGrade(id));
											System.out.println("Aguarde...");
											continue;
										case 0:
											System.out.println("Movimento Invalido! Tente novamente.");
											continue;
										case -6:
											System.out.println("Direção Invalida! Tente novamente.");
											continue;
										case -1:
											System.out.println("<<ERRO | moveCabra>> Tente novamente, ou contate o administrador do sistema.");
											continue;
									}
								} 							
								System.out.println("Posicione uma cabra, informando x e y, nesse formato: X-Y");
								String coordenada = leitor.nextLine();
								
								if(!coordenada.contains("-")){
									System.out.println("Você digitou alguma coisa errada, porfavor tente novamente.");
									continue;
								}
								String[] xy = coordenada.split("-");
							
								switch(bhagaChall.posicionaCabra(id, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]))){
									case 1:
										turno ++;
										cabras--;
										printTabuleiro(bhagaChall.obtemGrade(id));
										System.out.println("Aguarde...");
										continue;
									case 0:
										System.out.println("Posição Invalida! Tente novamente.");
										continue;
									case -1:
										System.out.println("<<ERRO | posicionaCabra>> Tente novamente, ou contate o administrador do sistema.");
										continue;
								}
							} catch(Exception nfe){
								System.out.println("Você digitou alguma coisa errada, porfavor tente novamente.");
								continue;
								} 
							}
							//se sou tigre
							else if(jogador == 1){
								try{
									System.out.println("Turno: " + turno + " " + args[1] + " vS " + bhagaChall.obtemOponente(id));
									printTabuleiro(bhagaChall.obtemGrade(id));
								
									System.out.println("Movimente um tigre, informando o tigre e a direção, nesse formato: Tigre-Direção");
									String coordenada = leitor.nextLine();
									
									if(!coordenada.contains("-")){
										System.out.println("Você digitou alguma coisa errada, porfavor tente novamente.");
										continue;
									}
									String[] xy = coordenada.split("-");
																		
									switch(bhagaChall.moveTigre(id, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]))){
										case 1:
											turno++;
											printTabuleiro(bhagaChall.obtemGrade(id));
											System.out.println("Aguarde...");
											continue;
										case 0:
											System.out.println("Movimento Invalido! Tente novamente.");
											continue;
										case -5:
											System.out.println("Direção Invalida! Tente novamente.");
											continue;
										case -1:
											System.out.println("<<ERRO | moveTigre>> Tente novamente, ou contate o administrador do sistema.");
											continue;
									}
								}catch(Exception nfe){
									System.out.println("Você digitou alguma coisa errada, porfavor tente novamente.");
									continue;
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
		}catch (Exception e) {
			System.out.println ("BhagaChallClient failed.");
			e.printStackTrace();
		}
		
		
		
	}
	
	private static void printTabuleiro(String tab){
		System.out.println("Tabuleiro:");
		System.out.println(tab.substring(0, 5));
		System.out.println(tab.substring(5, 10));
		System.out.println(tab.substring(10, 15));
		System.out.println(tab.substring(15, 20));
		System.out.println(tab.substring(20, tab.length()));
	}
}
