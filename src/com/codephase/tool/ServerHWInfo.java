package com.codephase.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 与系统相关的一些常用工具方法. 
 *  
 * @author MarkYXJ
 * @version 190728 
 */  
public class ServerHWInfo {
	static Scanner sc = new Scanner(System.in); 
	static String mac=null;
	static String disk=null;
	static String cpu=null;
    /** 
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等. 
     */  
    private static String getOSName() {
        return System.getProperty("os.name").toLowerCase();  
    }  
  
    private static void setUnixMACSerial() {
    	if(mac==null){
	        BufferedReader bufferedReader = null;  
	        Process process = null;  
	        try {  
	            process = Runtime.getRuntime().exec("ip addr");  
	            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
	            String line = null;
	     outer: while ((line = bufferedReader.readLine()) != null) {
	            	//System.out.println(line);
	            	String regex = "ether \\b[0-9a-f]{2}:[0-9a-f]{2}:[0-9a-f]{2}:[0-9a-f]{2}:[0-9a-f]{2}:[0-9a-f]{2}\\b";
	            	Pattern p = Pattern.compile(regex);
	            	Matcher m = p.matcher(line);
	            	while(m.find()){
	            		mac=line.substring(m.start()+6,m.end()).replaceAll("[ :-]+", "");
	            		break outer;
	            	}
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (bufferedReader != null) {  
	                    bufferedReader.close();  
	                }  
	            } catch (IOException e1) {  
	                e1.printStackTrace();  
	            }  
	            bufferedReader = null;  
	            process = null;  
	        }  
    	}
    }  
    
    private static void setUnixDiskSerial() {
    	if(disk==null){
	        BufferedReader bufferedReader = null;  
	        Process process = null;  
	        try {
	            process = Runtime.getRuntime().exec("hdparm -I /dev/sda");  
	            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
	            String line = null;  
	            int index = -1;  
	            while ((line = bufferedReader.readLine()) != null) {
	            //	System.out.println(line);
	                index = line.indexOf("Serial Number");  
	                if (index >= 0) {
	                    disk = line.substring(index + "Serial Number".length() + 1).replaceAll("[ :-]+", "");  
	                    break;  
	                }   
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (bufferedReader != null) {
	                    bufferedReader.close();  
	                }  
	            } catch (IOException e1) {  
	                e1.printStackTrace();  
	            }  
	            bufferedReader = null;  
	            process = null;  
	        }  
    	}
    }
    
    private static void setUnixCPUSerial() {
        if(cpu==null){
	        BufferedReader bufferedReader = null;  
	        Process process = null;  
	        try {
	            process = Runtime.getRuntime().exec("dmidecode -t 4");  
	            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
	            String line = null;  
	            int index = -1;  
	            while ((line = bufferedReader.readLine()) != null) {
	            //	System.out.println(line);
	                index = line.indexOf("ID:");  
	                if (index >= 0) {
	                    cpu = line.substring(index + "ID:".length() + 1).replaceAll("[ :-]+", "");  
	                    break;  
	                } 
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (bufferedReader != null) {
	                    bufferedReader.close();  
	                }  
	            } catch (IOException e1) {  
	                e1.printStackTrace();  
	            }  
	            bufferedReader = null;  
	            process = null;  
	        }  
        } 
    }
    
    private static void setWindowsMACSerial() {
    	if(mac==null){
	        System.out.println("请输入MAC地址："); 
	        mac = sc.nextLine().replaceAll("[ :-]+", ""); 
    	}
    }  
    
    private static void setWindowsDiskSerial() {
    	if(disk==null){
	        System.out.println("请输入硬盘序列号："); 
	        disk = sc.nextLine().replaceAll("[ :-]+", ""); 
    	}
    }  
    
    private static void setWindowsCPUSerial() {
    	if(cpu==null){
	        System.out.println("请输入CPU ID："); 
	        cpu = sc.nextLine().replaceAll("[ :-]+", ""); 
    	}
    }
    
    static String getMac(){
    	String os = getOSName();  
    	if (os.startsWith("win")){
    		setWindowsMACSerial();
    		return mac;
    	}
    	else{
    		setUnixMACSerial();
    		return mac;
    	}    	
    }
    
    static String getDisk(){
    	String os = getOSName();  
    	if (os.startsWith("win")){
    		setWindowsDiskSerial();
    		return disk;
    	}
    	else{
    		setUnixDiskSerial();
    		return disk;
    	}    	
    }
    
    static String getCpu(){
    	String os = getOSName();  
    	if (os.startsWith("win")){
    		setWindowsCPUSerial();
    		return cpu;
    	}
    	else{
    		setUnixCPUSerial();
    		return cpu;
    	}    	
    }

}  