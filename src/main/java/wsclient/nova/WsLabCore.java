
package wsclient.nova;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "wsLabCore", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://labnovaws1.gda.mx:2024/wsReporte.svc?singleWsdl")
public class WsLabCore
    extends Service
{

    private final static URL WSLABCORE_WSDL_LOCATION;
    private final static WebServiceException WSLABCORE_EXCEPTION;
    private final static QName WSLABCORE_QNAME = new QName("http://tempuri.org/", "wsLabCore");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://labnovaws1.gda.mx:2024/wsReporte.svc?singleWsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSLABCORE_WSDL_LOCATION = url;
        WSLABCORE_EXCEPTION = e;
    }

    public WsLabCore() {
        super(__getWsdlLocation(), WSLABCORE_QNAME);
    }

    public WsLabCore(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSLABCORE_QNAME, features);
    }

    public WsLabCore(URL wsdlLocation) {
        super(wsdlLocation, WSLABCORE_QNAME);
    }

    public WsLabCore(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSLABCORE_QNAME, features);
    }

    public WsLabCore(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WsLabCore(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IwsReporte
     */
    @WebEndpoint(name = "BasicHttpBinding_IwsReporte")
    public IwsReporte getBasicHttpBindingIwsReporte() {
        return super.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_IwsReporte"), IwsReporte.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IwsReporte
     */
    @WebEndpoint(name = "BasicHttpBinding_IwsReporte")
    public IwsReporte getBasicHttpBindingIwsReporte(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_IwsReporte"), IwsReporte.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSLABCORE_EXCEPTION!= null) {
            throw WSLABCORE_EXCEPTION;
        }
        return WSLABCORE_WSDL_LOCATION;
    }

}