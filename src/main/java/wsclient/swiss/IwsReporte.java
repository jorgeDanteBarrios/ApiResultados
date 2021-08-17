
package wsclient.swiss;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IwsReporte", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IwsReporte {


    /**
     * 
     * @param numeroOrden
     * @param logos
     * @return
     *     returns wsclient.swiss.RespReporte
     */
    @WebMethod(operationName = "GetReporte", action = "http://tempuri.org/IwsReporte/GetReporte")
    @WebResult(name = "GetReporteResult", targetNamespace = "http://tempuri.org/")
    @RequestWrapper(localName = "GetReporte", targetNamespace = "http://tempuri.org/", className = "wsclient.swiss.GetReporte")
    @ResponseWrapper(localName = "GetReporteResponse", targetNamespace = "http://tempuri.org/", className = "wsclient.swiss.GetReporteResponse")
    public RespReporte getReporte(
        @WebParam(name = "NumeroOrden", targetNamespace = "http://tempuri.org/")
        String numeroOrden,
        @WebParam(name = "logos", targetNamespace = "http://tempuri.org/")
        Boolean logos);

}
