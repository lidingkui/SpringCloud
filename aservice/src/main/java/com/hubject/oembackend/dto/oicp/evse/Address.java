package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Address {
    @JSONField(name = "City")
    private String city;
    @JSONField(name = "Country")
    private String country;
    @JSONField(name = "Floor")
    private String floor;
    @JSONField(name = "HouseNum")
    private String houseNum;
    @JSONField(name = "PostalCode")
    private String postalCode;
    @JSONField(name = "Region")
    private String region;
    @JSONField(name = "Street")
    private String street;
    @JSONField(name = "TimeZone")
    private String timeZone;
    @JSONField(name = "ParkingFacility")
    private Boolean parkingFacility;
    @JSONField(name = "ParkingSpot")
    private String parkingSpot;
}
