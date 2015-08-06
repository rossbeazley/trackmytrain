
package com.thalesgroup.rtti._2014_02_20.ldb;

import com.thalesgroup.rtti._2007_10_10.ldb.commontypes.FilterType;
import com.thalesgroup.rtti._2013_11_28.token.types.*;
import com.thalesgroup.rtti._2014_02_20.ldb.types.ServiceDetails;

import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import uk.co.rossbeazley.trackmytrain.wsclient.LBDAuth;

import static com.thalesgroup.rtti._2013_11_28.token.types.ObjectFactory._AccessToken_QNAME;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2015-01-28T21:02:03.533Z
 * Generated source version: 3.0.3
 * 
 */
public final class LDBServiceSoap_LDBServiceSoap12_Client {

    private static final QName SERVICE_NAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "ldb");

    private LDBServiceSoap_LDBServiceSoap12_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        String serviceID = "AFV3Wn3NbBay26vPmPcqSQ==";

        String result = serviceDetails(serviceID);

        System.out.println(result);

        System.exit(0);
    }

    public static String serviceDetails(String serviceID) {
        URL wsdlURL = Ldb.WSDL_LOCATION;
//        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
//            File wsdlFile = new File(args[0]);
//            try {
//                if (wsdlFile.exists()) {
//                    wsdlURL = wsdlFile.toURI().toURL();
//                } else {
//                    wsdlURL = new URL(args[0]);
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }

        Ldb ss = new Ldb(wsdlURL, SERVICE_NAME);
        LDBServiceSoap port = ss.getLDBServiceSoap12();
        addTokenHeader((BindingProvider) port);

//        {
//        System.out.println("Invoking getArrivalBoard...");
//
//            // Set request context property.
//
//        com.thalesgroup.rtti._2014_02_20.ldb.GetBoardRequestParams _getArrivalBoard_parameters = new GetBoardRequestParams();
//            _getArrivalBoard_parameters.setCrs("BSV");
//
//        com.thalesgroup.rtti._2014_02_20.ldb.StationBoardResponseType _getArrivalBoard__return = port.getArrivalBoard(_getArrivalBoard_parameters);
//        System.out.println("getArrivalBoard.result=" + _getArrivalBoard__return);
//
//
//        }
        {
        System.out.println("Invoking getDepartureBoard...");
        com.thalesgroup.rtti._2014_02_20.ldb.GetBoardRequestParams _getDepartureBoard_parameters = new GetBoardRequestParams();
            _getDepartureBoard_parameters.setCrs("MCO");
            _getDepartureBoard_parameters.setFilterCrs("SLD");
            _getDepartureBoard_parameters.setFilterType(FilterType.TO);
        com.thalesgroup.rtti._2014_02_20.ldb.StationBoardResponseType _getDepartureBoard__return = port.getDepartureBoard(_getDepartureBoard_parameters);
        System.out.println("getDepartureBoard.result=" + _getDepartureBoard__return);


        }
//        {
//        System.out.println("Invoking getArrivalDepartureBoard...");
//        com.thalesgroup.rtti._2014_02_20.ldb.GetBoardRequestParams _getArrivalDepartureBoard_parameters = null;
//        com.thalesgroup.rtti._2014_02_20.ldb.StationBoardResponseType _getArrivalDepartureBoard__return = port.getArrivalDepartureBoard(_getArrivalDepartureBoard_parameters);
//        System.out.println("getArrivalDepartureBoard.result=" + _getArrivalDepartureBoard__return);
//
//
//        }
        {

            System.out.println("Invoking getServiceDetails for " + serviceID);
            GetServiceDetailsRequestParams _getServiceDetails_parameters = new GetServiceDetailsRequestParams();
            _getServiceDetails_parameters.setServiceID(serviceID);
            ServiceDetailsResponseType _getServiceDetails__return = port.getServiceDetails(_getServiceDetails_parameters);
            System.out.println("getServiceDetails.result=" + _getServiceDetails__return);
            ServiceDetails r = _getServiceDetails__return.getGetServiceDetailsResult();
            String result = r.getSta() + " " + r.getEta();

            return result;
        }
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
