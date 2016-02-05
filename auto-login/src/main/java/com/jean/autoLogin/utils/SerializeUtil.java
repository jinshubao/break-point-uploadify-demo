package com.jean.autoLogin.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SerializeUtil {
	static Log log = LogFactory.getLog(SerializeUtil.class);

	public static void serialize(Object serializable, String fullName) {
		FileOutputStream fs = null;
		ObjectOutputStream os = null;
		try {
			fs = new FileOutputStream(fullName);
			os = new ObjectOutputStream(fs);
			os.writeObject(serializable);
			os.flush();
		} catch (Exception ex) {
			log.error(ex);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				log.error(e);
			}
			try {
				if (fs != null) {
					fs.close();
				}
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(String fileName) {
		ObjectInputStream ois = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(fileName);// ("foo.ser");
			ois = new ObjectInputStream(fs);
			return (T) ois.readObject();

		} catch (Exception ex) {
			log.error(ex);
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				log.error(e);
			}
			try {
				if (fs != null) {
					fs.close();
				}
			} catch (IOException e) {
				log.error(e);
			}
		}
		return null;
	}
}
