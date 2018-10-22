package gov.usgs.wma.mlrlegacy.validator;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This class implements the Composite design pattern to permit a group of 
 * ConstraintValidators to be used as one. Child classes define which 
 * ConstraintValidators should be grouped together.
 * 
 * @param <A> an annotation type. Example: UniqueKey
 * @param <V> the type to validate. Example: MonitoringLocation
 */
public abstract class CompositeConstraintValidator<A extends Annotation , V> implements ConstraintValidator<A, V> {
    
    private final List<ConstraintValidator<A, V>> constraintValidators;
    
    /**
     *
     * @param constraintValidators one or more ConstraintValidators that should
     * be managed as though they are a single ConstraintValidator.
     */
    public CompositeConstraintValidator(ConstraintValidator<A, V> ... constraintValidators) {
        this.constraintValidators = Arrays.asList(constraintValidators);
    }
    
    /**
     * Initializes every underlying ConstraintValidator.
     * @param constraintAnnotation 
     */
    @Override
    public void initialize(A constraintAnnotation) {
        this.constraintValidators.stream().forEach((cv) -> cv.initialize(constraintAnnotation));
    }

    /**
     * Validates `value` against all of the ConstraintValidators that were 
     * passed to this instance's constructor.
     * @param value the object to validate
     * @param context the context within which to validate the value
     * @return true if all underlying ConstraintValidators return true. False otherwise.
     */
    @Override
    public boolean isValid(V value, ConstraintValidatorContext context) {
        boolean valid = this.constraintValidators.stream()
                .allMatch((cv) -> cv.isValid(value, context));
        return valid;
    }

}
