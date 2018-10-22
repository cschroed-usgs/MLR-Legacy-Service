package gov.usgs.wma.mlrlegacy.validator;

import gov.usgs.wma.mlrlegacy.Controller;
import gov.usgs.wma.mlrlegacy.MonitoringLocation;
import gov.usgs.wma.mlrlegacy.MonitoringLocationDao;
import gov.usgs.wma.mlrlegacy.UniqueKey;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides common functionality for determining if a Monitoring Location
 * @author cschroed
 */
public abstract class BaseDuplicateMonitoringLocationValidator implements ConstraintValidator<UniqueKey, MonitoringLocation> {
	
	@Autowired
	private MonitoringLocationDao dao;

	@Override
	public void initialize(UniqueKey constraintAnnotation) {
		// Nothing for us to do here at this time.
	}

        /**
         * 
         * @param value
         * @param context
         * @return 
         */
	@Override
	public boolean isValid(MonitoringLocation value, ConstraintValidatorContext context) {
		boolean valid = true;

		if (null != value && null != context) {
                    Map<String, Object> filters = this.getFilterValues(value);
                    MonitoringLocation monitoringLocation = dao.getByAK(filters);
                    if (monitoringLocation != null) {
                        if (null == value.getId() || 0 == monitoringLocation.getId().compareTo(value.getId())) {
                            valid = false;
                            context.disableDefaultConstraintViolation();
                            setConstraintViolations(value, context);
                        }
                    }
		}

		return valid;
	}
        
        /**
         * 
         * @param monitoringLocation a monitoring location
         * @return a Map that determines what will be searched for. The map's keys are database fields and its values are from the `monitoringLocation`. These predicates will be AND-ed together.
         */
        public abstract Map<String, Object> getFilterValues(MonitoringLocation monitoringLocation);
        
        /**
         * If a MonitoringLocation is found that matches the criteria defined by `getFilterValues()`, then this method will be executed to add custom constraint
         * violations to the context.
         * @param value
         * @param context 
         */
        public abstract void setConstraintViolations(MonitoringLocation value, ConstraintValidatorContext context);
}