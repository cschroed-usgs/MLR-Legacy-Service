package gov.usgs.wma.mlrlegacy.validator;

import gov.usgs.wma.mlrlegacy.Controller;
import gov.usgs.wma.mlrlegacy.MonitoringLocation;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 * Validates that a MonitoringLocation has unique agency code and site number.
 */
@Component
public class UniqueAgencyCodeAndSiteNumberValidator extends BaseDuplicateMonitoringLocationValidator {

    @Override
    public Map<String, Object> getFilterValues(MonitoringLocation value) {
        Map<String, Object> filters = new HashMap<>();
        filters.put(Controller.AGENCY_CODE, value.getAgencyCode());
        filters.put(Controller.SITE_NUMBER, value.getSiteNumber());
        return filters;
    }

    @Override
    public void setConstraintViolations(MonitoringLocation value, ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate("Duplicate Agency Code and Site Number found in MLR.")
                .addPropertyNode(Controller.AGENCY_CODE)
                .addPropertyNode(Controller.SITE_NUMBER)
                .addConstraintViolation();
    }
}