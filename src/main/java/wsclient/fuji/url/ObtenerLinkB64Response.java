
package wsclient.fuji.url;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="obtenerLinkB64Result" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "obtenerLinkB64Result"
})
@XmlRootElement(name = "obtenerLinkB64Response")
public class ObtenerLinkB64Response {

    protected String obtenerLinkB64Result;

    /**
     * Obtiene el valor de la propiedad obtenerLinkB64Result.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObtenerLinkB64Result() {
        return obtenerLinkB64Result;
    }

    /**
     * Define el valor de la propiedad obtenerLinkB64Result.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObtenerLinkB64Result(String value) {
        this.obtenerLinkB64Result = value;
    }

}
