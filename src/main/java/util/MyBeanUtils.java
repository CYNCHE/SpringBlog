package util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

//used to copy the field names that is null
public class MyBeanUtils {
	
	public static String[] getNullPropertyNames(Object source) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
		List<String> nullPropertyNames = new ArrayList<>();
		for (PropertyDescriptor pd : pds) {
			String propertyName = pd.getName();
			if (beanWrapper.getPropertyValue(propertyName) == null) {
				nullPropertyNames.add(propertyName);
			}
		}
		return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
	}
}
