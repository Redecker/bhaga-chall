/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bhagachallwsclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Roland Teodorowitsch
 */
public class BhagaChallWSclient {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        executaTeste("BhagaChall-000");
    }

    private static void executaTeste(String rad) throws IOException {
        String inFile = rad+".in";
        FileInputStream is = new FileInputStream(new File(inFile));
        System.setIn(is);

        String outFile = rad+".out";
        FileWriter outWriter = new FileWriter(outFile);
        try (PrintWriter out = new PrintWriter(outWriter)) {
            Scanner leitura = new Scanner(System.in);
            int numOp = leitura.nextInt();
            for (int i=0;i<numOp;++i) {
                System.out.print("\r"+rad+": "+(i+1)+"/"+numOp);
                int op = leitura.nextInt();
                String parametros = leitura.next();
                String param[] = parametros.split(":",-1);
                switch(op) {
                    case 0:
                        if (param.length!=4)
                            erro(inFile,i+1);
                        else
                            out.println(preRegistro(param[0],Integer.parseInt(param[1]),param[2],Integer.parseInt(param[3])));
                        break;
                    case 1:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(registraJogador(param[0]));
                        break;
                    case 2:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(encerraPartida(Integer.parseInt(param[0])));
                        break;
                    case 3:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(temPartida(Integer.parseInt(param[0])));
                        break;
                    case 4:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(ehMinhaVez(Integer.parseInt(param[0])));
                        break;
                    case 5:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(obtemGrade(Integer.parseInt(param[0])));
                        break;
                    case 6:
                        if (param.length!=3)
                            erro(inFile,i+1);
                        else
                            out.println(moveTigre(Integer.parseInt(param[0]),Integer.parseInt(param[1]),Integer.parseInt(param[2])));
                        break;
                    case 7:
                        if (param.length!=3)
                            erro(inFile,i+1);
                        else
                            out.println(posicionaCabra(Integer.parseInt(param[0]),Integer.parseInt(param[1]),Integer.parseInt(param[2])));
                        break;
                    case 8:
                        if (param.length!=3)
                            erro(inFile,i+1);
                        else
                            out.println(moveCabra(Integer.parseInt(param[0]),Integer.parseInt(param[1]),Integer.parseInt(param[2])));
                        break;
                    case 9:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(obtemOponente(Integer.parseInt(param[0])));
                        break;
                    default:
                        erro(inFile,i+1);
                }
            }
            System.out.println("... terminado!");
            out.close();
            leitura.close();
        }
    }
    
    private static void erro(String arq,int operacao) {
        System.err.println("Entrada invalida: erro na operacao "+operacao+" do arquivo "+arq);
        System.exit(1);
    }

    private static String preRegistro(java.lang.String jogador1, int identificador1, java.lang.String jogador2, int identificador2) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.preRegistro(jogador1, identificador1, jogador2, identificador2);
    }

    private static int registraJogador(java.lang.String nome) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.registraJogador(nome);
    }

    private static int temPartida(int identificador) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.temPartida(identificador);
    }

    private static int posicionaCabra(int identificador, int x, int y) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.posicionaCabra(identificador, x, y);
    }

    private static String obtemOponente(int identificador) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.obtemOponente(identificador);
    }

    private static String obtemGrade(int identificador) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.obtemGrade(identificador);
    }

    private static int moveTigre(int identificador, int tigre, int direcao) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.moveTigre(identificador, tigre, direcao);
    }

    private static int moveCabra(int identificador, int cabra, int direcao) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.moveCabra(identificador, cabra, direcao);
    }

    private static int encerraPartida(int identificador) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.encerraPartida(identificador);
    }

    private static int ehMinhaVez(int identificador) {
        webservice.BhagaChallWebService_Service service = new webservice.BhagaChallWebService_Service();
        webservice.BhagaChallWebService port = service.getBhagaChallWebServicePort();
        return port.ehMinhaVez(identificador);
    }
    
    
}
