
package wsclient.azteca;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="GetReporteResult" type="{http://schemas.datacontract.org/2004/07/wsReporte}respReporte" minOccurs="0"/>
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
    "getReporteResult"
})
@XmlRootElement(name = "GetReporteResponse")
public class GetReporteResponse {

    @XmlElementRef(name = "GetReporteResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<RespReporte> getReporteResult;

    /**
     * Obtiene el valor de la propiedad getReporteResult.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RespReporte }{@code >}
     *     
     */
    public JAXBElement<RespReporte> getGetReporteResult() {
        return getReporteResult;
    }

    /**
     * Define el valor de la propiedad getReporteResult.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RespReporte }{@code >}
     *     
     */
    public void setGetReporteResult(JAXBElement<RespReporte> value) {
        this.getReporteResult = value;
    }

}
