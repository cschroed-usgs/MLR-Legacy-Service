package gov.usgs.wma.mlrlegacy.validator;

import gov.usgs.wma.mlrlegacy.MonitoringLocation;
import gov.usgs.wma.mlrlegacy.UniqueKey;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Composite Constraint Validator. Validates that Monitoring Locations are not
 * duplicates in multiple ways.
 */
public class DuplicateMonitoringLocationValidator extends CompositeConstraintValidator<UniqueKey, MonitoringLocation>{
    
    @Autowired
    public DuplicateMonitoringLocationValidator(
            UniqueAgencyCodeAndSiteNumberValidator uniqueAgencyCodeAndSiteNumberValidator,
            UniqueDistrictCodeAndStationNameValidator uniqueDistrictCodeAndStationNameValidator
    ) {
        super(
                uniqueAgencyCodeAndSiteNumberValidator,
                uniqueDistrictCodeAndStationNameValidator
        );
    }

}
