package com.KoreaIT.java.am;

public class Config {

	public static String getUrl() {
		
		return "jdbc:mysql://127.0.0.1:3306/JSPTest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

	}
	
	public static String getUser() {
		
		return "root";

	}
	
	public static String getPassWord() {
		
		return "";

	}
	
	public static String getDriverName() {
		
		return "com.mysql.jdbc.Driver";

	}

}
