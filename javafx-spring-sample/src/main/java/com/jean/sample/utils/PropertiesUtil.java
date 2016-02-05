package com.jean.sample.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	private static Map<String, Properties> properties = new HashMap<>();

	public static String getProperty(String url, String key) throws IOException {
		if (properties.get(url) == null) {
			Properties pro = new Properties();
			pro.load(new BufferedReader(new InputStreamReader(PropertiesUtil.class.getResourceAsStream(url), "UTF-8")));
			properties.put(url, pro);
		}
		return properties.get(url).getProperty(key);
	}

}
