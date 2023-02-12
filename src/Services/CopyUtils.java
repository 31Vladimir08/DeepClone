package Services;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class CopyUtils {
	public static <T> T deepCopy(T originObject) throws Exception {
		var classType = originObject.getClass();
		var dd = classType.isPrimitive();
		if (classType.isPrimitive()) {
			return originObject;
		}
		var newClass = createNewClass(classType);
		if (newClass == null) {
			return originObject;
		}
		
		var s = newClass.getClass().getTypeName();
		if (s.equals("java.lang.String") || s.equals("java.lang.Character") || s.equals("java.lang.Boolean")) {
			return originObject;
		}
		
		var baseClass = newClass.getClass().getSuperclass().getTypeName();
		if (baseClass.equals("java.util.AbstractList")) {
			var list = (List)newClass;
			for (var item : (List)originObject) {
				var basyTypeName = item.getClass().getSuperclass().getTypeName();
			    switch(basyTypeName){            
	            case "java.lang.Object":
	            case "java.util.AbstractList":
	            	var res = deepCopy(item);
	            	list.add(res);
	                break;
	            default:
	            	list.add(item);
			    }
			}
			
			return (T)newClass;
		}
		var fields = classType.getDeclaredFields();
		for (var item : fields) {
			item.setAccessible(true);
		    var value = item.get(originObject);
		    var typeName = value.getClass().getSuperclass().getTypeName();
		    switch(typeName){            
            case "java.lang.Object":
            case "java.util.AbstractList":
            	var res = deepCopy(value);
            	item.set(newClass, res);
                break;
            default:
            	item.set(newClass, value);
        	}
		}
		return (T)newClass;
	}
	
	private static <T> T createNewClass(Class<?> clazz) throws Exception {
		var constructors = clazz.getConstructors();
		if (constructors.length == 0) {
			return null;
		}
		var paramTypes = getParamsForConstructor(constructors);		
        var paramsOb = getDefaultParamsForNewObject(paramTypes);        
        var newClass = (T)clazz.getConstructor(paramTypes).newInstance(paramsOb);
		return newClass;
	}
	
	private static Class[] getParamsForConstructor(Constructor<?> ... constructors) {
		var constructorMin = constructors[0];
		for (var item : constructors) {
			if (item.getParameterCount() < constructorMin.getParameterCount()) {
				constructorMin = item;
			}
		}
		return constructorMin.getParameterTypes();
	}
	
	private static Object[] getDefaultParamsForNewObject(Class<?> ... paramTypes) throws ClassNotFoundException {
		var paramsOb = new ArrayList<Object>();
        for (var paramType : paramTypes) {
        	var typeName = paramType.getTypeName();
        	switch(typeName){            
            case "int":
            case "long":
            case "short":
            case "byte":
            case "float":
            case "double":
            	paramsOb.add(0);
                break;
            case "boolean":
            	paramsOb.add(false);
                break;
            case "char": 
            	paramsOb.add('t');
                break;
            default:
            	paramsOb.add(null);
        	}
        }
        
        return paramsOb.toArray();
	}
}
