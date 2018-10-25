package gov.usgs.wma.mlrlegacy.validator;

import gov.usgs.wma.mlrlegacy.MonitoringLocation;
import gov.usgs.wma.mlrlegacy.UniqueKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Composite Constraint Validator. Validates that Monitoring Locations are not
 * duplicates in multiple ways.
 * 
 * To create and enforce a new ConstraintValidator for MonitoringLocations:
 *  1) Create a new object that extends BaseDuplicateMonitoringLocationValidator
 *  2) Add the new object to this class' autowired dependencies and its call to super(). 
 *  3) Update the MyBatis mappings to support any new fields.
 *  
 */
@Component
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
