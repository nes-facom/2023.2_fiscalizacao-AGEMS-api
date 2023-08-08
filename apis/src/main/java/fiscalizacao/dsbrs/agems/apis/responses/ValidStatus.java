package fiscalizacao.dsbrs.agems.apis.responses;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidStatusValidator.class)
@NotNull
@ReportAsSingleViolation
public @interface ValidStatus {

  String message() default "Status Inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

