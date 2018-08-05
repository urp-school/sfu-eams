package com.shufe.service.util.stat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatUtils {
	
	public static void setValueToMap(String key, Object tempValue,
			String tempType, Map m) {
		if ("integer".equals(tempType)) {
			if (m.containsKey(key)) {
				m.put(key, new Integer(((Integer) m.get(key)).intValue()
						+ ((Integer) tempValue).intValue()));
			} else {
				m.put(key, (Integer) tempValue);
			}
		} else if ("float".equals(tempType)) {
			if (m.containsKey(key)) {
				m.put(key, new Float(((Float) m.get(key)).floatValue()
						+ ((Float) tempValue).floatValue()));
			} else {
				m.put(key, (Float) (tempValue));
			}
		} else if ("list".equals(tempType)) {
			List tempList = new ArrayList();
			if (m.containsKey(key)) {
				tempList = (ArrayList) m.get(key);
			}
			tempList.add(tempValue);
			m.put(key, tempList);
		} else if ("set".equals(tempType)) {
			Set tempSet = new HashSet();
			if (m.containsKey(key)) {
				tempSet = (HashSet) m.get(key);
			}
			tempSet.add(tempValue);
			m.put(key, tempSet);
		}
	}
}
