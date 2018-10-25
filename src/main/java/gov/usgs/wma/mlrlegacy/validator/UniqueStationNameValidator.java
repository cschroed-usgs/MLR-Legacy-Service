package gov.usgs.wma.mlrlegacy.validator;

import gov.usgs.wma.mlrlegacy.Controller;
import gov.usgs.wma.mlrlegacy.MonitoringLocation;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 * Validates that a MonitoringLocation has a unique station name
 */
@Component
public class UniqueStationNameValidator extends BaseDuplicateMonitoringLocationValidator {
    @Override
    public Map<String, Object> getFilterValues(MonitoringLocation value) {
        Map<String, Object> filters = new HashMap<>();
        filters.put(Controller.STATION_NAME, value.getStationName());
        return filters;
    }

    @Override
    public void setConstraintViolations(MonitoringLocation value, ConstraintValidatorContext context) {
        String message = String.format("Duplicate Station Name (%s) found.", value.getStationName());
        
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(Controller.STATION_NAME)
                .addConstraintViolation();
    }
}