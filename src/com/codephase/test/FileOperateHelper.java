package com.codephase.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
/**
 * 
 * Title: FileOperateHelper
 * Description: 文件读写帮助类
 * @author 陈家宝
 * @date   2019年3月12日 下午1:37:01
 *
 */
public class FileOperateHelper {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final boolean HIDE_LOG = false;		// 隐藏调试输出
	
	/**
	 * 
	 * @title: read
	 * @description: 读取文件内容
	 * @author: 陈家宝
	 * @version: V1.00
	 * @date: 2019年3月13日 上午2:16:04
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static byte[] read(File file) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		byte[] buf = new byte[1024];
		int len = 0;
		List<Byte> list = new ArrayList<Byte>();
		while((len = bis.read(buf)) > 0) {
			for(int i=0; i<len; i++) {
				list.add(buf[i]);
			}
		}
		
		bis.close();
		
		// InputStream.available()只是一个估计，不可靠，要不也没这么多事了
		byte[] result = new byte[list.size()];
		for(int i=0; i<list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}
	
	/**
	 * 
	 * @title: read
	 * @description: 读取流内容
	 * @author: 陈家宝
	 * @version: V1.00
	 * @date: 2019年3月13日 上午2:17:07
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] read(InputStream in) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(in);
		byte[] buf = new byte[1024];
		int len = 0;
		List<Byte> list = new ArrayList<Byte>();
		while((len = bis.read(buf)) > 0) {
			for(int i=0; i<len; i++) {
				list.add(buf[i]);
			}
		}
		
		bis.close();
		// InputStream.available()只是一个估计，不可靠，要不也没这么多事了
		byte[] result = new byte[list.size()];
		for(int i=0; i<list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}
	
	/**
	 * 
	 * @title: write
	 * @description: 将内容写入文件
	 * @author: 陈家宝
	 * @version: V1.00
	 * @date: 2019年3月13日 上午2:19:19
	 * @param content
	 * @param file
	 * @throws Exception
	 */
	public static void write(byte[] content, File file) throws Exception {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		bos.write(content);
		bos.flush();
		bos.close();
	}
	
	/**
	 * 
	 * @title: log
	 * @description: 做日志，不能断点调试太惨了
	 * @author: 陈家宝
	 * @version: V1.00
	 * @date: 2019年3月12日 下午8:10:22
	 * @param content 写入文件的内容
	 * @param append 是否追加到文件末尾
	 * @throws Exception
	 */
	public static void log(String content, boolean append) {
		if (HIDE_LOG) {
			return;
		}
		
		try {
			File file = new File("D:\\log.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,append)));
			bw.write("\r\n" + sdf.format(new Date()) + "   " + content);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static byte[] hexToBytes(String hex) {
	    hex = hex.length() % 2 != 0 ? "0" + hex : hex;
	 
	    byte[] b = new byte[hex.length() / 2];
	    for (int i = 0; i < b.length; i++) {
	        int index = i * 2;
	        int v = Integer.parseInt(hex.substring(index, index + 2), 16);
	        b[i] = (byte) v;
	    }
	    return b;
	}
	
	/**
     * Convert byte[] to hex string
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    public static void main(String[] args) {
		byte[] b = hexToBytes("cafebabe");
		String str = bytesToHexString(b);
		System.out.println(str);
	}
}