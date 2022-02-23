
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
 *         &lt;element name="obtenerLinkResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "obtenerLinkResult"
})
@XmlRootElement(name = "obtenerLinkResponse")
public class ObtenerLinkResponse {

    protected String obtenerLinkResult;

    /**
     * Obtiene el valor de la propiedad obtenerLinkResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObtenerLinkResult() {
        return obtenerLinkResult;
    }

    /**
     * Define el valor de la propiedad obtenerLinkResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObtenerLinkResult(String value) {
        this.obtenerLinkResult = value;
    }

}
