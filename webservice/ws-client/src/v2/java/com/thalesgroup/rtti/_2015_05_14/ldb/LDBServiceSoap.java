package com.thalesgroup.rtti._2015_05_14.ldb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.0.3
 * 2015-08-05T07:08:26.330+01:00
 * Generated source version: 3.0.3
 * 
 */
@WebService(targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", name = "LDBServiceSoap")
@XmlSeeAlso({com.thalesgroup.rtti._2013_11_28.token.types.ObjectFactory.class, com.thalesgroup.rtti._2014_02_20.ldb.types.ObjectFactory.class, com.thalesgroup.rtti._2015_05_14.ldb.types.ObjectFactory.class, com.thalesgroup.rtti._2007_10_10.ldb.commontypes.ObjectFactory.class, com.thalesgroup.rtti._2012_01_13.ldb.types.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface LDBServiceSoap {

    @WebResult(name = "GetArrBoardWithDetailsResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetArrBoardWithDetails", action = "http://thalesgroup.com/RTTI/2015-05-14/ldb/GetArrBoardWithDetails")
    public StationBoardWithDetailsResponseType getArrBoardWithDetails(
        @WebParam(partName = "parameters", name = "GetArrBoardWithDetailsRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetNextDeparturesResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetNextDepartures", action = "http://thalesgroup.com/RTTI/2015-05-14/ldb/GetNextDepartures")
    public DeparturesBoardResponseType getNextDepartures(
        @WebParam(partName = "parameters", name = "GetNextDeparturesRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetDeparturesRequestParams parameters
    );

    @WebResult(name = "GetDepBoardWithDetailsResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetDepBoardWithDetails", action = "http://thalesgroup.com/RTTI/2015-05-14/ldb/GetDepBoardWithDetails")
    public StationBoardWithDetailsResponseType getDepBoardWithDetails(
        @WebParam(partName = "parameters", name = "GetDepBoardWithDetailsRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetArrivalDepartureBoardResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetArrivalDepartureBoard", action = "http://thalesgroup.com/RTTI/2012-01-13/ldb/GetArrivalDepartureBoard")
    public StationBoardResponseType getArrivalDepartureBoard(
        @WebParam(partName = "parameters", name = "GetArrivalDepartureBoardRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetDepartureBoardResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetDepartureBoard", action = "http://thalesgroup.com/RTTI/2012-01-13/ldb/GetDepartureBoard")
    public StationBoardResponseType getDepartureBoard(
        @WebParam(partName = "parameters", name = "GetDepartureBoardRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetFastestDeparturesWithDetailsResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetFastestDeparturesWithDetails", action = "http://thalesgroup.com/RTTI/2015-05-14/ldb/GetFastestDeparturesWithDetails")
    public DeparturesBoardWithDetailsResponseType getFastestDeparturesWithDetails(
        @WebParam(partName = "parameters", name = "GetFastestDeparturesWithDetailsRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetDeparturesRequestParams parameters
    );

    @WebResult(name = "GetArrivalBoardResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetArrivalBoard", action = "http://thalesgroup.com/RTTI/2012-01-13/ldb/GetArrivalBoard")
    public StationBoardResponseType getArrivalBoard(
        @WebParam(partName = "parameters", name = "GetArrivalBoardRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetFastestDeparturesResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetFastestDepartures", action = "http://thalesgroup.com/RTTI/2015-05-14/ldb/GetFastestDepartures")
    public DeparturesBoardResponseType getFastestDepartures(
        @WebParam(partName = "parameters", name = "GetFastestDeparturesRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetDeparturesRequestParams parameters
    );

    @WebResult(name = "GetNextDeparturesWithDetailsResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetNextDeparturesWithDetails", action = "http://thalesgroup.com/RTTI/2015-05-14/ldb/GetNextDeparturesWithDetails")
    public DeparturesBoardWithDetailsResponseType getNextDeparturesWithDetails(
        @WebParam(partName = "parameters", name = "GetNextDeparturesWithDetailsRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetDeparturesRequestParams parameters
    );

    @WebResult(name = "GetArrDepBoardWithDetailsResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetArrDepBoardWithDetails", action = "http://thalesgroup.com/RTTI/2015-05-14/ldb/GetArrDepBoardWithDetails")
    public StationBoardWithDetailsResponseType getArrDepBoardWithDetails(
        @WebParam(partName = "parameters", name = "GetArrDepBoardWithDetailsRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetBoardRequestParams parameters
    );

    @WebResult(name = "GetServiceDetailsResponse", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/", partName = "parameters")
    @WebMethod(operationName = "GetServiceDetails", action = "http://thalesgroup.com/RTTI/2012-01-13/ldb/GetServiceDetails")
    public ServiceDetailsResponseType getServiceDetails(
        @WebParam(partName = "parameters", name = "GetServiceDetailsRequest", targetNamespace = "http://thalesgroup.com/RTTI/2015-05-14/ldb/")
        GetServiceDetailsRequestParams parameters
    );
}
