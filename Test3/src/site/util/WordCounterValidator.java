package site.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class WordCounterValidator implements ConstraintValidator<WordCounter, String>
{
    private int minWords;
    private int minNoun;

	@Override
	public void initialize(WordCounter constraintAnnotation)
	{
        minWords = constraintAnnotation.min();
        minNoun = constraintAnnotation.minNoun();
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context)
	{
        // Überprüfung die Anzahl der Wörter
        String[] words = value.split("\\s+");
        if (words.length < minWords) {
            return false;
        }
        
     // Überprüfung die Anzahl der Großbuchstaben
        int countUppercase = 0;
        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                countUppercase++;
            }
        }
        if (countUppercase < minNoun) {
            return false;
        }

        return true;

	}

}
