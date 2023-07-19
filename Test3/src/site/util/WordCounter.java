package site.util;
import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = WordCounterValidator.class)
public @interface WordCounter
{
	String message() default "Invalid value. It should have at least 3 words and 3 uppercase letters.";
	
	int minNoun();
	int min();
	
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
