
package webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de moveCabra complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="moveCabra">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cabra" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="direcao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "moveCabra", propOrder = {
    "identificador",
    "cabra",
    "direcao"
})
public class MoveCabra {

    protected int identificador;
    protected int cabra;
    protected int direcao;

    /**
     * Obtém o valor da propriedade identificador.
     * 
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Define o valor da propriedade identificador.
     * 
     */
    public void setIdentificador(int value) {
        this.identificador = value;
    }

    /**
     * Obtém o valor da propriedade cabra.
     * 
     */
    public int getCabra() {
        return cabra;
    }

    /**
     * Define o valor da propriedade cabra.
     * 
     */
    public void setCabra(int value) {
        this.cabra = value;
    }

    /**
     * Obtém o valor da propriedade direcao.
     * 
     */
    public int getDirecao() {
        return direcao;
    }

    /**
     * Define o valor da propriedade direcao.
     * 
     */
    public void setDirecao(int value) {
        this.direcao = value;
    }

}
