package project;

import static org.junit.Assert.assertSame;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//import static org.junit.Assert.*;

import org.junit.Test;

public class BhagaChall extends UnicastRemoteObject implements BhagaChallInterface {

		private static final long serialVersionUID = -513804057617910473L;
		
		public class Tabuleiro{
			
			char[][] board;
						
			public Tabuleiro(){
				board = new char[5][5];
				startBoard();
			}
			
			private void startBoard(){
				for(int i = 0; i < board[0].length ; i++){
					for(int j = 0; j < board[0].length ; j++){
						board[i][j] = '.';
					}
				}
			}
			
			public char getPosicao(int x, int y){
				return board[x][y];
			}
			
			public void setPosicao(int x, int y, char content){
				board[x][y] = content;
			}
			
			public String toString(){
				String toReturn = "";
				for(int i = 0; i < board[0].length ; i++){
					for(int j = 0; j < board[0].length ; j++){
						toReturn += board[i][j];
					}
				}
				return toReturn;
			}
			
			public String toStringForTests(){
				String toReturn = "";
				for(int i = 0; i < board[0].length ; i++){
					for(int j = 0; j < board[0].length ; j++){
						toReturn += board[i][j];
					}
					toReturn += "\n";
				}
				return toReturn;
			}
			
		}

		private Tabuleiro tabuleiro;
		String[] jogadores;
		int turno;
		
		int proximaCabra;
		
		public BhagaChall() throws RemoteException{			
			tabuleiro = new Tabuleiro();
			tabuleiro.setPosicao(0, 0, '1');
			tabuleiro.setPosicao(0, 4, '2');
			tabuleiro.setPosicao(4, 0, '3');
			tabuleiro.setPosicao(4, 4, '4');
			
			jogadores = new String[2];
		
			turno = 0;
			
			proximaCabra = 65;
		}
		
		public static void main(String[] args) throws RemoteException{
			
			BhagaChall bc = new BhagaChall();
			
			

		}
		
		//verifica se alguem ganhou
		//retorna 0 se o id perdeu
		//		  1 se o id ganhou
		//        2 se empatou?????
		private int vencedor(int id){
			return 0;
		}
		
