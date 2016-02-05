package com.jean.autoLogin.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	static Log log = LogFactory.getLog(HttpUtil.class);

	/**
	 * 
	 * @param url
	 * @param formparams
	 * @return
	 */
	public static String post(String url, Map<String, Object> formparams) {
		ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
		if (formparams != null) {
			for (Entry<String, Object> entry : formparams.entrySet()) {
				arrayList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}
		return post(url, arrayList);
	}

	/**
	 * 
	 * @param url
	 * @param formparams
	 * @return
	 */
	public static String post(String url, List<NameValuePair> formparams) {
		// 创建参数队列
		if (formparams == null) {
			formparams = new ArrayList<NameValuePair>();
		}
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error(e);
			}
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpget = new HttpGet(url);
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			if (entity != null) {
				if (response.getStatusLine().getStatusCode() == 200) {
					return EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			// 关闭连接,释放资源
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error(e);
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
		return null;
	}

	/**
	 * 上传文件
	 */
	public static void upload(String url, File file) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httppost = new HttpPost(url);
			FileBody bin = new FileBody(file);
			StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);
			HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment)
					.build();
			httppost.setEntity(reqEntity);
			System.out.println("executing request " + httppost.getRequestLine());
			response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			EntityUtils.consume(resEntity);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e1) {
				log.error(e1);
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}
}