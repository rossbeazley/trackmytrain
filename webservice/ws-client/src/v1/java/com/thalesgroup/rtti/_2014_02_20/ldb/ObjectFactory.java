
package com.thalesgroup.rtti._2014_02_20.ldb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.thalesgroup.rtti._2014_02_20.ldb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetDepartureBoardRequest_QNAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "GetDepartureBoardRequest");
    private final static QName _GetServiceDetailsResponse_QNAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "GetServiceDetailsResponse");
    private final static QName _GetArrivalBoardRequest_QNAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "GetArrivalBoardRequest");
    private final static QName _GetArrivalDepartureBoardResponse_QNAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "GetArrivalDepartureBoardResponse");
    private final static QName _GetServiceDetailsRequest_QNAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "GetServiceDetailsRequest");
    private final static QName _GetDepartureBoardResponse_QNAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "GetDepartureBoardResponse");
    private final static QName _GetArrivalDepartureBoardRequest_QNAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "GetArrivalDepartureBoardRequest");
    private final static QName _GetArrivalBoardResponse_QNAME = new QName("http://thalesgroup.com/RTTI/2014-02-20/ldb/", "GetArrivalBoardResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.thalesgroup.rtti._2014_02_20.ldb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StationBoardResponseType }
     * 
     */
    public StationBoardResponseType createStationBoardResponseType() {
        return new StationBoardResponseType();
    }

    /**
     * Create an instance of {@link GetBoardRequestParams }
     * 
     */
    public GetBoardRequestParams createGetBoardRequestParams() {
        return new GetBoardRequestParams();
    }

    /**
     * Create an instance of {@link ServiceDetailsResponseType }
     * 
     */
    public ServiceDetailsResponseType createServiceDetailsResponseType() {
        return new ServiceDetailsResponseType();
    }

    /**
     * Create an instance of {@link GetServiceDetailsRequestParams }
     * 
     */
    public GetServiceDetailsRequestParams createGetServiceDetailsRequestParams() {
        return new GetServiceDetailsRequestParams();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBoardRequestParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "GetDepartureBoardRequest")
    public JAXBElement<GetBoardRequestParams> createGetDepartureBoardRequest(GetBoardRequestParams value) {
        return new JAXBElement<GetBoardRequestParams>(_GetDepartureBoardRequest_QNAME, GetBoardRequestParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "GetServiceDetailsResponse")
    public JAXBElement<ServiceDetailsResponseType> createGetServiceDetailsResponse(ServiceDetailsResponseType value) {
        return new JAXBElement<ServiceDetailsResponseType>(_GetServiceDetailsResponse_QNAME, ServiceDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBoardRequestParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "GetArrivalBoardRequest")
    public JAXBElement<GetBoardRequestParams> createGetArrivalBoardRequest(GetBoardRequestParams value) {
        return new JAXBElement<GetBoardRequestParams>(_GetArrivalBoardRequest_QNAME, GetBoardRequestParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StationBoardResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "GetArrivalDepartureBoardResponse")
    public JAXBElement<StationBoardResponseType> createGetArrivalDepartureBoardResponse(StationBoardResponseType value) {
        return new JAXBElement<StationBoardResponseType>(_GetArrivalDepartureBoardResponse_QNAME, StationBoardResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServiceDetailsRequestParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "GetServiceDetailsRequest")
    public JAXBElement<GetServiceDetailsRequestParams> createGetServiceDetailsRequest(GetServiceDetailsRequestParams value) {
        return new JAXBElement<GetServiceDetailsRequestParams>(_GetServiceDetailsRequest_QNAME, GetServiceDetailsRequestParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StationBoardResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "GetDepartureBoardResponse")
    public JAXBElement<StationBoardResponseType> createGetDepartureBoardResponse(StationBoardResponseType value) {
        return new JAXBElement<StationBoardResponseType>(_GetDepartureBoardResponse_QNAME, StationBoardResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBoardRequestParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "GetArrivalDepartureBoardRequest")
    public JAXBElement<GetBoardRequestParams> createGetArrivalDepartureBoardRequest(GetBoardRequestParams value) {
        return new JAXBElement<GetBoardRequestParams>(_GetArrivalDepartureBoardRequest_QNAME, GetBoardRequestParams.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StationBoardResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "GetArrivalBoardResponse")
    public JAXBElement<StationBoardResponseType> createGetArrivalBoardResponse(StationBoardResponseType value) {
        return new JAXBElement<StationBoardResponseType>(_GetArrivalBoardResponse_QNAME, StationBoardResponseType.class, null, value);
    }

}
