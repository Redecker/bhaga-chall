
package webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de moveTigre complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="moveTigre">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificador" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tigre" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "moveTigre", propOrder = {
    "identificador",
    "tigre",
    "direcao"
})
public class MoveTigre {

    protected int identificador;
    protected int tigre;
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
     * Obtém o valor da propriedade tigre.
     * 
     */
    public int getTigre() {
        return tigre;
    }

    /**
     * Define o valor da propriedade tigre.
     * 
     */
    public void setTigre(int value) {
        this.tigre = value;
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
