
package wsclient.fuji;

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
@WebServiceClient(name = "ReportWebService", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://192.168.0.197:8800/ReportWebService.asmx?wsdl")
public class ReportWebService
    extends Service
{

    private final static URL REPORTWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException REPORTWEBSERVICE_EXCEPTION;
    private final static QName REPORTWEBSERVICE_QNAME = new QName("http://tempuri.org/", "ReportWebService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.0.197:8800/ReportWebService.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        REPORTWEBSERVICE_WSDL_LOCATION = url;
        REPORTWEBSERVICE_EXCEPTION = e;
    }

    public ReportWebService() {
        super(__getWsdlLocation(), REPORTWEBSERVICE_QNAME);
    }

    public ReportWebService(WebServiceFeature... features) {
        super(__getWsdlLocation(), REPORTWEBSERVICE_QNAME, features);
    }

    public ReportWebService(URL wsdlLocation) {
        super(wsdlLocation, REPORTWEBSERVICE_QNAME);
    }

    public ReportWebService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, REPORTWEBSERVICE_QNAME, features);
    }

    public ReportWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReportWebService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ReportWebServiceSoap
     */
    @WebEndpoint(name = "ReportWebServiceSoap")
    public ReportWebServiceSoap getReportWebServiceSoap() {
        return super.getPort(new QName("http://tempuri.org/", "ReportWebServiceSoap"), ReportWebServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReportWebServiceSoap
     */
    @WebEndpoint(name = "ReportWebServiceSoap")
    public ReportWebServiceSoap getReportWebServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "ReportWebServiceSoap"), ReportWebServiceSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns ReportWebServiceSoap
     */
    @WebEndpoint(name = "ReportWebServiceSoap12")
    public ReportWebServiceSoap getReportWebServiceSoap12() {
        return super.getPort(new QName("http://tempuri.org/", "ReportWebServiceSoap12"), ReportWebServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReportWebServiceSoap
     */
    @WebEndpoint(name = "ReportWebServiceSoap12")
    public ReportWebServiceSoap getReportWebServiceSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "ReportWebServiceSoap12"), ReportWebServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (REPORTWEBSERVICE_EXCEPTION!= null) {
            throw REPORTWEBSERVICE_EXCEPTION;
        }
        return REPORTWEBSERVICE_WSDL_LOCATION;
    }

}