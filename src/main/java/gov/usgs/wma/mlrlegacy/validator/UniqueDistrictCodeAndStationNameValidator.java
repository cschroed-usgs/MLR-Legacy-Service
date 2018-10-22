package gov.usgs.wma.mlrlegacy.validator;

import gov.usgs.wma.mlrlegacy.Controller;
import gov.usgs.wma.mlrlegacy.MonitoringLocation;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 * Validates that a MonitoringLocation has a unique site name within its district code
 */
@Component
public class UniqueDistrictCodeAndStationNameValidator extends BaseDuplicateMonitoringLocationValidator {
    @Override
    public Map<String, Object> getFilterValues(MonitoringLocation value) {
        Map<String, Object> filters = new HashMap<>();
        filters.put(Controller.DISTRICT_CODE, value.getDistrictCode());
        filters.put(Controller.STATION_NAME, value.getStationName());
        return filters;
    }

    @Override
    public void setConstraintViolations(MonitoringLocation value, ConstraintValidatorContext context) {
        String message = String.format("Duplicate Station Name (%s) within the Same District (%s).", value.getStationName(), value.getDistrictCode());
        
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(Controller.DISTRICT_CODE)
                .addPropertyNode(Controller.STATION_NAME)
                .addConstraintViolation();
    }
}