package site.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

public class ShowcaseUtil {

    private ShowcaseUtil() {

    }


    public static final Object getPropertyValueViaReflection(Object o, String field)
                throws ReflectiveOperationException, IllegalArgumentException, IntrospectionException {
        return new PropertyDescriptor(field, o.getClass()).getReadMethod().invoke(o);
    }
    



}