		/* 1)registraJogador
	 	* Recebe: string com o nome do usuário/jogador
	 	* Retorna: id (valor inteiro) do usuário (que corresponde a um número de identificação único para
	 	* este usuário durante uma partida), -­1 se este usuário já está  cadastrado ou ­-2 se o número
	 	* máximo de jogadores tiver sido atingido 
	 	*/
		@Override
		public int registraJogador(String nome) throws RemoteException {			
			//retorna id do jogador
			if(jogadores[0] == null){
				jogadores[0] = nome;
				return 0;					
			}else if(jogadores[1] == null){
				//se o usuario já esta cadastrado retorna -1		
				if(jogadores[0].equals(nome)) return -1; 
				jogadores[1] = nome;
				return 1;
			}
			//se não tem mais espaço pra jogar
			else{
				return -2;
			}
		}		
		@Test
		public void testRegistraJogador() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			assertSame(0, bhaga.registraJogador("Matheus"));
			assertSame(-1, bhaga.registraJogador("Matheus"));	
			assertSame(1, bhaga.registraJogador("Carol"));
			assertSame(-2, bhaga.registraJogador("Maria"));
		}
		
		/* 2) encerraPartida
		 * Recebe: id do usuário (obtido através da chamada registraJogador)
		 * Retorna: -­1 (erro), 0 (ok) 
		 */
		@Override
		public int encerraPartida(int id) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}
		/* 3) temPartida
		 * Recebe: id do usuário (obtido através da chamada registraJogador)
		 * Retorna: ­-2 (tempo de espera esgotado), -­1 (erro), 0 (ainda não há partida), 1 (sim, há partida e o
		 * jogador inicia jogando com cabras, identificadas, por exemplo, com letras de “A” até “T”) ou 2
		 * (sim, há partida e o jogador é o segundo a jogar, com os tigres, identificados, por exemplo, com
		 * dígitos de “1” até “4”)
		 */
		@Override
		public int temPartida(int id) throws RemoteException {
			//tempo de espera esgotado retorna -2
			//se ainda não tem partida
			if(jogadores[0] == null || jogadores[1] == null){
				return 0;
			}
			
			if(id == 0){
				return 1;
			}else if(id == 1){
				return 2;
			}else{
				//se erro retorna -1
				return -1;
			}			
		}
		@Test
		public void testTemPartida() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			assertSame(0, bhaga.temPartida(0));			
			assertSame(0, bhaga.temPartida(1));
			assertSame(0, bhaga.registraJogador("Matheus"));
			assertSame(0, bhaga.temPartida(0));			
			assertSame(0, bhaga.temPartida(1));
			assertSame(1, bhaga.registraJogador("Carol"));
			assertSame(1, bhaga.temPartida(0));			
			assertSame(2, bhaga.temPartida(1));
			
			assertSame(-1, bhaga.temPartida(2));			
			assertSame(-1, bhaga.temPartida(10));
		}
		
		/*4) ehMinhaVez
		 * Recebe: id do usuário (obtido através da chamada registraJogador)
	     * Retorna: -­2 (erro: ainda não há 2 jogadores registrados na partida), ­-1 (erro), 0 (não), 1 (sim), 2
	     * (é o vencedor), 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por WO)
		 */
		@Override
		public int ehMinhaVez(int id) throws RemoteException {
			//se não tem um jogador retorn -2
			if(jogadores[0] == null || jogadores[1] == null){
				return -2;
			}//se for o meu turno retorno 1
			else if(id == turno){
				return 1;
			}//se não for meu turno retorno 0
			else if(id != turno){
				return 0;
			}//se eu sou vencedor retorno 2
			else if(vencedor(id) == 1){
				return 2;
			}//se eu sou perdedor retorno 3
			else if(vencedor(id) == 0){
				return 3;
			}//se deu empate retorno 4
			else if(vencedor(id) == 2){
				return 4;
			}//erro retorna -1
			else{
				return -1;
			}
		}
		@Test
		public void testEhMinhaVez() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			assertSame(0, bhaga.registraJogador("Matheus"));
			assertSame(-2, bhaga.ehMinhaVez(0));
			assertSame(1, bhaga.registraJogador("Carol"));
			assertSame(1, bhaga.ehMinhaVez(0));
			bhaga.turno = 1; //fazer algum movimento para trocar o turno
			assertSame(1, bhaga.ehMinhaVez(1));
			//cenario de ganhar o jogo para cabras
			assertSame(2, bhaga.ehMinhaVez(0));
			assertSame(3, bhaga.ehMinhaVez(1));
			//cenario de ganhar o jogo para tigres
			assertSame(3, bhaga.ehMinhaVez(0));
			assertSame(2, bhaga.ehMinhaVez(1));
			//cenario de empate?
			assertSame(4, bhaga.ehMinhaVez(0));
			assertSame(4, bhaga.ehMinhaVez(1));
			//erro
			assertSame(-1, bhaga.ehMinhaVez(2));
			assertSame(-1, bhaga.ehMinhaVez(10));
		}
		
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
		@Override
		public String obtemGrade(int id) throws RemoteException {
			if(jogadores[0] == null || jogadores[1] == null || id != 0 || id != 1){
				return "";
			}
			//ver se o ID do jogador é valido se não retornar erro(string vazia)
			return tabuleiro.toStringForTests();
		}
		@Test
		public void testObtemGrade() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			assertSame("", bhaga.obtemGrade(0));		
			assertSame("", bhaga.obtemGrade(1));
			assertSame(0, bhaga.registraJogador("Matheus"));
			assertSame(1, bhaga.registraJogador("Carol"));
			assertSame("1...2...............3...4", bhaga.obtemGrade(0));
			assertSame("1...2...............3...4", bhaga.obtemGrade(1));
			
			
			
			
			assertSame("", bhaga.obtemGrade(2));
			assertSame("", bhaga.obtemGrade(10));
			assertSame("", bhaga.obtemGrade(-1));
			assertSame("", bhaga.obtemGrade(-10));
			
		}
		
		
		/*6) moveTigre
		 * Recebe: id do usuário (obtido através da chamada registraJogador) e direção relativa da jogada
		 * (0 = para direita; 1 = para direita/abaixo; 2 = para baixo; 3 = para esquerda/abaixo; 4 = para
		 * esquerda; 5 = para esquerda/acima; 6 = para cima; 7 = para direita/acima).
		 * Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
		 * jogada e ocorra o time­out de 30 segundos para envio de jogadas), 1 (tudo certo), 0 (movimento
		 * inválido), -­1 (erro), ­-2 (partida não iniciada: ainda não há dois jogadores registrados na partida),
		 * ­-3 (não é a vez do jogador), ­-4 (não está jogando com o animal correto) ou -­5 (direção inválida).
		 */
		@Override
		public int moveTigre(int id, int tigre, int direcao) throws RemoteException {
			// TODO Auto-generated method stub
			return 0;
		}
		/*7) posicionaCabra
		 * ­Recebe: id do usuário (obtido através da chamada registraJogador) e posição do tabuleiro onde a
		 * ­cabra deve ser posicionada (x e y, ambos variando de 0 a 4, inclusive).
		 * ­Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
		 * ­jogada e ocorra o time­out de 30 segundos para envio de jogadas), 1 (tudo certo), 0 (movimento
		 * ­inválido), -­1 (erro), -­2 (partida não iniciada: ainda não há dois jogadores registrados na partida),
		 * ­­-3 (não é a vez do jogador), ­-4 (não está jogando com o animal correto) ou ­-5 (todas as cabras já
		 * ­foram posicionadas).
		 */
		@Override
		public int posicionaCabra(int id, int x, int y) throws RemoteException {			
//			if (id != algo){
//				return -1;
//			}
			//se o jogador não é cabra manda embora e retorna -4

			//se não é a vez desse jogador retorna -3
			
			//se a partida não iniciou retorna -2
			
			if(proximaCabra > 84){
				return -5;
			}
			if(x > 4 || x < 0 || y > 4 || y < 0 || tabuleiro.getPosicao(x, y) != '.'){
				return 0;
			}
			
			tabuleiro.setPosicao(x, y, (char)proximaCabra++);
			return 1;
			
		}
		/*8) moveCabra
		 * ­Recebe: id do usuário (obtido através da chamada registraJogador), identificador da cabra a ser
		 * ­movimentada (valor de 0 até 19, inclusive, considerando que cada cabra estará em determinada
		 * ­posição do tabuleiro, conforme histórico do jogo) e direção relativa da jogada (0 = para direita; 1
		 * ­= para direita/abaixo; 2 = para baixo; 3 = para esquerda/abaixo; 4 = para esquerda; 5 = para
		 * ­esquerda/acima; 6 = para cima; 7 = para direita/acima).
		 * ­Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
		 * ­jogada e ocorra o time­out de 30 segundos para envio de jogadas), 1 (tudo certo), 0 (movimento
		 * ­inválido), -­1 (erro), ­-2 (partida não iniciada: ainda não há dois jogadores registrados na partida),
		 * ­­-3 (não é a vez do jogador), -­4 (não está jogando com o animal correto), -­5 (ainda não está na
		 * ­fase de movimentação de cabras) ou ­-6 (direção inválida).
		 */	
		@Override
		public String moveCabra(int id, String cabra, int direcao) throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		/*9) obtemOponente
	 	 * Recebe: id do usuário (obtido através da chamada registraJogador)
		 * Retorna: string vazio para erro ou string com o nome do oponente
		 */
		@Override
		public String obtemOponente(int id) throws RemoteException {
			//retorna "" se não existir algum dos jogadores
			if(jogadores[0] == null || jogadores[1] == null){
				return "";
			}			
			//se meu id é o 0 meu oponente é o 1
			if(id == 0){
				return jogadores[1];
			}//se meu id é o 1 meu oponente é o 0
			else if(id == 1){
				return jogadores[0];
			}//nenhum id conhecido
			else{
				return "";
			}
		}
		@Test
		public void testObtemOponente() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			assertSame("", bhaga.obtemOponente(0));		
			assertSame("", bhaga.obtemOponente(1));
			assertSame(0, bhaga.registraJogador("Matheus"));
			assertSame(1, bhaga.registraJogador("Carol"));
			assertSame("Carol", bhaga.obtemOponente(0));
			assertSame("Matheus", bhaga.obtemOponente(1));

			assertSame("", bhaga.obtemOponente(2));
			assertSame("", bhaga.obtemOponente(10));
			assertSame("", bhaga.obtemOponente(100));
			assertSame("", bhaga.obtemOponente(-1));
			assertSame("", bhaga.obtemOponente(-10));
			assertSame("", bhaga.obtemOponente(-100));
		}
}

