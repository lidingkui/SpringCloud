package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Evse {

    @JSONField(name = "Accessibility")
    private String accessibility;

    @JSONField(name = "AccessibilityLocation")
    private String accessibilityLocation;

    @JSONField(name = "Address")
    private Address address;

    @JSONField(name = "CalibrationLawDataAvailability")
    private String calibrationLawDataAvailability;

    @JSONField(name = "ChargingPoolID")
    private String chargingPoolID;

    @JSONField(name = "ChargingStationID")
    private String chargingStationID;

    @JSONField(name = "ChargingStationImage")
    private String chargingStationImage;

    @JSONField(name = "ClearinghouseID")
    private String clearinghouseID;

    @JSONField(name = "DynamicInfoAvailable")
    private String dynamicInfoAvailable;

    @JSONField(name = "DynamicPowerLevel")
    private Boolean dynamicPowerLevel;

    @JSONField(name = "EvseID")
    private String evseId;

    @JSONField(name = "EnvironmentalImpact")
    private EnvironmentalImpact environmentalImpact;

    @JSONField(name = "GeoChargingPointEntrance")
    private GeoCoordinates geoChargingPointEntrance;

    @JSONField(name = "GeoCoordinates")
    private GeoCoordinates geoCoordinates; // Geolocation of the charging station

    @JSONField(name = "HardwareManufacturer")
    private String hardwareManufacturer;

    @JSONField(name = "HotlinePhoneNumber")
    private String hotlinePhoneNumber;

    @JSONField(name = "HubOperatorID")
    private String hubOperatorId;

    @JSONField(name = "IsHubjectCompatible")
    private Boolean isHubjectCompatible;

    @JSONField(name = "IsOpen24Hours")
    private Boolean isOpen24Hours;

    @JSONField(name = "MaxCapacity")
    private Integer maxCapacity;

    @JSONField(name = "RenewableEnergy")
    private Boolean renewableEnergy;

    @JSONField(name = "SubOperatorName")
    private String subOperatorName;

    @JSONField(name = "deltaType")
    private String deltaType;

    @JSONField(name = "lastUpdate")
    private String lastUpdate;

    @JSONField(name = "OperatorID")
    private String operatorID;

    @JSONField(name = "OperatorName")
    private String operatorName;

    @JSONField(name = "AdditionalInfo")
    private List<InfoText> additionalInfo;

    @JSONField(name = "AuthenticationModes")
    private List<String> authenticationModes;

    @JSONField(name = "ChargingFacilities")
    private List<ChargingFacilities> chargingFacilities;

    @JSONField(name = "ChargingStationNames")
    private List<InfoText> chargingStationNames;

    @JSONField(name = "ChargingStationLocationReference")
    private List<InfoText> chargingStationLocationReference;

    @JSONField(name = "EnergySource")
    private List<EnergySource> energySource;

    @JSONField(name = "OpeningTimes")
    private List<OpeningTimes> openingTimes;

    @JSONField(name = "PaymentOptions")
    private List<String> paymentOptions;

    @JSONField(name = "Plugs")
    private List<String> plugs;

    @JSONField(name = "ValueAddedServices")
    private List<String> valueAddedServices;
}
