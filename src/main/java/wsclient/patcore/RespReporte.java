
package wsclient.patcore;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para respReporte complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="respReporte">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ArchivoBase64" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Caso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Generado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Mensaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respReporte", namespace = "http://schemas.datacontract.org/2004/07/wsPatCore", propOrder = {
    "archivoBase64",
    "caso",
    "generado",
    "mensaje"
})
public class RespReporte {

    @XmlElementRef(name = "ArchivoBase64", namespace = "http://schemas.datacontract.org/2004/07/wsPatCore", type = JAXBElement.class, required = false)
    protected JAXBElement<String> archivoBase64;
    @XmlElementRef(name = "Caso", namespace = "http://schemas.datacontract.org/2004/07/wsPatCore", type = JAXBElement.class, required = false)
    protected JAXBElement<String> caso;
    @XmlElement(name = "Generado")
    protected Boolean generado;
    @XmlElementRef(name = "Mensaje", namespace = "http://schemas.datacontract.org/2004/07/wsPatCore", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mensaje;

    /**
     * Obtiene el valor de la propiedad archivoBase64.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getArchivoBase64() {
        return archivoBase64;
    }

    /**
     * Define el valor de la propiedad archivoBase64.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArchivoBase64(JAXBElement<String> value) {
        this.archivoBase64 = value;
    }

    /**
     * Obtiene el valor de la propiedad caso.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCaso() {
        return caso;
    }

    /**
     * Define el valor de la propiedad caso.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCaso(JAXBElement<String> value) {
        this.caso = value;
    }

    /**
     * Obtiene el valor de la propiedad generado.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGenerado() {
        return generado;
    }

    /**
     * Define el valor de la propiedad generado.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGenerado(Boolean value) {
        this.generado = value;
    }

    /**
     * Obtiene el valor de la propiedad mensaje.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMensaje() {
        return mensaje;
    }

    /**
     * Define el valor de la propiedad mensaje.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMensaje(JAXBElement<String> value) {
        this.mensaje = value;
    }

}
