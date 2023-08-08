package fiscalizacao.dsbrs.agems.apis.responses;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidStatusValidator
  implements ConstraintValidator<ValidStatus, Integer> {

  private static final int[] ALLOWED_VALUES = {
    0,
    400,
    401,
    402,
    403,
    404,
    405,
    406,
    407,
    408,
    409,
    410,
    411,
    412,
    413,
    414,
    415,
    416,
    417,
    418,
    421,
    422,
    423,
    424,
    426,
    428,
    429,
    431,
    444,
    451,
    500,
    501,
    502,
    503,
    504,
    505,
    506,
    507,
    508,
    510,
    511,
    599,
  };

  @Override
  public void initialize(ValidStatus constraintAnnotation) {
   
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; 
    }

    if (!isWithinAllowedRanges(value)) {
      context.disableDefaultConstraintViolation();
      context
        .buildConstraintViolationWithTemplate("Status inválido")
        .addConstraintViolation();
      return false;
    }

    return true;
  }

  private boolean isWithinAllowedRanges(int value) {
    for (int i = 0; i < ALLOWED_VALUES.length;i++) {
      if (value == ALLOWED_VALUES[i]) {
        return true;
      }
    }
    return false;
  }

 
}
