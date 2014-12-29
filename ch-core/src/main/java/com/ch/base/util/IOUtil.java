/**
 * 
 */
package com.ch.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author mozhiqiang
 *
 */
public class IOUtil {

	public static String inputStreamToString(InputStream is,String encoding) throws IOException {

		if(StringUtil.isEmpty(encoding))
			encoding = "UTF-8";
		BufferedReader in = new BufferedReader(new InputStreamReader(is,encoding));
		StringBuffer buffer = new StringBuffer();
		String line = " ";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

}
