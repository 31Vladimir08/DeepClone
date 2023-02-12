package Services;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import Enums.BaseTypes;

public class CopyUtils {
	public static <T> T deepCopy(T originObject) throws Exception {
		if (isPrimitive(originObject)) {
			return originObject;
		}
		
		var classType = originObject.getClass();
		var newObject = createNewObject(classType);
		if (newObject == null) {
			return originObject;
		}

		var baseClass = getTypeFromName(newObject.getClass().getSuperclass().getTypeName());
		if (baseClass == BaseTypes.AbstractList
				|| baseClass == BaseTypes.AbstractCollection) {
			copyCollection(newObject, originObject);			
			return (T)newObject;
		}
		
		if (baseClass == BaseTypes.AbstractMap) {
			copyMap(newObject, originObject);
			return (T)newObject;
		}

		var fields = classType.getDeclaredFields();
		for (var item : fields) {
			item.setAccessible(true);
		    var value = item.get(originObject);
		    if (isPrimitive(value)) {
		    	item.set(newObject, value);
		    }
		    else {
		    	var res = deepCopy(value);
            	item.set(newObject, res);
		    }
		}
		return (T)newObject;
	}
	
	private static boolean isPrimitive(Object classType) {
		if (classType.getClass().isPrimitive()) {
			return true;
		}
		
		var type = getTypeFromName(classType.getClass().getTypeName());
		//знаю, что string не примитив, но тк он не изменяемый, тоже добавил сюда
		if (type == BaseTypes.String
				|| type == BaseTypes.Character
				|| type == BaseTypes.Boolean) {
			return true;
		}
		
		var baseClass = getTypeFromName(classType.getClass().getSuperclass().getTypeName());
		if (baseClass == BaseTypes.Number) {
			return true;
		}
		return false;
	}
	
	private static <T> T createNewObject(Class<?> clazz) throws Exception {
		var constructors = clazz.getDeclaredConstructors();
		if (constructors.length == 0) {
			return null;
		}
		var constructor = getConstructorWithMinParams(constructors);
		var paramTypes = getParamsForConstructor(constructor);		
        var paramsOb = getDefaultParamsForNewObject(paramTypes);
        constructor.setAccessible(true);
        var newClass = (T)constructor.newInstance(paramsOb);
		return newClass;
	}
	
	private static Constructor getConstructorWithMinParams(Constructor<?> ... constructors) {
		var constructorMin = constructors[0];
		for (var item : constructors) {
			if (item.getParameterCount() < constructorMin.getParameterCount()) {
				constructorMin = item;
			}
		}
		return constructorMin;
	}
	
	private static Class[] getParamsForConstructor(Constructor constructor) {
		return constructor.getParameterTypes();
	}
	
	private static BaseTypes getTypeFromName(String typeName) {
		switch(typeName){            
        case "java.lang.Object":
        	return BaseTypes.Object;
        case "java.util.AbstractList":
        	return BaseTypes.AbstractList;
        case "java.util.AbstractCollection":
        	return BaseTypes.AbstractCollection;
        case "java.util.AbstractMap":
        	return BaseTypes.AbstractMap;
        case "java.lang.Number":
        	return BaseTypes.Number;
        case "java.lang.Character":
        	return BaseTypes.Character;
        case "java.lang.Boolean":
        	return BaseTypes.Boolean;
        case "java.lang.String":
        	return BaseTypes.String;
        default:
        	return BaseTypes.Object;
    	}
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
	
	private static void copyMap(Object newClass, Object originObject) throws Exception {
		var newMap = (Map)newClass;
		var originMap = (Map)originObject;
		
		for(var item : originMap.entrySet()){
			var keyValue = (Map.Entry<Object, Object>)item;
			
			var key = keyValue.getKey();			
			if (!isPrimitive(key)) {
				key = deepCopy(key);
		    }
			
		    var value = keyValue.getValue();
		    if (!isPrimitive(value)) {
		    	value = deepCopy(value);
		    }
		    
		    newMap.put(key, value);
	    }
	}
	
	private static void copyCollection(Object newClass, Object originObject) throws Exception {
		var list = (Collection)newClass;
		for (var item : (Collection)originObject) {
			if (!isPrimitive(item)) {
				var res = deepCopy(item);
            	list.add(res);
                break;
		    }
			else {
				list.add(item);			
		    }
		}
	}
}
