
package wsclient.fuji.url;

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
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "wsGetLink", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://192.168.0.197:8887/wsGetLink.asmx?wsdl")
public class WsGetLink
    extends Service
{

    private final static URL WSGETLINK_WSDL_LOCATION;
    private final static WebServiceException WSGETLINK_EXCEPTION;
    private final static QName WSGETLINK_QNAME = new QName("http://tempuri.org/", "wsGetLink");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.0.197:8887/wsGetLink.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSGETLINK_WSDL_LOCATION = url;
        WSGETLINK_EXCEPTION = e;
    }

    public WsGetLink() {
        super(__getWsdlLocation(), WSGETLINK_QNAME);
    }

    public WsGetLink(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSGETLINK_QNAME, features);
    }

    public WsGetLink(URL wsdlLocation) {
        super(wsdlLocation, WSGETLINK_QNAME);
    }

    public WsGetLink(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSGETLINK_QNAME, features);
    }

    public WsGetLink(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WsGetLink(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WsGetLinkSoap
     */
    @WebEndpoint(name = "wsGetLinkSoap")
    public WsGetLinkSoap getWsGetLinkSoap() {
        return super.getPort(new QName("http://tempuri.org/", "wsGetLinkSoap"), WsGetLinkSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WsGetLinkSoap
     */
    @WebEndpoint(name = "wsGetLinkSoap")
    public WsGetLinkSoap getWsGetLinkSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "wsGetLinkSoap"), WsGetLinkSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns WsGetLinkSoap
     */
    @WebEndpoint(name = "wsGetLinkSoap12")
    public WsGetLinkSoap getWsGetLinkSoap12() {
        return super.getPort(new QName("http://tempuri.org/", "wsGetLinkSoap12"), WsGetLinkSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WsGetLinkSoap
     */
    @WebEndpoint(name = "wsGetLinkSoap12")
    public WsGetLinkSoap getWsGetLinkSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "wsGetLinkSoap12"), WsGetLinkSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSGETLINK_EXCEPTION!= null) {
            throw WSGETLINK_EXCEPTION;
        }
        return WSGETLINK_WSDL_LOCATION;
    }

}
