
package wsclient.patcore;

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
@WebServiceClient(name = "Service1", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://labcentralws1.gda.mx:81/wsPatCore.svc?wsdl")
public class Service1
    extends Service
{

    private final static URL SERVICE1_WSDL_LOCATION;
    private final static WebServiceException SERVICE1_EXCEPTION;
    private final static QName SERVICE1_QNAME = new QName("http://tempuri.org/", "Service1");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://labcentralws1.gda.mx:81/wsPatCore.svc?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SERVICE1_WSDL_LOCATION = url;
        SERVICE1_EXCEPTION = e;
    }

    public Service1() {
        super(__getWsdlLocation(), SERVICE1_QNAME);
    }

    public Service1(WebServiceFeature... features) {
        super(__getWsdlLocation(), SERVICE1_QNAME, features);
    }

    public Service1(URL wsdlLocation) {
        super(wsdlLocation, SERVICE1_QNAME);
    }

    public Service1(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SERVICE1_QNAME, features);
    }

    public Service1(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Service1(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IwsPatCore
     */
    @WebEndpoint(name = "BasicHttpBinding_IwsPatCore")
    public IwsPatCore getBasicHttpBindingIwsPatCore() {
        return super.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_IwsPatCore"), IwsPatCore.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IwsPatCore
     */
    @WebEndpoint(name = "BasicHttpBinding_IwsPatCore")
    public IwsPatCore getBasicHttpBindingIwsPatCore(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "BasicHttpBinding_IwsPatCore"), IwsPatCore.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SERVICE1_EXCEPTION!= null) {
            throw SERVICE1_EXCEPTION;
        }
        return SERVICE1_WSDL_LOCATION;
    }

}
