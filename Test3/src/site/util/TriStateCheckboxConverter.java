package site.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class TriStateCheckboxConverter implements Converter
{
	/* 
	 * Custom converter for TriStateCheckbox 
	 * */
	public Boolean getAsObject(FacesContext context, UIComponent component, String value)
	{
        if ("0".equals(value)) {
            return null;
        } else if ("1".equals(value)) {
            return true;
        } else if ("2".equals(value)) {
            return false;
        }
		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Boolean arg2)
	{
        if (arg2==null) {
            return "0";
        } else if (arg2==true) {
            return "1";
        } else if (arg2==false) {
            return "2";
        }		
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
