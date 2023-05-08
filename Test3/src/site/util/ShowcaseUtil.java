package site.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.FilterMeta;

public class ShowcaseUtil {

	private ShowcaseUtil() {

	}

	public static final Object getPropertyValueViaReflection(final Object o, final String field)
			throws ReflectiveOperationException, IllegalArgumentException, IntrospectionException {
		return new PropertyDescriptor(field, o.getClass()).getReadMethod().invoke(o);
	}

	public static Map<String, Object> convertFilters(final Map<String, FilterMeta> filterBy) {
		// convert filtermeta to simple hashmap
		final Map<String, Object> filters = new HashMap<>();
		if (filterBy != null) {
			for (final Entry<String, FilterMeta> entry : filterBy.entrySet()) {
				filters.put(entry.getValue().getField(), entry.getValue().getFilterValue());
			}
		}

		return filters;
	}

}