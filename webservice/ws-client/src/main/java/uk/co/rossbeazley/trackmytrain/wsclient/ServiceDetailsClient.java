package uk.co.rossbeazley.trackmytrain.wsclient;


import com.thalesgroup.rtti._2017_10_01.ldb.GetServiceDetailsRequestParams;
import com.thalesgroup.rtti._2017_10_01.ldb.LDBServiceSoap;
import com.thalesgroup.rtti._2017_10_01.ldb.ServiceDetailsResponseType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.ServiceDetails;

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
            final boolean departed = !isEmpty(r.getAtd());
            final String scheduledTime = departed ? "Departed" : isEmpty(r.getSta()) ? r.getStd() : r.getSta();

            final String estimatedTime = departed ? r.getAtd() : isEmpty(r.getEta()) ? r.getEtd() : r.getEta();
            result = ServiceResult.ok(Train.ok(serviceID, scheduledTime, estimatedTime, r.getPlatform(), departed));


        } catch (Exception e) {
            result = ServiceResult.error(e.getMessage());
        }
        return result;
    }

}
/**
 * sta 1723
 * eta null
 * ata on time
 * std 1724
 * etd on time
 * atd null
 **/