package uk.co.rossbeazley.trackmytrain.wsclient;


import com.thalesgroup.rtti._2013_11_28.token.types.AccessToken;

import com.thalesgroup.rtti._2017_02_02.ldb.LDBServiceSoap;
import com.thalesgroup.rtti._2017_02_02.ldb.Ldb;


import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import static com.thalesgroup.rtti._2013_11_28.token.types.ObjectFactory.*;

public class SoapPort {
    private static final QName SERVICE_NAME = new QName("http://thalesgroup.com/RTTI/2016-02-16/ldb/", "ldb");
    private static LDBServiceSoap port;

    static LDBServiceSoap getLdbServiceSoap() {
        if(port!=null) return port;
        URL wsdlURL = Ldb.WSDL_LOCATION;
        Ldb ss = new Ldb(wsdlURL);
        port = ss.getLDBServiceSoap12();
        addTokenHeader((BindingProvider) port);

        return port;
    }

    private static void addTokenHeader(BindingProvider port) {

        ArrayList<Header> headers = new ArrayList<Header>(1);
        try {
            AccessToken accessToken = new AccessToken();
            accessToken.setTokenValue(LBDAuth.TOKEN_VALUE);
            Header soapHeader = new Header(_AccessToken_QNAME, accessToken, new JAXBDataBinding(AccessToken.class));
            headers.add(soapHeader);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        port.getRequestContext().put(Header.HEADER_LIST, headers);

    }
}
