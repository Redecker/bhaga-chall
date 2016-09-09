package project;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BhagaChallInterface extends Remote {
	/* 1)registraJogador
	 * Recebe: string com o nome do usuário/jogador
	 * Retorna: id (valor inteiro) do usuário (que corresponde a um número de identificação único para
	 * este usuário durante uma partida), ­1 se este usuário já está  cadastrado ou ­2 se o número
	 * máximo de jogadores tiver sido atingido 
	 */
	public int registraJogador(String nome) throws RemoteException;
	
	/* 2) encerraPartida
	 * Recebe: id do usuário (obtido através da chamada registraJogador)
	 * Retorna: ­1 (erro), 0 (ok) 
	 */
	public int encerraPartida(int id) throws RemoteException;
	
	/* 3) temPartida
	 * Recebe: id do usuário (obtido através da chamada registraJogador)
	 * Retorna: ­2 (tempo de espera esgotado), ­1 (erro), 0 (ainda não há partida), 1 (sim, há partida e o
	 * jogador inicia jogando com cabras, identificadas, por exemplo, com letras de “A” até “T”) ou 2
	 * (sim, há partida e o jogador é o segundo a jogar, com os tigres, identificados, por exemplo, com
	 * dígitos de “1” até “4”)
	 */
	public int temPartida(int id) throws RemoteException;
	
	/*4) ehMinhaVez
	 * Recebe: id do usuário (obtido através da chamada registraJogador)
     * Retorna: ­2 (erro: ainda não há 2 jogadores registrados na partida), ­1 (erro), 0 (não), 1 (sim), 2
     * (é o vencedor), 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por WO)
	 */
	public int ehMinhaVez(int id) throws RemoteException;
	
	/*5) obtemGrade
	 * Recebe: id do usuário (obtido através da chamada registraJogador)
	 * Retorna: string vazio em caso de erro ou string com a grade de jogo
	 * A grade de jogo pode, por exemplo, ser uma cadeia de caracteres onde cada caracter corresponde
	 * a uma casa do tabuleiro, com: letras de 'A' até 'T' correspondendo a uma casa ocupada por uma
	 * cabra, dígitos de '1' a '4' correspondendo a uma casa ocupada por um tigre e '.' correspondendo a
	 * uma casa não ocupada. Para a posição inicial do tabuleiro, o string seria, por exemplo:
	 * “1...2...............3...4”
	 * Depois da cabra “A” colocada na posição central, do tigre “1” movimentado em direção a esta
	 * cabra   e   da   cabra   “B”   colocada   em   um   posição   desta   mesma   diagonal,   o  string  seria,   por
	 * exemplo:
	 * “....2.1.....A.....B.3...4”
	 */
	public String obtemGrade(int id) throws RemoteException;
	
	/*6) moveTigre
	 * Recebe: id do usuário (obtido através da chamada registraJogador) e direção relativa da jogada
	 * (0 = para direita; 1 = para direita/abaixo; 2 = para baixo; 3 = para esquerda/abaixo; 4 = para
	 * esquerda; 5 = para esquerda/acima; 6 = para cima; 7 = para direita/acima).
	 * Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
	 * jogada e ocorra o time­out de 30 segundos para envio de jogadas), 1 (tudo certo), 0 (movimento
	 * inválido), ­1 (erro), ­2 (partida não iniciada: ainda não há dois jogadores registrados na partida),
	 * ­3 (não é a vez do jogador), ­4 (não está jogando com o animal correto) ou ­5 (direção inválida).
	 */
	public String moveTigre(int id, int direcao) throws RemoteException;
	
	/*7) posicionaCabra
	 * ­Recebe: id do usuário (obtido através da chamada registraJogador) e posição do tabuleiro onde a
	 * ­cabra deve ser posicionada (x e y, ambos variando de 0 a 4, inclusive).
	 * ­Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
	 * ­jogada e ocorra o time­out de 30 segundos para envio de jogadas), 1 (tudo certo), 0 (movimento
	 * ­inválido), ­1 (erro), ­2 (partida não iniciada: ainda não há dois jogadores registrados na partida),
	 * ­­3 (não é a vez do jogador), ­4 (não está jogando com o animal correto) ou ­5 (todas as cabras já
	 * ­foram posicionadas).
	 */
	public String posicionaCabra(int id, int x, int y) throws RemoteException;
	
	/*8) moveCabra
	 * ­Recebe: id do usuário (obtido através da chamada registraJogador), identificador da cabra a ser
	 * ­movimentada (valor de 0 até 19, inclusive, considerando que cada cabra estará em determinada
	 * ­posição do tabuleiro, conforme histórico do jogo) e direção relativa da jogada (0 = para direita; 1
	 * ­= para direita/abaixo; 2 = para baixo; 3 = para esquerda/abaixo; 4 = para esquerda; 5 = para
	 * ­esquerda/acima; 6 = para cima; 7 = para direita/acima).
	 * ­Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
	 * ­jogada e ocorra o time­out de 30 segundos para envio de jogadas), 1 (tudo certo), 0 (movimento
	 * ­inválido), ­1 (erro), ­2 (partida não iniciada: ainda não há dois jogadores registrados na partida),
	 * ­­3 (não é a vez do jogador), ­4 (não está jogando com o animal correto), ­5 (ainda não está na
	 * ­fase de movimentação de cabras) ou ­6 (direção inválida).
	 */	
	public String moveCabra(int id, int cabra, int direcao) throws RemoteException;
	
	/*9) obtemOponente
 	 * Recebe: id do usuário (obtido através da chamada registraJogador)
	 * Retorna: string vazio para erro ou string com o nome do oponente
	 */
	public String obtemOponente(int id) throws RemoteException;
	
}


