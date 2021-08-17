
package wsclient.fuji;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para response complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reporteHTML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reporte" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response", propOrder = {
    "success",
    "message",
    "reporteHTML",
    "reporte"
})
public class Response {

    @XmlElement(name = "Success")
    protected boolean success;
    @XmlElement(name = "Message")
    protected String message;
    protected String reporteHTML;
    protected byte[] reporte;

    /**
     * Obtiene el valor de la propiedad success.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Define el valor de la propiedad success.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Obtiene el valor de la propiedad message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define el valor de la propiedad message.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Obtiene el valor de la propiedad reporteHTML.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReporteHTML() {
        return reporteHTML;
    }

    /**
     * Define el valor de la propiedad reporteHTML.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReporteHTML(String value) {
        this.reporteHTML = value;
    }

    /**
     * Obtiene el valor de la propiedad reporte.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getReporte() {
        return reporte;
    }

    /**
     * Define el valor de la propiedad reporte.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setReporte(byte[] value) {
        this.reporte = value;
    }

}
