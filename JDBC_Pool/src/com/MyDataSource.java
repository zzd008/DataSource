package com;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

//数据库连接池规范
//DataSource接口提供了两个getConnection()重载方法，但是没有提供close()关闭方法
public class MyDataSource implements DataSource{//连接池都要继承sun公司的规范，即实现DataSource（数据源）接口
	private static LinkedList<Connection> pool=new LinkedList<Connection>();
	//private static LinkedList<Connection> pool=(LinkedList<Connection>) Collections.synchronizedList(new LinkedList<Connection>());加线程锁会有异常
	static{
		for(int i=0;i<10;i++){//连接池中放入10个连接
			Connection con=null;
			try {
				con=Dbutil.getConnection();
				pool.add(con);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {//无参方法
		if(pool.size()>0){//连接池中还有Connection
			Connection con=pool.removeFirst();//拿走一个
			MyConnection c=new MyConnection(con, pool);//将Connection装饰，返回一个MyConnection对象
			return c;
			//return con;
		}
		else{//池中Connection不够
			//创建Connection/等待其他用户用完 把Connection放回
			throw new RuntimeException("服务器忙！");
		}
	}
	
	public void colse(Connection con){//关闭方法(非重写方法)
		pool.addLast(con);//用完放到集合的最后，而不是直接关闭掉con.close()
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {//有参方法
		return null;
	}
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
