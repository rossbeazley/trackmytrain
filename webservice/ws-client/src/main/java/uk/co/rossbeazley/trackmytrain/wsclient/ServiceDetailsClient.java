package uk.co.rossbeazley.trackmytrain.wsclient;

import com.thalesgroup.rtti._2014_02_20.ldb.GetServiceDetailsRequestParams;
import com.thalesgroup.rtti._2014_02_20.ldb.LDBServiceSoap;
import com.thalesgroup.rtti._2014_02_20.ldb.ServiceDetailsResponseType;
import com.thalesgroup.rtti._2014_02_20.ldb.types.ServiceDetails;

import static org.apache.cxf.common.util.StringUtils.isEmpty;

public class ServiceDetailsClient {

    public static void main(String args[]) throws java.lang.Exception {
        String serviceID = "fPOF1tZiXa9wX2#YyGfizQ==";
        Train result = serviceDetails(serviceID).train;
        System.out.println(result);
        System.exit(0);
    }

    public static ServiceResult serviceDetails(String serviceID) {
        ServiceResult result;
        try {
            LDBServiceSoap port = SoapPort.getLdbServiceSoap();
            GetServiceDetailsRequestParams requestParams;

            requestParams = new GetServiceDetailsRequestParams();
            requestParams.setServiceID(serviceID);

            ServiceDetailsResponseType serviceDetailsResponseType;
            serviceDetailsResponseType = port.getServiceDetails(requestParams);
            ServiceDetails r = serviceDetailsResponseType.getGetServiceDetailsResult();

            final String scheduledTime = isEmpty(r.getSta()) ? r.getStd() : r.getSta();
            final String estimatedTime = isEmpty(r.getEta()) ? r.getEtd() : r.getEta();
            result = ServiceResult.ok(Train.ok(serviceID, scheduledTime, estimatedTime, r.getPlatform()));


        } catch (Exception e) {
            result = ServiceResult.error(e.getMessage());
        }
        return result;
    }

}
