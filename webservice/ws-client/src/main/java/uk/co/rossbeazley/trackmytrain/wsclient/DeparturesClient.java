package uk.co.rossbeazley.trackmytrain.wsclient;

import com.thalesgroup.rtti._2007_10_10.ldb.commontypes.FilterType;
import com.thalesgroup.rtti._2015_05_14.ldb.GetBoardRequestParams;
import com.thalesgroup.rtti._2015_05_14.ldb.LDBServiceSoap;
import com.thalesgroup.rtti._2015_05_14.ldb.StationBoardResponseType;
import com.thalesgroup.rtti._2015_05_14.ldb.types.ArrayOfServiceItems;
import com.thalesgroup.rtti._2015_05_14.ldb.types.ServiceItem;
import com.thalesgroup.rtti._2015_05_14.ldb.types.StationBoard;

import java.util.List;

import static org.apache.cxf.common.util.StringUtils.isEmpty;

public class DeparturesClient {

    public static void main(String args[]) throws Exception {
        DeparturesResult result = services("MCO", "MAN");
        System.out.println(result.toJson());
        System.exit(0);
    }

    public static DeparturesResult services(String from, String to) {
        DeparturesResult result;
        try {
            LDBServiceSoap port = SoapPort.getLdbServiceSoap();

            GetBoardRequestParams params = new GetBoardRequestParams();
            params.setCrs(from);
            params.setFilterCrs(to);
            params.setFilterType(FilterType.TO);

            StationBoardResponseType r = port.getDepartureBoard(params);

            result = DeparturesResult.ok();

            final StationBoard getStationBoardResult = r.getGetStationBoardResult();
            final ArrayOfServiceItems trainServices = getStationBoardResult.getTrainServices();
            if (trainServices != null) {
                final List<ServiceItem> services = trainServices.getService();
                if (services!=null) {
                    for (ServiceItem service : services) {
                        final String scheduledTime = isEmpty(service.getSta()) ? service.getStd() : service.getSta();
                        final String estimatedTime = isEmpty(service.getEta()) ? service.getEtd() : service.getEta();

                        result.add(Train.ok(service.getServiceID(), scheduledTime, estimatedTime, service.getPlatform(), false));
                    }
                }
            }
        } catch (Exception e) {
            result = DeparturesResult.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


}
