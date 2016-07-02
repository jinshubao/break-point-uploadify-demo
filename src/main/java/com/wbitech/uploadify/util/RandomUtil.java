/**
 * RandomUtil.java
 *
 * 项目名称:互联网金融教育 
 *  
 * Created date: 2015年12月26日
 *
 */
package com.wbitech.uploadify.util;

import java.util.Random;

/**
 * @TODO 随机code码工具类
 *
 * @author Ye.Chen
 * @created 2015年12月29日 下午4:28:38
 * @version 1.0.0
 * @since 1.0.0
 * @modify
 * 
 */
public class RandomUtil {
	public static String random(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while(true) {
                int k = ran.nextInt(10);
                if( (bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char)(k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }

}
