package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.junit.Test;

//获取一个数据库连接对象Connection的规范

public class Dbutil {
	private static String driverurl;
	private static String url;
	private static String username;
	private static String password;
	
	static{
		ResourceBundle res=ResourceBundle.getBundle("Dbinfo");//从配置文件中读取配置信息（key-value）
		driverurl=res.getString("driverurl");
		url=res.getString("url");
		username=res.getString("username");
		password=res.getString("password");
		try {
			Class.forName(driverurl);//注册驱动（反射）
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//获取一个连接
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,username,password);
	}
	
	//关闭连接
	public static void closeAll(Connection c,PreparedStatement p,ResultSet r){
		if(r!=null){
			try {
				r.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				r=null;
			}
		}
		if(p!=null){
			try {
				p.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				p=null;
			}
		}
		if(c!=null){
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				c=null;
			}
		}
	}

	@Test
	public void text1(){  //JUnit测试
		Connection c=null;
		try {
			c=Dbutil.getConnection();
			System.out.println("数据库连接正常！");
		} catch (SQLException e) {
			System.out.println("数据库连接失败！");
		}
		Dbutil.closeAll(c, null, null);
		System.out.println("数据库关闭正常！");
	}
}
