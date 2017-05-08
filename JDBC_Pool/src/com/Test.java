package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;;

//测试类

public class Test {
	public static void main(String[] args) {
		Connection con=null;
		PreparedStatement p=null;
		ResultSet r=null;
		DataSource ds=new MyDataSource();
		try {
			con=ds.getConnection();
			p=con.prepareStatement("select *from Student;");
			r=p.executeQuery();
			while(r.next()){
				System.out.print("id:"+r.getInt(1));
				System.out.println("   name:"+r.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				//使用装饰类MyConnection的对象,因为那个装饰类中提供了close()方法
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//测试2
	@org.junit.Test
	public void text1(){
		Connection con=null;
		PreparedStatement p=null;
		ResultSet r=null;
		//MyDataSource mds=new MyDataSource(); 不规范 因为别人不认识MyDataSource 只认识sun公司提供的规范DataSource
		DataSource ds=new MyDataSource();
		try {
			con=ds.getConnection();
			p=con.prepareStatement("select *from Student;");
			r=p.executeQuery();
			while(r.next()){
				System.out.print("id:"+r.getInt(1));
				System.out.println("   name:"+r.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//ds.close(); 关闭不了 因为父类DataSource中不存在close()方法
			((MyDataSource)ds).colse(con);//强转 这个方法也不规范 别人不认识MyDataSource
			//所以要用装饰设计模式，将mysql提供的jar包里的 Connection进行装饰
		}
	}
}
