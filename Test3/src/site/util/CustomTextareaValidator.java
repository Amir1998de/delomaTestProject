package site.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("customTextareaValidator")
public class CustomTextareaValidator implements Validator<String>
{
    private static final int MIN_WORD_COUNT = 3;
    private static final int MIN_UPPERCASE_COUNT = 3;
    
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, String arg2) throws ValidatorException
	{
			String textareaValue =  arg2;
		 
	        /*  .trim() -> führende und nachfolgende Leerzeichen entfernen
	         *  .split("\\s+") -> den String in ein Array von Wörtern aufteilen
	         * 
	         */
	        String[] words = textareaValue.trim().split("\\s+");
	        //  Count the number of words not char for Example "Hello World" has two words
	        int wordCount = words.length;

	        // Count the number of uppercase letters
	        int uppercaseCount = 0;
	        for (char c : textareaValue.toCharArray()) {
	            if (Character.isUpperCase(c)) {
	                uppercaseCount++;
	            }
	        }

	        if (wordCount < MIN_WORD_COUNT || uppercaseCount < MIN_UPPERCASE_COUNT) {
	            FacesMessage message = new FacesMessage("The textarea must contain at least 3 words and 3 uppercase letters");
	            throw new ValidatorException(message);
	        }
		
	}

}
