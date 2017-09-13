package com.thalesgroup.rtti._2017_02_02.ldb;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2017-08-02T13:04:54.479+01:00
 * Generated source version: 3.0.3
 * 
 */
@WebServiceClient(name = "ldb", 
                  wsdlLocation = "https://lite.realtime.nationalrail.co.uk/OpenLDBWS/wsdl.aspx",
                  targetNamespace = "http://thalesgroup.com/RTTI/2017-02-02/ldb/") 
public class Ldb extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://thalesgroup.com/RTTI/2017-02-02/ldb/", "ldb");
    public final static QName LDBServiceSoap12 = new QName("http://thalesgroup.com/RTTI/2017-02-02/ldb/", "LDBServiceSoap12");
    public final static QName LDBServiceSoap = new QName("http://thalesgroup.com/RTTI/2017-02-02/ldb/", "LDBServiceSoap");
    static {
        URL url = null;
        try {
            url = new URL("https://lite.realtime.nationalrail.co.uk/OpenLDBWS/wsdl.aspx");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Ldb.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "https://lite.realtime.nationalrail.co.uk/OpenLDBWS/wsdl.aspx");
        }
        WSDL_LOCATION = url;
    }

    public Ldb(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Ldb(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Ldb() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public Ldb(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public Ldb(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public Ldb(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    /**
     *
     * @return
     *     returns LDBServiceSoap
     */
    @WebEndpoint(name = "LDBServiceSoap12")
    public LDBServiceSoap getLDBServiceSoap12() {
        return super.getPort(LDBServiceSoap12, LDBServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns LDBServiceSoap
     */
    @WebEndpoint(name = "LDBServiceSoap12")
    public LDBServiceSoap getLDBServiceSoap12(WebServiceFeature... features) {
        return super.getPort(LDBServiceSoap12, LDBServiceSoap.class, features);
    }
    /**
     *
     * @return
     *     returns LDBServiceSoap
     */
    @WebEndpoint(name = "LDBServiceSoap")
    public LDBServiceSoap getLDBServiceSoap() {
        return super.getPort(LDBServiceSoap, LDBServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns LDBServiceSoap
     */
    @WebEndpoint(name = "LDBServiceSoap")
    public LDBServiceSoap getLDBServiceSoap(WebServiceFeature... features) {
        return super.getPort(LDBServiceSoap, LDBServiceSoap.class, features);
    }

}
