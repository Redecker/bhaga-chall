
package webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de preRegistro complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="preRegistro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jogador1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificador1" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="jogador2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificador2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preRegistro", propOrder = {
    "jogador1",
    "identificador1",
    "jogador2",
    "identificador2"
})
public class PreRegistro {

    protected String jogador1;
    protected int identificador1;
    protected String jogador2;
    protected int identificador2;

    /**
     * Obtém o valor da propriedade jogador1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJogador1() {
        return jogador1;
    }

    /**
     * Define o valor da propriedade jogador1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJogador1(String value) {
        this.jogador1 = value;
    }

    /**
     * Obtém o valor da propriedade identificador1.
     * 
     */
    public int getIdentificador1() {
        return identificador1;
    }

    /**
     * Define o valor da propriedade identificador1.
     * 
     */
    public void setIdentificador1(int value) {
        this.identificador1 = value;
    }

    /**
     * Obtém o valor da propriedade jogador2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJogador2() {
        return jogador2;
    }

    /**
     * Define o valor da propriedade jogador2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJogador2(String value) {
        this.jogador2 = value;
    }

    /**
     * Obtém o valor da propriedade identificador2.
     * 
     */
    public int getIdentificador2() {
        return identificador2;
    }

    /**
     * Define o valor da propriedade identificador2.
     * 
     */
    public void setIdentificador2(int value) {
        this.identificador2 = value;
    }

}
