package com.codephase.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
 
public class DecodeConf {
	
	private Properties props = new Properties();
	
	private boolean isRunDecode = false;
	
	private String[] dirs;
	
	private String[] packages;
	
	private String classPath;
	
	private static DecodeConf decodeConf;
	
	public Properties getProps(){
		return props;
	}
	
	public boolean isRunDecode() {
		return isRunDecode;
	}
 
	public void setRunDecode(boolean isRunDecode) {
		this.isRunDecode = isRunDecode;
	}
 
	public String[] getDirs() {
		return dirs;
	}
 
	public void setDirs(String[] dirs) {
		this.dirs = dirs;
	}
 
	public String[] getPackages() {
		return packages;
	}
 
	public void setPackages(String[] packages) {
		this.packages = packages;
	}
 
	public String getClassPath() {
		return classPath;
	}
 
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
	public static DecodeConf getConf(){
		return decodeConf;
	}
 
	static{
		decodeConf = new DecodeConf();
		InputStream is = DecodeConf.class.getClassLoader().getResourceAsStream("decode.properties");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try{
			decodeConf.getProps().load(br);
			decodeConf.setRunDecode(Boolean.parseBoolean(decodeConf.getProps().getProperty("isRunDecode")));
		//	decodeConf.setDirs(decodeConf.getProps().getProperty("dirs").split(","));
			decodeConf.setDirs(decodeConf.getProps().getProperty("packages").replace('.', '/').split(","));
			decodeConf.setPackages(decodeConf.getProps().getProperty("packages").split(","));
			decodeConf.setClassPath(decodeConf.getProps().getProperty("classPath"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		if(DecodeConf.getConf().isRunDecode())
			System.out.println("it's true");
		else
			System.out.println("it's false");
	//	System.out.println(DecodeConf.getConf().isRunDecode());
	}
	
}