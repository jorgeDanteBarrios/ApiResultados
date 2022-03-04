
package wsclient.fuji.url;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="numeroDeAcceso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="locexpirationSeconds" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "numeroDeAcceso",
    "locexpirationSeconds"
})
@XmlRootElement(name = "obtenerLinkB64")
public class ObtenerLinkB64 {

    protected String numeroDeAcceso;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer locexpirationSeconds;

    /**
     * Obtiene el valor de la propiedad numeroDeAcceso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDeAcceso() {
        return numeroDeAcceso;
    }

    /**
     * Define el valor de la propiedad numeroDeAcceso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDeAcceso(String value) {
        this.numeroDeAcceso = value;
    }

    /**
     * Obtiene el valor de la propiedad locexpirationSeconds.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLocexpirationSeconds() {
        return locexpirationSeconds;
    }

    /**
     * Define el valor de la propiedad locexpirationSeconds.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLocexpirationSeconds(Integer value) {
        this.locexpirationSeconds = value;
    }

}
