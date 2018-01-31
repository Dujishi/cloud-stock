package com.xiaoka.cloud.stock.service.core.util;

import java.util.Iterator;
import java.util.List;

/**
 * Do something
 *
 * @author suqin 2017/11/16
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ListUtil {

	public static String toStringWithoutBracket(List input) {

		Iterator<Object> it = input.iterator();
		if (!it.hasNext())
			return "";

		StringBuilder sb = new StringBuilder();
		for (; ; ) {
			Object e = it.next();
			sb.append(e);
			if (!it.hasNext())
				return sb.toString();
			sb.append(',');
		}
	}

}
