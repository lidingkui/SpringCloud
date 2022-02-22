package com.hubject.oembackend.converter.bff;

import com.hubject.common.dto.bff.StationDTO;
import com.hubject.oembackend.dto.oicp.evse.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class EvseData2BFFStation {

    private static final String ADDRESS_SPLIT = "-";

    public static StationDTO cvt(Evse evse) {
        if (evse == null || StringUtils.isBlank(evse.getChargingStationID())) return null;

        StationDTO ret = new StationDTO();
        ret.setStationId(evse.getChargingStationID());
        List<InfoText> chargingStationNames = evse.getChargingStationNames();
        if (chargingStationNames != null && !chargingStationNames.isEmpty()) {
            ret.setStationName(chargingStationNames.get(0).getValue());
        }

        // country - city - street - HouseNum
        Address address = evse.getAddress();
        if (address != null) {
            ret.setAddress(address.getCountry() + ADDRESS_SPLIT + address.getCity() + ADDRESS_SPLIT +
                    address.getStreet() + ADDRESS_SPLIT + address.getHouseNum());
        }

        GeoCoordinates geoCoordinates = evse.getGeoCoordinates();
        String[] latLong = geoCoordinates.getGoogle().getCoordinates().split(" ");
        ret.setLatitude(latLong[0]);
        ret.setLongitude(latLong[1]);

        ret.setOperatorId(evse.getOperatorID());
        ret.setOperatorName(evse.getOperatorName());

        return ret;
    }
}
