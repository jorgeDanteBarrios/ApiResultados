
package wsclient.fuji;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="vchAccessionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vchAccessionNumber"
})
@XmlRootElement(name = "reporteHtmlBase64")
public class ReporteHtmlBase64 {

    protected String vchAccessionNumber;

    /**
     * Obtiene el valor de la propiedad vchAccessionNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVchAccessionNumber() {
        return vchAccessionNumber;
    }

    /**
     * Define el valor de la propiedad vchAccessionNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVchAccessionNumber(String value) {
        this.vchAccessionNumber = value;
    }

}
