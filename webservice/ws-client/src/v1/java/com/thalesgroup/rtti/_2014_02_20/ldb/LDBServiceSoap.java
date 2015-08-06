package com.thalesgroup.rtti._2014_02_20.ldb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2015-01-28T21:02:03.608Z
 * Generated source version: 3.0.3
 * 
 */
@WebService(targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", name = "LDBServiceSoap")
@XmlSeeAlso({com.thalesgroup.rtti._2013_11_28.token.types.ObjectFactory.class, com.thalesgroup.rtti._2014_02_20.ldb.types.ObjectFactory.class, com.thalesgroup.rtti._2007_10_10.ldb.commontypes.ObjectFactory.class, com.thalesgroup.rtti._2012_01_13.ldb.types.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface LDBServiceSoap {

    @WebResult(name = "GetArrivalBoardResponse", targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetArrivalBoard", action = "http://thalesgroup.com/RTTI/2012-01-13/ldb/GetArrivalBoard")
    public StationBoardResponseType getArrivalBoard(
        @WebParam(partName = "parameters", name = "GetArrivalBoardRequest", targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetDepartureBoardResponse", targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetDepartureBoard", action = "http://thalesgroup.com/RTTI/2012-01-13/ldb/GetDepartureBoard")
    public StationBoardResponseType getDepartureBoard(
        @WebParam(partName = "parameters", name = "GetDepartureBoardRequest", targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetArrivalDepartureBoardResponse", targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetArrivalDepartureBoard", action = "http://thalesgroup.com/RTTI/2012-01-13/ldb/GetArrivalDepartureBoard")
    public StationBoardResponseType getArrivalDepartureBoard(
        @WebParam(partName = "parameters", name = "GetArrivalDepartureBoardRequest", targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetServiceDetailsResponse", targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetServiceDetails", action = "http://thalesgroup.com/RTTI/2012-01-13/ldb/GetServiceDetails")
    public ServiceDetailsResponseType getServiceDetails(
        @WebParam(partName = "parameters", name = "GetServiceDetailsRequest", targetNamespace = "http://thalesgroup.com/RTTI/2014-02-20/ldb/")
        GetServiceDetailsRequestParams parameters
    );
}
