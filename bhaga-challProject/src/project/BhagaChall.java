package project;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//import static org.junit.Assert.*;
//import java.util.Scanner;

import org.junit.Test;

public class BhagaChall extends UnicastRemoteObject implements BhagaChallInterface {
	
		private static final long serialVersionUID = -513804057617910473L;
		
		public class Coordenada{			
			int[] x;
			int[] y;
			
			public Coordenada(){
				x = new int[8];
				y = new int[8];
				
				for(int i = 0; i < 8 ; i++){
					x[i] = -1;
					y[i] = -1;
				}
				
			}
			
			public int[] getDirecao(int direcao){
				if(direcao >= 0 && direcao <= 7){
					int[] toReturn = new int[2];
					toReturn[0] = x[direcao];
					toReturn[1] = y[direcao];
					
					return toReturn;
				}
				return null;
			}
			
			public void setPosicao(int direcao, int x, int y){
				if((x >= 0 && x <= 4) && (y >= 0 && y <= 4)){
					this.x[direcao] = x;
					this.y[direcao] = y;
				}
			}
			
			public String toString(){
				String toReturn = "";
				for(int i = 0; i < x.length ; i++){
					toReturn += "d:" + i + " x:" + x[i] + " y:" + y[i] + "\n";
				}
				return toReturn;
			}
		}
	
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
				if((x >= 0 && x <= 4) && (y >= 0 && y <= 4)){
					return board[x][y];
				}
				return ' ';
			}
			
			public void setPosicao(int x, int y, char content){
				if((x >= 0 && x <= 4) && (y >= 0 && y <= 4)){
					board[x][y] = content;
				}
			}
			
			public int[] findTigre(int tigre){
				int[] xy = new int[2];
				//System.out.println(tigre);
				//System.out.println((char)tigre);
				for(int i = 0; i < board[0].length; i++){
					for(int j = 0; j < board[0].length ; j++){
						if(board[i][j] == (char) tigre){
							xy[0] = i; 
							xy[1] = j;
							return xy;
						}
					}
				}
				return null;
			}
			
			public int[] findCabra(int cabra) {
				int[] xy = new int[2];
				
				for(int i = 0; i < board[0].length; i++){
					for(int j = 0; j < board[0].length ; j++){
						if(board[i][j] == (char) cabra){
							xy[0] = i; 
							xy[1] = j;
							return xy;
						}
					}
				}
				return null;
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

		public class Jogador{
			
			private int ID;
			private String nome;
			//0 cabra 1 tigre
			private int jogador;
			
			//private long tempoInicio;
			
			//private long tempoJogada;
			
			public Jogador(int id, String n, int jogo){
				ID = id;
				nome = n;
				jogador = jogo;
				//tempoJogada = 0;
			}
			
			public int getID(){
				return ID;
			}
			
			public String getNome(){
				return nome;
			}
			
			public int getMove(){
				return jogador;
			}
			
		}
		
		private Tabuleiro tabuleiro;
		private Jogador[] jogadores;
		private int turno;
		private Coordenada[][] movesSemPulo;
		private Coordenada[][] movesComPulo;
		private int proximaCabra;
		private int cabrasCapturadas;
		private static int ID = 0;
		
		public BhagaChall() throws RemoteException{			
			tabuleiro = new Tabuleiro();
			tabuleiro.setPosicao(0, 0, '1');
			tabuleiro.setPosicao(0, 4, '2');
			tabuleiro.setPosicao(4, 0, '3');
			tabuleiro.setPosicao(4, 4, '4');
			jogadores = new Jogador[2];		
			turno = 0;			
			proximaCabra = 65;			
			cabrasCapturadas = 0;			
			iniciaMovesSemPulo();
			iniciaMovesComPulo();
		}
		
		//inicia as jogadas possiveis sem pulo
		private void iniciaMovesSemPulo(){
			//inicia a matrix
			movesSemPulo = new Coordenada[5][5];
			
			//posição x=0 y=0
			Coordenada zeroZero = new Coordenada();
			zeroZero.setPosicao(0, 0, 1);
			zeroZero.setPosicao(1, 1, 1);
			zeroZero.setPosicao(2, 1, 0);
			movesSemPulo[0][0] = zeroZero;
			
			//posição x=0 y=1
			Coordenada zeroUm = new Coordenada();
			zeroUm.setPosicao(0, 0, 2);
			zeroUm.setPosicao(2, 1, 1);
			zeroUm.setPosicao(4, 0, 0);
			movesSemPulo[0][1] = zeroUm;
			
			//posição x=0 y=2
			Coordenada zeroDois = new Coordenada();
			zeroDois.setPosicao(0, 0, 3);
			zeroDois.setPosicao(1, 1, 3);
			zeroDois.setPosicao(2, 1, 2);
			zeroDois.setPosicao(3, 1, 1);
			zeroDois.setPosicao(4, 0, 1);
			movesSemPulo[0][2] = zeroDois;
			
			//posição x=0 y=3
			Coordenada zeroTres = new Coordenada();
			zeroTres.setPosicao(0, 0, 4);
			zeroTres.setPosicao(2, 1, 3);
			zeroTres.setPosicao(4, 0, 2);
			movesSemPulo[0][3] = zeroTres;
			
			//posição x=0 y=4
			Coordenada zeroQuatro = new Coordenada();
			zeroQuatro.setPosicao(2, 1, 4);
			zeroQuatro.setPosicao(3, 1, 3);
			zeroQuatro.setPosicao(4, 0, 3);
			movesSemPulo[0][4] = zeroQuatro;
			
			//posição x=1 y=0
			Coordenada umZero = new Coordenada();
			umZero.setPosicao(0, 1, 1);
			umZero.setPosicao(2, 2, 0);
			umZero.setPosicao(6, 0, 0);
			movesSemPulo[1][0] = umZero;
			
			//posição x=1 y=1
			Coordenada umUm = new Coordenada();
			umUm.setPosicao(0, 1, 2);
			umUm.setPosicao(1, 2, 2);
			umUm.setPosicao(2, 2, 1);	
			umUm.setPosicao(3, 2, 0);
			umUm.setPosicao(4, 1, 0);
			umUm.setPosicao(5, 0, 0);
			umUm.setPosicao(6, 0, 1);
			umUm.setPosicao(7, 0, 2);
			movesSemPulo[1][1] = umUm;
			
			//posição x=1 y=2
			Coordenada umDois = new Coordenada();
			umDois.setPosicao(0, 1, 3);
			umDois.setPosicao(2, 2, 2);
			umDois.setPosicao(4, 1, 1);	
			umDois.setPosicao(6, 0, 2);
			movesSemPulo[1][2] = umDois;
			
			//posição x=1 y=3
			Coordenada umTres = new Coordenada();
			umTres.setPosicao(0, 1, 4);
			umTres.setPosicao(1, 2, 4);
			umTres.setPosicao(2, 2, 3);	
			umTres.setPosicao(3, 2, 2);
			umTres.setPosicao(4, 1, 2);
			umTres.setPosicao(5, 0, 2);
			umTres.setPosicao(6, 0, 3);
			umTres.setPosicao(7, 0, 4);
			movesSemPulo[1][3] = umTres;
			
			//posição x=1 y=4
			Coordenada umQuatro = new Coordenada();
			umQuatro.setPosicao(2, 2, 4);
			umQuatro.setPosicao(4, 1, 3);	
			umQuatro.setPosicao(6, 0, 4);
			movesSemPulo[1][4] = umQuatro;
			
			//posição x=2 y=0
			Coordenada doisZero = new Coordenada();
			doisZero.setPosicao(0, 2, 1);
			doisZero.setPosicao(1, 3, 1);	
			doisZero.setPosicao(2, 3, 0);
			doisZero.setPosicao(6, 1, 0);
			doisZero.setPosicao(7, 1, 1);
			movesSemPulo[2][0] = doisZero;
			
			//posição x=2 y=1
			Coordenada doisUm = new Coordenada();
			doisUm.setPosicao(0, 2, 2);
			doisUm.setPosicao(2, 3, 1);	
			doisUm.setPosicao(4, 2, 0);
			doisUm.setPosicao(6, 1, 1);
			movesSemPulo[2][1] = doisUm;
			
			//posição x=2 y=2
			Coordenada doisDois = new Coordenada();
			doisDois.setPosicao(0, 2, 3);
			doisDois.setPosicao(1, 3, 3);	
			doisDois.setPosicao(2, 3, 2);
			doisDois.setPosicao(3, 3, 1);
			doisDois.setPosicao(4, 2, 1);
			doisDois.setPosicao(5, 1, 1);
			doisDois.setPosicao(6, 1, 2);
			doisDois.setPosicao(7, 1, 3);
			movesSemPulo[2][2] = doisDois;
			
			//posição x=2 y=3
			Coordenada doisTres = new Coordenada();
			doisTres.setPosicao(0, 2, 4);
			doisTres.setPosicao(2, 3, 3);	
			doisTres.setPosicao(4, 2, 2);
			doisTres.setPosicao(6, 1, 3);
			movesSemPulo[2][3] = doisTres;
			
			//posição x=2 y=4
			Coordenada doisQuatro = new Coordenada();
			doisQuatro.setPosicao(2, 3, 4);
			doisQuatro.setPosicao(3, 3, 3);	
			doisQuatro.setPosicao(4, 2, 3);
			doisQuatro.setPosicao(5, 1, 3);
			doisQuatro.setPosicao(6, 1, 4);
			movesSemPulo[2][4] = doisQuatro;
			
			//posição x=3 y=0
			Coordenada tresZero = new Coordenada();
			tresZero.setPosicao(0, 3, 1);
			tresZero.setPosicao(2, 4, 0);	
			tresZero.setPosicao(6, 2, 0);
			movesSemPulo[3][0] = tresZero;
			
			//posição x=3 y=1
			Coordenada tresUm = new Coordenada();
			tresUm.setPosicao(0, 3, 2);
			tresUm.setPosicao(1, 4, 2);	
			tresUm.setPosicao(2, 4, 1);
			tresUm.setPosicao(3, 4, 0);
			tresUm.setPosicao(4, 3, 0);	
			tresUm.setPosicao(5, 2, 0);
			tresUm.setPosicao(6, 2, 1);
			tresUm.setPosicao(7, 2, 2);
			movesSemPulo[3][1] = tresUm;
			
			//posição x=3 y=2
			Coordenada tresDois = new Coordenada();
			tresDois.setPosicao(0, 3, 3);
			tresDois.setPosicao(2, 4, 2);	
			tresDois.setPosicao(4, 3, 1);
			tresDois.setPosicao(6, 2, 2);
			movesSemPulo[3][2] = tresDois;
			
			//posição x=3 y=3
			Coordenada tresTres = new Coordenada();
			tresTres.setPosicao(0, 3, 4);
			tresTres.setPosicao(1, 4, 4);	
			tresTres.setPosicao(2, 4, 3);
			tresTres.setPosicao(3, 4, 2);
			tresTres.setPosicao(4, 3, 2);	
			tresTres.setPosicao(5, 2, 2);
			tresTres.setPosicao(6, 2, 3);
			tresTres.setPosicao(7, 2, 4);
			movesSemPulo[3][3] = tresTres;
			
			//posição x=3 y=4
			Coordenada tresQuatro = new Coordenada();
			tresQuatro.setPosicao(2, 4, 4);
			tresQuatro.setPosicao(4, 3, 3);	
			tresQuatro.setPosicao(6, 2, 4);
			movesSemPulo[3][4] = tresQuatro;
			
			//posição x=4 y=0
			Coordenada quatroZero = new Coordenada();
			quatroZero.setPosicao(0, 4, 1);
			quatroZero.setPosicao(6, 3, 0);	
			quatroZero.setPosicao(7, 3, 1);
			movesSemPulo[4][0] = quatroZero;
			
			//posição x=4 y=1
			Coordenada quatroUm = new Coordenada();
			quatroUm.setPosicao(0, 4, 2);
			quatroUm.setPosicao(4, 4, 0);	
			quatroUm.setPosicao(6, 3, 1);
			movesSemPulo[4][1] = quatroUm;
			
			//posição x=4 y=2
			Coordenada quatroDois = new Coordenada();
			quatroDois.setPosicao(0, 4, 3);
			quatroDois.setPosicao(4, 4, 1);	
			quatroDois.setPosicao(5, 3, 1);
			quatroDois.setPosicao(6, 3, 2);	
			quatroDois.setPosicao(7, 3, 3);
			movesSemPulo[4][2] = quatroDois;
			
			//posição x=4 y=3
			Coordenada quatroTres = new Coordenada();
			quatroTres.setPosicao(0, 4, 4);
			quatroTres.setPosicao(4, 4, 2);	
			quatroTres.setPosicao(6, 3, 3);
			movesSemPulo[4][3] = quatroTres;
			
			//posição x=4 y=4
			Coordenada quatroQuatro = new Coordenada();
			quatroQuatro.setPosicao(4, 4, 3);
			quatroQuatro.setPosicao(5, 3, 3);	
			quatroQuatro.setPosicao(6, 3, 4);
			movesSemPulo[4][4] = quatroQuatro;
		}
		
		//inicia as jogadaspossiveis com pulo
		private void iniciaMovesComPulo(){
			//inicia a matrix
			movesComPulo = new Coordenada[5][5];
			
			//posição x=0 y=0
			Coordenada zeroZero = new Coordenada();
			zeroZero.setPosicao(0, 0, 2);
			zeroZero.setPosicao(1, 2, 2);
			zeroZero.setPosicao(2, 2, 0);
			movesComPulo[0][0] = zeroZero;
			
			//posição x=0 y=1
			Coordenada zeroUm = new Coordenada();
			zeroUm.setPosicao(0, 0, 3);
			zeroUm.setPosicao(2, 2, 1);
			movesComPulo[0][1] = zeroUm;
			
			//posição x=0 y=2
			Coordenada zeroDois = new Coordenada();
			zeroDois.setPosicao(0, 0, 4);
			zeroDois.setPosicao(1, 2, 4);
			zeroDois.setPosicao(2, 2, 2);
			zeroDois.setPosicao(3, 2, 0);
			zeroDois.setPosicao(4, 0, 0);
			movesComPulo[0][2] = zeroDois;
			
			//posição x=0 y=3
			Coordenada zeroTres = new Coordenada();
			zeroTres.setPosicao(2, 2, 3);
			zeroTres.setPosicao(4, 0, 1);
			movesComPulo[0][3] = zeroTres;
			
			//posição x=0 y=4
			Coordenada zeroQuatro = new Coordenada();
			zeroQuatro.setPosicao(2, 2, 4);
			zeroQuatro.setPosicao(3, 2, 2);
			zeroQuatro.setPosicao(4, 0, 2);
			movesComPulo[0][4] = zeroQuatro;
			
			//posição x=1 y=0
			Coordenada umZero = new Coordenada();
			umZero.setPosicao(0, 1, 2);
			umZero.setPosicao(2, 3, 0);
			movesComPulo[1][0] = umZero;
			
			//posição x=1 y=1
			Coordenada umUm = new Coordenada();
			umUm.setPosicao(0, 1, 3);
			umUm.setPosicao(1, 3, 3);
			umUm.setPosicao(2, 3, 1);
			movesComPulo[1][1] = umUm;
			
			//posição x=1 y=2
			Coordenada umDois = new Coordenada();
			umDois.setPosicao(0, 1, 4);
			umDois.setPosicao(2, 3, 2);
			umDois.setPosicao(4, 1, 0);
			movesComPulo[1][2] = umDois;
			
			//posição x=1 y=3
			Coordenada umTres = new Coordenada();
			umTres.setPosicao(2, 3, 3);
			umTres.setPosicao(3, 3, 1);
			umTres.setPosicao(4, 1, 1);
			movesComPulo[1][3] = umTres;
			
			//posição x=1 y=4
			Coordenada umQuatro = new Coordenada();
			umQuatro.setPosicao(2, 3, 4);
			umQuatro.setPosicao(4, 1, 2);
			movesComPulo[1][4] = umQuatro;
			
			//posição x=2 y=0
			Coordenada doisZero = new Coordenada();
			doisZero.setPosicao(0, 2, 2);
			doisZero.setPosicao(1, 4, 2);
			doisZero.setPosicao(2, 4, 0);
			doisZero.setPosicao(6, 0, 0);
			doisZero.setPosicao(7, 0, 2);
			movesComPulo[2][0] = doisZero;
			
			//posição x=2 y=1
			Coordenada doisUm = new Coordenada();
			doisUm.setPosicao(0, 2, 3);
			doisUm.setPosicao(2, 4, 1);
			doisUm.setPosicao(6, 0, 1);
			movesComPulo[2][1] = doisUm;
			
			//posição x=2 y=2
			Coordenada doisDois = new Coordenada();
			doisDois.setPosicao(0, 2, 4);
			doisDois.setPosicao(1, 4, 4);
			doisDois.setPosicao(2, 4, 2);
			doisDois.setPosicao(3, 4, 0);
			doisDois.setPosicao(4, 2, 0);
			doisDois.setPosicao(5, 0, 0);
			doisDois.setPosicao(6, 0, 2);
			doisDois.setPosicao(7, 0, 4);
			movesComPulo[2][2] = doisDois;
			
			//posição x=2 y=3
			Coordenada doisTres = new Coordenada();
			doisTres.setPosicao(2, 4, 3);
			doisTres.setPosicao(4, 2, 1);
			doisTres.setPosicao(6, 0, 3);
			movesComPulo[2][3] = doisTres;
			
			//posição x=2 y=4
			Coordenada doisQuatro = new Coordenada();
			doisQuatro.setPosicao(2, 4, 4);
			doisQuatro.setPosicao(3, 4, 2);
			doisQuatro.setPosicao(4, 2, 2);
			doisQuatro.setPosicao(5, 0, 2);
			doisQuatro.setPosicao(6, 0, 4);
			movesComPulo[2][4] = doisQuatro;
			
			//posição x=3 y=0
			Coordenada tresZero = new Coordenada();
			tresZero.setPosicao(0, 3, 2);
			tresZero.setPosicao(6, 1, 0);
			movesComPulo[3][0] = tresZero;
			
			//posição x=3 y=1
			Coordenada tresUm = new Coordenada();
			tresUm.setPosicao(0, 3, 3);
			tresUm.setPosicao(6, 1, 1);
			tresUm.setPosicao(7, 1, 3);
			movesComPulo[3][1] = tresUm;
			
			//posição x=3 y=2
			Coordenada tresDois = new Coordenada();
			tresDois.setPosicao(0, 3, 4);
			tresDois.setPosicao(4, 3, 0);
			tresDois.setPosicao(6, 1, 2);
			movesComPulo[3][2] = tresDois;
			
			//posição x=3 y=3
			Coordenada tresTres = new Coordenada();
			tresTres.setPosicao(4, 3, 1);
			tresTres.setPosicao(5, 1, 1);
			tresTres.setPosicao(6, 1, 3);
			movesComPulo[3][3] = tresTres;
			
			//posição x=3 y=4
			Coordenada tresQuatro = new Coordenada();
			tresQuatro.setPosicao(4, 3, 2);
			tresQuatro.setPosicao(6, 1, 4);
			movesComPulo[3][4] = tresQuatro;
			
			//posição x=4 y=0
			Coordenada quatroZero = new Coordenada();
			quatroZero.setPosicao(0, 4, 2);
			quatroZero.setPosicao(6, 2, 0);
			quatroZero.setPosicao(7, 2, 2);
			movesComPulo[4][0] = quatroZero;
			
			//posição x=4 y=1
			Coordenada quatroUm = new Coordenada();
			quatroUm.setPosicao(0, 4, 3);
			quatroUm.setPosicao(6, 2, 2);
			movesComPulo[4][1] = quatroUm;
			
			//posição x=4 y=2
			Coordenada quatroDois = new Coordenada();
			quatroDois.setPosicao(0, 4, 4);
			quatroDois.setPosicao(4, 4, 0);
			quatroDois.setPosicao(5, 2, 0);
			quatroDois.setPosicao(6, 2, 2);
			quatroDois.setPosicao(7, 2, 4);
			movesComPulo[4][2] = quatroDois;
			
			//posição x=4 y=3
			Coordenada quatroTres = new Coordenada();
			quatroTres.setPosicao(4, 4, 1);
			quatroTres.setPosicao(6, 2, 3);
			movesComPulo[4][3] = quatroTres;
		
			//posição x=4 y=4
			Coordenada quatroQuatro = new Coordenada();
			quatroQuatro.setPosicao(4, 4, 2);
			quatroQuatro.setPosicao(5, 2, 2);
			quatroQuatro.setPosicao(6, 2, 4);
			movesComPulo[4][4] = quatroQuatro;
		}
		
		//verifica se alguem ganhou
		//retorna 0 se o id perdeu
		//		  1 se o id ganhou
		private int vencedor(int id){
			Jogador player = getJogador(id);
			if(player == null){return -1;}
			
			if(cabrasCapturadas == 5){
				if(player.getMove() == 0){
					return 0;
				}else if (player.getMove() == 1){
					return 1;
				}
			}else if(tigrePreso()){
				if(player.getMove() == 0){
					return 1;
				}else if(player.getMove() == 1){
					return 0;
				}
			}
			return -1;
		}
		
		private boolean tigrePreso() {
			//para cada tigre teste se ele tem como se mexer
			for(int i = 0 ;i < 4; i++){
				int[] posicaoTigre = tabuleiro.findTigre(i+49);
				Coordenada movimentoSemPulo = movesSemPulo[posicaoTigre[0]][posicaoTigre[1]];
				Coordenada movimentoComPulo = movesComPulo[posicaoTigre[0]][posicaoTigre[1]];

				for(int j = 0; j < 7 ; j++){
					int[] movimentoTigreSemPulo = movimentoSemPulo.getDirecao(j);
					if(movimentoTigreSemPulo[0] == -1){// && movimentoTigreComPulo[0] == -1){
						continue;
					}else if(!temCabra(movimentoTigreSemPulo[0], movimentoTigreSemPulo[1]) && !temTigre(movimentoTigreSemPulo[0], movimentoTigreSemPulo[1])){
						return false;
					}
				}
				for(int j = 0; j < 7 ; j++){
					int[] movimentoTigreComPulo = movimentoComPulo.getDirecao(j);
					if(movimentoTigreComPulo[0] == -1){// && movimentoTigreComPulo[0] == -1){
						continue;
					}else if(!temCabra(movimentoTigreComPulo[0], movimentoTigreComPulo[1]) && temTigre(movimentoTigreComPulo[0], movimentoTigreComPulo[1])){
						return false;
					}
				}
			}
			return true;
		}

		private boolean temCabra(int x, int y){
			if(tabuleiro.getPosicao(x, y) >= 65 && tabuleiro.getPosicao(x, y) <= 84 ){
				return true;
			}else{
				return false;
			}
		}
		private boolean temTigre(int x, int y) {
			if(tabuleiro.getPosicao(x, y) >= 49 && tabuleiro.getPosicao(x, y) <= 52 ){
				return true;
			}else{
				return false;
			}
		}
		
		private Jogador getJogador(int id){
			if(jogadores[0].getID() == id){
				return jogadores[0];
			}else if(jogadores[1].getID() == id){
				return jogadores[1];
			}
			
			return null;
		}
		
		/* 1)registraJogador
	 	* Recebe: string com o nome do usuário/jogador
	 	* Retorna: id (valor inteiro) do usuário (que corresponde a um número de identificação único para
	 	* este usuário durante uma partida), -­1 se este usuário já está  cadastrado ou ­-2 se o número
	 	* máximo de jogadores tiver sido atingido 
	 	*/
		@Override
		public synchronized int registraJogador(String nome) throws RemoteException {			
			//retorna id do jogador
			if(jogadores[0] == null){
				jogadores[0] = new Jogador(ID++,nome,0);
				//jogadores[0].tempoInicio = System.currentTimeMillis();
				return jogadores[0].getID();					
			}else if(jogadores[1] == null){
				//se o usuario já esta cadastrado retorna -1		
				if(jogadores[0].getNome().equals(nome)) return -1; 
				jogadores[1] = new Jogador(ID++,nome,1);
				return jogadores[1].getID();
			}
			//se não tem mais espaço pra jogar
			else{
				return -2;
			}
		}
		@Test
		public void testRegistraJogador() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			assertSame(BhagaChall.ID, bhaga.registraJogador("Matheus"));
			assertSame(-1, bhaga.registraJogador("Matheus"));	
			assertSame(BhagaChall.ID, bhaga.registraJogador("Carol"));
			assertSame(-2, bhaga.registraJogador("Maria"));			
			assertSame(-2, bhaga.registraJogador("Pedro"));
			assertSame(-2, bhaga.registraJogador("Valnei"));
		}
		
		//para implementar o temporizador utilizar thread que verifica de um em um segundo e quando atingir trinta encerra
		/* 2) encerraPartida
		 * Recebe: id do usuário (obtido através da chamada registraJogador)
		 * Retorna: -­1 (erro), 0 (ok) || zera os dados da partida e o jogador adversario ganha por wo 
		 */
		@Override
		public int encerraPartida(int id) throws RemoteException {
			if(jogadores[0].getID() == id){
				return 0;
			}else if(jogadores[1].getID() == id){
				return 0;
			}
			return -1;
		}
		@Test
		public void testEncerraPartida() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			int idM = bhaga.registraJogador("Matheus");
			bhaga.registraJogador("Carol");
			
			assertSame(0, bhaga.encerraPartida(idM));
			assertSame(null, bhaga.jogadores[0]);
		}
				
		/* 3) temPartida
		 * Recebe: id do usuário (obtido através da chamada registraJogador)
		 * Retorna: ­-2 (tempo de espera esgotado), -­1 (erro), 0 (ainda não há partida), 1 (sim, há partida e o
		 * jogador inicia jogando com cabras, identificadas, por exemplo, com letras de “A” até “T”) ou 2
		 * (sim, há partida e o jogador é o segundo a jogar, com os tigres, identificados, por exemplo, com
		 * dígitos de “1” até “4”)
		 */
		@Override
		public synchronized int temPartida(int id) throws RemoteException {
			
			Jogador player = getJogador(id);
			if(player == null){return -1;}	
			
//			//tempo de espera esgotado retorna -2
//			if(System.currentTimeMillis() - player.tempoInicio > 120*1000){
//				return -2;
//			}
			
			//se ainda não tem partida
			if(jogadores[0] == null || jogadores[1] == null){
				return 0;
			}			
					
			//se o jogador joga com as cabras
			if(player.getMove() == 0){
				return 1;
			}//se o jogador joga com os tigres
			else if(player.getMove() == 1){
				return 2;
			}
			
			//se erro retorna -1
			return -1;		
		}
		@Test
		public void testTemPartida() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			assertSame(0, bhaga.temPartida(0));			
			assertSame(0, bhaga.temPartida(1));
			int idM = bhaga.registraJogador("Matheus");
			assertSame(0, bhaga.temPartida(idM));
			int idC = bhaga.registraJogador("Carol");
			assertSame(1, bhaga.temPartida(idM));			
			assertSame(2, bhaga.temPartida(idC));

			assertSame(-1, bhaga.temPartida(-10));
			assertSame(-1, bhaga.temPartida(-1));	
			assertSame(-1, bhaga.temPartida(2));			
			assertSame(-1, bhaga.temPartida(10));
		}
		
		/*4) ehMinhaVez
		 * Recebe: id do usuário (obtido através da chamada registraJogador)
	     * Retorna: -­2 (erro: ainda não há 2 jogadores registrados na partida), ­-1 (erro), 0 (não), 1 (sim), 2
	     * (é o vencedor), 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por WO)
		 */
		@Override
		public synchronized int ehMinhaVez(int id) throws RemoteException {
			
			//se não tem um jogador retorna -2
			if(jogadores[0] == null || jogadores[1] == null){
				return -2;
			}
			if(vencedor(id) != -1){
				if(vencedor(id) == 1){
					return 2;
				}
				if(vencedor(id) == 0){
					return 3;
				}
			}
			
			Jogador player = getJogador(id);
			if(player == null){return -1;}			
						
			if(player.getMove() == turno){
//				if(player.tempoJogada != 0) player.tempoJogada = System.currentTimeMillis();
//				if(System.currentTimeMillis() - player.tempoJogada > 30 * 100){
//					return 6;
//				}
				return 1;
			}//se não for meu turno retorno 0
			else if(player.getMove() != turno && (player.getMove() == 1 || player.getMove() == 0)){
				
//				if(jogadores[0].equals(player)){
//					System.out.println(System.currentTimeMillis() + " : " + jogadores[1].tempoJogada);
//					if(System.currentTimeMillis() - jogadores[1].tempoJogada > 30 * 100){
//						return 5;
//					}
//				}else if(jogadores[1].equals(player)){
//					System.out.println(System.currentTimeMillis() + " : " + jogadores[1].tempoJogada);
//					if(System.currentTimeMillis() - jogadores[0].tempoJogada > 30 * 100){
//						return 5;
//					}
//				}
				
				return 0;
			}//erro retorna -1
			return -1;

		}
		@Test
		public void testEhMinhaVez() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		
			int idM = bhaga.registraJogador("Matheus");
			assertSame(-2, bhaga.ehMinhaVez(idM));
			int idC = bhaga.registraJogador("Carol");
			assertSame(1, bhaga.ehMinhaVez(idM));
			bhaga.turno = 1;
			assertSame(1, bhaga.ehMinhaVez(idC));
			//cenario de ganhar o jogo para cabras
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 0, 1);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 0, 2);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 1, 1);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 2, 2);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 1, 0);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 2, 0);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 0, 3);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 1, 3);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 1, 4);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 2, 4);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 3, 0);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 3, 1);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 4, 1);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 4, 2);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 4, 3);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 3, 3);
			bhaga.turno = 0;
			bhaga.posicionaCabra(idM, 3, 4);
			assertSame(2, bhaga.ehMinhaVez(idM));
			assertSame(3, bhaga.ehMinhaVez(idC));
			//cenario de ganhar o jogo para tigres
			bhaga.tabuleiro.setPosicao(0, 1, '.');
			bhaga.cabrasCapturadas = 5;
			assertSame(3, bhaga.ehMinhaVez(idM));
			assertSame(2, bhaga.ehMinhaVez(idC));
			//erro
			assertSame(-1, bhaga.ehMinhaVez(-2));
			assertSame(-1, bhaga.ehMinhaVez(-10));
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
			Jogador player = getJogador(id);
			//ver se o ID do jogador é valido se não retornar erro(string vazia)
			if(jogadores[0] == null || jogadores[1] == null || player == null){
				return "";
			}
			return tabuleiro.toString();
		}
		@Test
		public void testObtemGrade() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();
			
			int idM = bhaga.registraJogador("Matheus");
			int idC = bhaga.registraJogador("Carol");
			assertEquals("1...2...............3...4", bhaga.obtemGrade(idM));
			assertEquals("1...2...............3...4", bhaga.obtemGrade(idC));
			//fazer aqui algumas jogadas e ver o resultado esperado
			assertEquals("", bhaga.obtemGrade(-1));
			assertEquals("", bhaga.obtemGrade(-10));
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
		public synchronized int moveTigre(int id, int tigre, int direcao) throws RemoteException {
			// se ainda não tem partida
			if(temPartida(id) == 0){
				return -2;
			}//esta jogando com as cabras
			else if(temPartida(id) == 1){
				return -4;
			}//não é a vez dos tigres jogarem
			else if	(ehMinhaVez(id) == 0){
				return -3;
			}
			if(tigre < 1 || tigre > 4){
				return -1;
			}
			
			int[] posicaoTigre = tabuleiro.findTigre(tigre+48);
			if(posicaoTigre != null){
				Coordenada movimento = movesSemPulo[posicaoTigre[0]][posicaoTigre[1]];
				int[] movimentoTigreSemPulo = movimento.getDirecao(direcao);
				//System.out.println(movimentoTigreSemPulo[0] +"-"+ movimentoTigreSemPulo[1]);
				//direcao invalido
				if(movimentoTigreSemPulo[0] == -1){ 
					return -5;
				}//tem um tigre na posicao que deseja ir se tiver retorna movimento invalido
				else if(temTigre(movimentoTigreSemPulo[0], movimentoTigreSemPulo[1])){
					return 0;
				}//se o tabuleiro tah livre movimenta o tigre para essa posicao
				else if(tabuleiro.getPosicao(movimentoTigreSemPulo[0], movimentoTigreSemPulo[1]) == '.'){
					//tira a posição do tigre
					tabuleiro.setPosicao(posicaoTigre[0], posicaoTigre[1], '.');
					//adiciona ele no novo lugar
					tabuleiro.setPosicao(movimentoTigreSemPulo[0], movimentoTigreSemPulo[1], (char)(tigre+48));
					turno = 0;
					return 1;
				}//verifica se tem uma cabra na posição que deseja ir
				else if(temCabra(movimentoTigreSemPulo[0], movimentoTigreSemPulo[1])){
					//tem uma cabra então tenta pular
					movimento = movesComPulo[posicaoTigre[0]][posicaoTigre[1]];
					int[] movimentoTigreComPulo = movimento.getDirecao(direcao);
					//se nao da pra pular retorna direcao invalida
					if(movimentoTigreComPulo[0] == -1){
						return -5;
					}// caso tenha cabra ou tigre ali
					else if(temCabra(movimentoTigreComPulo[0], movimentoTigreComPulo[1]) || temTigre(movimentoTigreComPulo[0], movimentoTigreComPulo[1])){
						return 0;
					}//se tem a possibilidade de pular
					else{
						//tira a posicao do tigre
						tabuleiro.setPosicao(posicaoTigre[0], posicaoTigre[1], '.');
						//tira a cabra
						tabuleiro.setPosicao(movimentoTigreSemPulo[0], movimentoTigreSemPulo[1], '.');
						//coloca o tigre na posicao do pulo					
						tabuleiro.setPosicao(movimentoTigreComPulo[0], movimentoTigreComPulo[1], (char)(tigre+48));
						//atualiza o turno e as cabras comidas
						turno = 0;
						cabrasCapturadas++;
						return 1;
					}
				}	
			}
			return -1;
		}
		@Test
		public void testMoveTigre() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();	

			assertSame(-2, bhaga.moveTigre(0, 1, 0));

			int idM = bhaga.registraJogador("Matheus");

			assertSame(-2, bhaga.moveTigre(idM, 1, 0));

			int idC = bhaga.registraJogador("Carol");
			
			assertSame(-3, bhaga.moveTigre(idC, 1, 0));
			assertSame(-3, bhaga.moveTigre(idC, 2, 4));
			assertSame(-3, bhaga.moveTigre(idC, 3, 0));
			assertSame(-3, bhaga.moveTigre(idC, 4, 4));

			assertSame(-4, bhaga.moveTigre(idM, 1, 0));
			assertSame(-4, bhaga.moveTigre(idM, 1, 2));

			bhaga.posicionaCabra(idM, 0, 2);
			assertSame(-1, bhaga.moveTigre(idC, 5, 0));
			assertSame(-1, bhaga.moveTigre(idC, -1, 0));
			assertSame(-1, bhaga.moveTigre(idC, 10, 0));
			assertSame(-1, bhaga.moveTigre(idC, -10, 0));

			assertSame(1, bhaga.moveTigre(idC, 4, 6));
			bhaga.posicionaCabra(idM, 0, 1);

			bhaga.posicionaCabra(idM, 0, 2);		
			assertSame(0, bhaga.moveTigre(idC, 1, 0));			
			assertSame(-5, bhaga.moveTigre(idC, 1, 6));
			assertSame(-5, bhaga.moveTigre(idC, 2, 0));
			assertSame(-5, bhaga.moveTigre(idC, 3, 4));
			assertSame(-5, bhaga.moveTigre(idC, 4, 0));
			assertSame(1, bhaga.moveTigre(idC, 1, 2));
			bhaga.posicionaCabra(idM, 4, 1);
			assertSame(1, bhaga.moveTigre(idC, 3, 0));
			
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
		public synchronized int posicionaCabra(int id, int x, int y) throws RemoteException {			
			Jogador player = getJogador(id);
			if(player == null){return -1;}	
			//se o jogador não é cabra manda embora e retorna -4
			else if(player.getMove() == 1){
				return -4; 
			}//se não é a vez desse jogador retorna -3
			else if(ehMinhaVez(id) == 0){
				return -3;
			}//se a partida não iniciou retorna -2
			else if(ehMinhaVez(id) == -2){
				return -2;
			}
			
			//todas as cabras já foram posicionadas
			if(proximaCabra > 84){
				return -5;
			}//movimento invalido
			if(x > 4 || x < 0 || y > 4 || y < 0 || tabuleiro.getPosicao(x, y) != '.'){
				return 0;
			}
			//deu tudo certo faz a jogada, atualiza o turno e retorna 1
			tabuleiro.setPosicao(x, y, (char)proximaCabra++);
			turno = 1;
			return 1;
			
		}
		@Test
		public void testPosicionaCabra() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();	
					
			int idM = bhaga.registraJogador("Matheus");
			assertSame(-2, bhaga.posicionaCabra(idM, 0, 0));
			int idC = bhaga.registraJogador("Carol");

			assertSame(-1, bhaga.posicionaCabra(-10, 0, 0));
			assertSame(-1, bhaga.posicionaCabra(-1, 0, 0));

			assertSame(-4, bhaga.posicionaCabra(idC, 0, 0));
			
			//jogada do trigre
			bhaga.turno = 1;
			assertSame(-3, bhaga.posicionaCabra(idM, 0, 0));
			bhaga.turno = 0;
			
			//adicionar todas as cabras
			bhaga.proximaCabra = 85;
			assertSame(-5, bhaga.posicionaCabra(idM, 0, 0));
			bhaga.proximaCabra = 65;
			
			assertSame(0, bhaga.posicionaCabra(idM, 5, 0));
			assertSame(0, bhaga.posicionaCabra(idM, -1, 0));
			assertSame(0, bhaga.posicionaCabra(idM, 0, 5));
			assertSame(0, bhaga.posicionaCabra(idM, 0, -1));
			assertSame(0, bhaga.posicionaCabra(idM, 4, 0));
			assertSame(0, bhaga.posicionaCabra(idM, 4, 4));
			
			assertSame(1, bhaga.posicionaCabra(idM, 2, 2));
			assertEquals("1...2.......A.......3...4", bhaga.obtemGrade(idM));
			assertSame(1, bhaga.turno);
			bhaga.turno = 0;
			assertSame(1, bhaga.posicionaCabra(idM, 3, 3));
			assertEquals("1...2.......A.....B.3...4", bhaga.obtemGrade(idM));
			assertSame(1, bhaga.turno);
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
		public synchronized int moveCabra(int id, int cabra, int direcao) throws RemoteException {
			// se ainda não tem partida
			if(temPartida(id) == 0){
				return -2;
			}//esta jogando com os tigres
			else if(temPartida(id) == 2){
				return -4;
			}//não é a vez das cabras jogarem
			else if	(ehMinhaVez(id) == 0){
				return -3;
			}else if(proximaCabra < 85){
				return -5;
			}
			int[] posicaoCabra = tabuleiro.findCabra(cabra);
			if(posicaoCabra != null){
				Coordenada movimento = movesSemPulo[posicaoCabra[0]][posicaoCabra[1]];
				int[] movimentoCabra = movimento.getDirecao(direcao);
				//movimento invalido ou se tem um tigre ou uma cabra na posicao que deseja ir retorna movimento invalido
				if(movimentoCabra[0] == -1 || temTigre(movimentoCabra[0], movimentoCabra[1]) || temCabra(movimentoCabra[0], movimentoCabra[1])){
					return -6;
				}//se o tabuleiro tah livre movimenta o tigre para essa posicao
				else if(tabuleiro.getPosicao(movimentoCabra[0], movimentoCabra[1]) == '.'){
					//tira a posição da cabra
					tabuleiro.setPosicao(posicaoCabra[0], posicaoCabra[1], '.');
					//adiciona ela no novo lugar
					tabuleiro.setPosicao(movimentoCabra[0], movimentoCabra[1], (char)cabra);
					//atualiza o turno
					turno = 1;
					return 1;
				}	
			}
			return -1;
		}
		@Test
		public void testMoveCabra() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();	
			
			assertSame(-2, bhaga.moveCabra(0, 64, 1));
			
			int idM = bhaga.registraJogador("Matheus");
			
			assertSame(-2, bhaga.moveCabra(idM, 64, 1));
			
			int idC = bhaga.registraJogador("Carol");
			
			assertSame(-4, bhaga.moveCabra(idC, 64, 1));
			assertSame(-4, bhaga.moveCabra(idC, 64, 2));			
			assertSame(-5, bhaga.moveCabra(idM, 64, 0));
			assertSame(-5, bhaga.moveCabra(idM, 64, 1));

			bhaga.posicionaCabra(idM, 2, 2);
			bhaga.moveTigre(idC, 1, 0);
			bhaga.posicionaCabra(idM, 3, 2);
			bhaga.moveTigre(idC, 2, 2);

			assertSame(-5, bhaga.moveCabra(idM, 64, 0));
			assertSame(-5, bhaga.moveCabra(idM, 64, 1));
			
			//forcando a ter o numero maximo de cabras
			bhaga.proximaCabra = 85;
			
			assertSame(-1, bhaga.moveCabra(idM, 0, 0));
			assertSame(-1, bhaga.moveCabra(idM, 64, 0));
			
			assertSame(1, bhaga.moveCabra(idM, 65, 0));
			assertSame(-3, bhaga.moveCabra(idM, 65, 0));
			bhaga.turno = 0;
			assertSame(1, bhaga.moveCabra(idM, 65, 2));
			bhaga.turno = 0;
			assertSame(-6, bhaga.moveCabra(idM, 65, 4));
			assertSame(-6, bhaga.moveCabra(idM, 66, 0));
			assertSame(1, bhaga.moveCabra(idM, 66, 4));
			bhaga.turno = 0;
			assertSame(-1, bhaga.moveCabra(idM, 68, 0));
		}		
		
		/*9) obtemOponente
	 	 * Recebe: id do usuário (obtido através da chamada registraJogador)
		 * Retorna: string vazio para erro ou string com o nome do oponente
		 */
		@Override
		public String obtemOponente(int id) throws RemoteException {
			Jogador player = getJogador(id);
			//retorna "" se não existir algum dos jogadores ou id invalido
			if(jogadores[0] == null || jogadores[1] == null || player == null){
				return "";
			}
			
			//se meu id é o 0 meu oponente é o 1
			if(player == jogadores[0]){
				return jogadores[1].getNome();
			}//se meu id é o 1 meu oponente é o 0
			else if(player == jogadores[1]){
				return jogadores[0].getNome();
			}
			return "";
		}
		@Test
		public void testObtemOponente() throws RemoteException {
			BhagaChall bhaga = new BhagaChall();		

			int idM = bhaga.registraJogador("Matheus");
			int idC = bhaga.registraJogador("Carol");
			assertEquals("Carol", bhaga.obtemOponente(idM));
			assertEquals("Matheus", bhaga.obtemOponente(idC));

			assertEquals("", bhaga.obtemOponente(-1));
			assertEquals("", bhaga.obtemOponente(-10));
			assertEquals("", bhaga.obtemOponente(-100));
		}
}

