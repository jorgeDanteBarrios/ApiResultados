
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
 *         &lt;element name="reporteHtmlBase64Result" type="{http://tempuri.org/}response" minOccurs="0"/>
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
    "reporteHtmlBase64Result"
})
@XmlRootElement(name = "reporteHtmlBase64Response")
public class ReporteHtmlBase64Response {

    protected Response reporteHtmlBase64Result;

    /**
     * Obtiene el valor de la propiedad reporteHtmlBase64Result.
     * 
     * @return
     *     possible object is
     *     {@link Response }
     *     
     */
    public Response getReporteHtmlBase64Result() {
        return reporteHtmlBase64Result;
    }

    /**
     * Define el valor de la propiedad reporteHtmlBase64Result.
     * 
     * @param value
     *     allowed object is
     *     {@link Response }
     *     
     */
    public void setReporteHtmlBase64Result(Response value) {
        this.reporteHtmlBase64Result = value;
    }

}
