package com.jean.autoLogin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	private static Map<String, Properties> properties = new HashMap<>();

	public static String getProperty(String url, String key) throws IOException {
		if (properties.get(url) == null) {
			Properties pro = new Properties();
			InputStream inputStream = PropertiesUtil.class.getResourceAsStream(url);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			pro.load(bf);
			properties.put(url, pro);
		}
		return properties.get(url).getProperty(key);
	}
}
